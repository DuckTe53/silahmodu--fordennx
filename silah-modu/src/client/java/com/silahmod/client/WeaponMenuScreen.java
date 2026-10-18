package com.silahmod.client;

import com.silahmod.ModItems;
import com.silahmod.Net;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** B tusuyla acilan silah menusu. Bir silaha tiklayinca sunucu o silahi envantere verir. */
public class WeaponMenuScreen extends Screen {
	private static final String[] NAME_KEYS = {
			"item.silahmod.sniper", "item.silahmod.rpg", "item.silahmod.revolver", "item.silahmod.knife"
	};
	private static final int BUTTON_W = 150;
	private static final int BUTTON_H = 24;
	private static final int GAP = 6;

	public WeaponMenuScreen() {
		super(Component.translatable("screen.silahmod.menu"));
	}

	private int top() {
		int total = NAME_KEYS.length * (BUTTON_H + GAP) + 30;
		return this.height / 2 - total / 2 + 10;
	}

	@Override
	protected void init() {
		int x = this.width / 2 - BUTTON_W / 2 + 10;
		int y = top();
		for (int i = 0; i < NAME_KEYS.length; i++) {
			final int index = i;
			this.addRenderableWidget(Button.builder(Component.translatable(NAME_KEYS[i]),
							button -> ClientPlayNetworking.send(new Net.GiveWeaponPayload(index)))
					.pos(x, y + i * (BUTTON_H + GAP))
					.size(BUTTON_W, BUTTON_H)
					.build());
		}
		this.addRenderableWidget(Button.builder(Component.translatable("gui.silahmod.close"), button -> this.onClose())
				.pos(x, y + NAME_KEYS.length * (BUTTON_H + GAP) + 6)
				.size(BUTTON_W, BUTTON_H)
				.build());
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.renderBackground(graphics, mouseX, mouseY, delta);
		super.render(graphics, mouseX, mouseY, delta);

		graphics.drawCenteredString(this.font, this.title, this.width / 2, top() - 22, 0xFFFFFFFF);

		// Her butonun soluna silah ikonu
		int x = this.width / 2 - BUTTON_W / 2 - 14;
		int y = top();
		for (int i = 0; i < NAME_KEYS.length; i++) {
			Item item = ModItems.byIndex(i);
			graphics.renderItem(new ItemStack(item), x, y + i * (BUTTON_H + GAP) + 4);
		}
	}
}
