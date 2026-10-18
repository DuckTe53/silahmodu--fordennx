package com.silahmod.item;

import com.silahmod.RocketManager;
import com.silahmod.WeaponConfig;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class RpgItem extends GunItem {
	public RpgItem(Properties properties) {
		super(properties, WeaponConfig.RPG_MAG, WeaponConfig.RPG_RESERVE,
				WeaponConfig.RPG_FIRE_DELAY, WeaponConfig.RPG_RELOAD);
	}

	@Override
	public void fire(ServerPlayer player, ServerLevel level) {
		RocketManager.launch(level, player);
	}
}
