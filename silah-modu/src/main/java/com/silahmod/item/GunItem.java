package com.silahmod.item;

import com.silahmod.AmmoData;
import com.silahmod.ModComponents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Mermisi olan silahlarin ortak sinifi. Sinirsiz dayaniklilik: esyada hasar/dayaniklilik yok. */
public abstract class GunItem extends Item {
	public final int magSize;
	public final int reserveMax;
	public final int fireDelay;
	public final int reloadTicks;

	protected GunItem(Properties properties, int magSize, int reserveMax, int fireDelay, int reloadTicks) {
		super(properties);
		this.magSize = magSize;
		this.reserveMax = reserveMax;
		this.fireDelay = fireDelay;
		this.reloadTicks = reloadTicks;
	}

	/** Sunucuda atis: mermi dusme ve bekleme suresi Combat tarafindan halledilir. */
	public abstract void fire(ServerPlayer player, ServerLevel level);

	public AmmoData ammo(ItemStack stack) {
		return stack.getOrDefault(ModComponents.AMMO, new AmmoData(this.magSize, this.reserveMax));
	}

	public void setAmmo(ItemStack stack, AmmoData data) {
		stack.set(ModComponents.AMMO, data);
	}

	public ItemStack createFull() {
		ItemStack stack = new ItemStack(this);
		stack.set(ModComponents.AMMO, new AmmoData(this.magSize, this.reserveMax));
		return stack;
	}
}
