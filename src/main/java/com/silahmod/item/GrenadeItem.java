package com.silahmod.item;

import com.silahmod.GrenadeManager;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** El bombasi. Sag tikla firlatilir; patlama GrenadeManager'da. */
public class GrenadeItem extends Item {
	public GrenadeItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
			ItemStack stack = player.getItemInHand(hand);
			if (!GrenadeManager.throwGrenade(serverLevel, serverPlayer, stack)) {
				return InteractionResult.CONSUME; // bekleme suresinde: kol sallanmaz
			}
		}
		return InteractionResult.SUCCESS;
	}
}
