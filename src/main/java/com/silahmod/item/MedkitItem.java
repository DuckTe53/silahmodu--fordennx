package com.silahmod.item;

import com.silahmod.Sfx;
import com.silahmod.WeaponConfig;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Med kit. Sag tik basili tutulur; sure dolunca can verir ve (survivalda) 1 adet harcanir. */
public class MedkitItem extends Item {
	public MedkitItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (player.getHealth() >= player.getMaxHealth()) {
			if (player instanceof ServerPlayer serverPlayer) {
				serverPlayer.displayClientMessage(Component.translatable("message.silahmod.health_full"), true);
			}
			return InteractionResult.FAIL; // cani dolu: harcama
		}
		player.startUsingItem(hand);
		return InteractionResult.CONSUME;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return WeaponConfig.MEDKIT_USE_TICKS;
	}

	/** Kullanma suresi dolunca: iyiles ve 1 adet harca. */
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (entity instanceof ServerPlayer player && player.level() instanceof ServerLevel serverLevel) {
			player.heal(WeaponConfig.MEDKIT_HEAL);
			serverLevel.sendParticles(ParticleTypes.HEART, player.getX(), player.getY() + 1.0, player.getZ(),
					6, 0.4, 0.4, 0.4, 0.0);
			Sfx.play(serverLevel, player.getX(), player.getY(), player.getZ(), "entity.experience_orb.pickup", 1.0F, 0.8F);
			player.displayClientMessage(Component.translatable("message.silahmod.healed"), true);
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
		}
		return stack;
	}
}
