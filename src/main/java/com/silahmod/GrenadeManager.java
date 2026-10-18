package com.silahmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

/**
 * El bombalari. Ozel varlik/renderer yok: firlatilan bomba, "yere dusmus esya" (ItemEntity) olarak
 * spawn edilir. Boylece oyun fizigini (yercekimi, ziplama, kayma) ve 3D modeli kendisi cizer.
 * Toplanamaz ve birlesmez. Fitil suresi dolunca sunucu patlatir (RocketManager ile ayni mantik).
 */
public final class GrenadeManager {
	private GrenadeManager() {}

	private static final class Grenade {
		ServerLevel level;
		ItemEntity carrier;
		UUID owner;
		int age;
		Vec3 lastPos;
	}

	private static final List<Grenade> GRENADES = new ArrayList<>();
	private static final Map<UUID, Long> NEXT_THROW = new HashMap<>();

	public static void init() {
		// Dunya kapaninca (tek oyunculuda cikis) eski dunyaya ait bombalari birakma
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			GRENADES.clear();
			NEXT_THROW.clear();
		});
	}

	/** @return firlatildiysa true (bekleme suresindeyse false) */
	public static boolean throwGrenade(ServerLevel level, ServerPlayer player, ItemStack held) {
		long now = level.getGameTime();
		Long next = NEXT_THROW.get(player.getUUID());
		if (next != null && now < next) {
			return false;
		}
		NEXT_THROW.put(player.getUUID(), now + WeaponConfig.GRENADE_COOLDOWN);

		Vec3 look = player.getLookAngle();
		Vec3 start = player.getEyePosition().add(look.scale(0.5)).add(0.0, -0.2, 0.0);

		ItemEntity carrier = new ItemEntity(level, start.x, start.y, start.z, new ItemStack(ModItems.GRENADE));
		carrier.setNeverPickUp();         // toplanamaz, baska esyayla birlesmez
		carrier.setUnlimitedLifetime();   // fitil bitmeden kaybolmaz
		carrier.setDeltaMovement(look.scale(WeaponConfig.GRENADE_SPEED).add(0.0, 0.15, 0.0));
		level.addFreshEntity(carrier);

		Grenade grenade = new Grenade();
		grenade.level = level;
		grenade.carrier = carrier;
		grenade.owner = player.getUUID();
		grenade.lastPos = start;
		GRENADES.add(grenade);

		if (!player.getAbilities().instabuild) {
			held.shrink(1);
		}
		Sfx.play(level, player.getX(), player.getEyeY(), player.getZ(), "block.lever.click", 1.0F, 1.2F);
		Sfx.play(level, player.getX(), player.getEyeY(), player.getZ(), "entity.tnt.primed", 0.7F, 1.3F);
		return true;
	}

	public static void tick() {
		if (GRENADES.isEmpty()) {
			return;
		}
		Iterator<Grenade> it = GRENADES.iterator();
		while (it.hasNext()) {
			Grenade g = it.next();
			g.age++;
			boolean gone = g.carrier.isRemoved() || !g.carrier.isAlive();
			if (!gone) {
				g.lastPos = g.carrier.position();
				if (g.age % 3 == 0) {
					g.level.sendParticles(ParticleTypes.SMOKE, g.lastPos.x, g.lastPos.y + 0.25, g.lastPos.z,
							1, 0.02, 0.02, 0.02, 0.0);
				}
				// Son 1 saniyede bip sesi
				if (g.age >= WeaponConfig.GRENADE_FUSE - 20 && g.age % 5 == 0) {
					Sfx.play(g.level, g.lastPos.x, g.lastPos.y, g.lastPos.z, "block.note_block.hat", 0.8F, 1.6F);
				}
			}
			if (gone || g.age >= WeaponConfig.GRENADE_FUSE) {
				if (!gone) {
					g.carrier.discard();
				}
				explode(g.level, g.owner, g.lastPos);
				it.remove();
			}
		}
	}

	private static void explode(ServerLevel level, UUID ownerId, Vec3 pos) {
		Entity ownerEntity = level.getEntity(ownerId);
		DamageSource source = ownerEntity instanceof Player player
				? level.damageSources().playerAttack(player)
				: level.damageSources().generic();

		double r = WeaponConfig.GRENADE_RADIUS;
		Vec3 origin = pos.add(0.0, 0.3, 0.0); // yere gomulu olmasin diye hafif yukari
		AABB area = AABB.ofSize(origin, r * 2.0, r * 2.0, r * 2.0);
		for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area)) {
			if (target.isSpectator() || !target.isAlive()) {
				continue;
			}
			if (!WeaponConfig.GRENADE_HURTS_THROWER && target.getUUID().equals(ownerId)) {
				continue;
			}
			Vec3 center = target.getBoundingBox().getCenter();
			double dist = center.distanceTo(origin);
			if (dist > r) {
				continue;
			}
			// Duvarin arkasindakine hasar yok
			BlockHitResult wall = level.clip(new ClipContext(origin, center, ClipContext.Block.COLLIDER,
					ClipContext.Fluid.NONE, CollisionContext.empty()));
			if (wall.getType() != HitResult.Type.MISS) {
				continue;
			}
			float falloff = (float) (1.0 - dist / r); // merkezde 1, kenarda 0
			float amount = WeaponConfig.GRENADE_DAMAGE * (0.25F + 0.75F * falloff) * Armors.bodyFactor(target);
			target.invulnerableTime = 0;
			target.hurtServer(level, source, amount);
		}

		level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, pos.x, pos.y + 0.3, pos.z, 1, 0, 0, 0, 0);
		level.sendParticles(ParticleTypes.FLAME, pos.x, pos.y + 0.3, pos.z, 25, 0.5, 0.5, 0.5, 0.06);
		level.sendParticles(ParticleTypes.LARGE_SMOKE, pos.x, pos.y + 0.3, pos.z, 15, 0.5, 0.5, 0.5, 0.04);
		Sfx.play(level, pos.x, pos.y, pos.z, "entity.generic.explode", 3.0F, 1.0F);
	}
}
