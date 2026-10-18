package com.silahmod;

import java.util.function.Function;

import com.silahmod.item.KnifeItem;
import com.silahmod.item.RevolverItem;
import com.silahmod.item.RpgItem;
import com.silahmod.item.SniperItem;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class ModItems {
	private ModItems() {}

	// Menu ve creative sekmesindeki sira: sniper, rpg, revolver, bicak
	public static final SniperItem SNIPER = register("sniper", SniperItem::new,
			new Item.Properties().stacksTo(1)
					.component(ModComponents.AMMO, new AmmoData(WeaponConfig.SNIPER_MAG, WeaponConfig.SNIPER_RESERVE)));

	public static final RpgItem RPG = register("rpg", RpgItem::new,
			new Item.Properties().stacksTo(1)
					.component(ModComponents.AMMO, new AmmoData(WeaponConfig.RPG_MAG, WeaponConfig.RPG_RESERVE)));

	public static final RevolverItem REVOLVER = register("revolver", RevolverItem::new,
			new Item.Properties().stacksTo(1)
					.component(ModComponents.AMMO, new AmmoData(WeaponConfig.REVOLVER_MAG, WeaponConfig.REVOLVER_RESERVE)));

	public static final KnifeItem KNIFE = register("knife", KnifeItem::new,
			new Item.Properties().stacksTo(1));

	public static final ResourceKey<CreativeModeTab> TAB_KEY =
			ResourceKey.create(Registries.CREATIVE_MODE_TAB, SilahMod.id("silahlar"));

	private static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, SilahMod.id(name));
		return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(properties.setId(key)));
	}

	/** 0 = sniper, 1 = rpg, 2 = revolver, 3 = bicak */
	public static Item byIndex(int index) {
		return switch (index) {
			case 0 -> SNIPER;
			case 1 -> RPG;
			case 2 -> REVOLVER;
			case 3 -> KNIFE;
			default -> null;
		};
	}

	public static void init() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, FabricItemGroup.builder()
				.title(Component.translatable("itemGroup.silahmod.silahlar"))
				.icon(() -> new ItemStack(SNIPER))
				.displayItems((context, entries) -> {
					// Creative'den alinan silahlar dolu mermiyle gelir
					entries.accept(SNIPER.createFull());
					entries.accept(RPG.createFull());
					entries.accept(REVOLVER.createFull());
					entries.accept(KNIFE);
				})
				.build());
	}
}
