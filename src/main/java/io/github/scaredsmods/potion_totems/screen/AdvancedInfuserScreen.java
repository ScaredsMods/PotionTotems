/*
	Copyright (C) 2025 ScaredRabbitNL

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program. If not, see <https://www.gnu.org/licenses/>.
*/
package io.github.scaredsmods.potion_totems.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.screen.menu.AdvancedInfuserMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AdvancedInfuserScreen extends AbstractContainerScreen<AdvancedInfuserMenu> {


	private static final ResourceLocation GUI_TEXTURE = PotionTotems.id("textures/gui/advanced_infuser/advanced_infuser_gui.png");
	private static final ResourceLocation ARROW_TEXTURE = PotionTotems.id("textures/gui/advanced_infuser/advanced_infuser_arrow_progress.png");


	public AdvancedInfuserScreen(AdvancedInfuserMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI_TEXTURE);

		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;

		guiGraphics.blit(GUI_TEXTURE, x ,y ,0 ,0, imageWidth, imageHeight);
		renderProgressArrow(guiGraphics, x,y);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	private void renderProgressArrow(GuiGraphics graphics, int x, int y) {
		if (menu.isCrafting()) {
			graphics.blit(ARROW_TEXTURE, x + 35, y + 14, 0, 0 , menu.getScaledArrowProgress(), 57, 96, 57);
		}
	}

}
