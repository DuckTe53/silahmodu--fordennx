package com.silahmod.item;

import com.silahmod.Ballistics;
import com.silahmod.WeaponConfig;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SniperItem extends GunItem {
	public SniperItem(Properties properties) {
		super(properties, WeaponConfig.SNIPER_MAG, WeaponConfig.SNIPER_RESERVE,
				WeaponConfig.SNIPER_FIRE_DELAY, WeaponConfig.SNIPER_RELOAD);
	}

	/** Sag tik basili tutulunca scope acilir (esya "kullaniliyor" durumuna girer). */
	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		player.startUsingItem(hand);
		return InteractionResult.CONSUME;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public void fire(ServerPlayer player, ServerLevel level) {
		Ballistics.shoot(level, player, WeaponConfig.SNIPER_RANGE,
				WeaponConfig.SNIPER_HEAD, WeaponConfig.SNIPER_BODY,
				"entity.firework_rocket.large_blast", 0.7F);
	}
}
