package io.github.scaredsmods.potion_totems.screen;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.screen.menu.CrusherMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CrusherScreen extends AbstractContainerScreen<CrusherMenu> {

    private static final Identifier GUI_TEXTURE = PotionTotems.id("textures/gui/crusher/crusher_gui.png");
    private static final Identifier ARROW_TEXTURE = PotionTotems.id("textures/gui/crusher/crusher_arrow_progress.png");

    public CrusherScreen(CrusherMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x ,y ,0 ,0, imageWidth, imageHeight, 256, 256);
        renderProgressArrow(graphics, x,y);
    }

    // TODO: Add proper arrow dimensions
    private void renderProgressArrow(GuiGraphicsExtractor graphics, int x, int y) {
        if (menu.isCrafting()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE, x + 42, y + 31, 0, 0 ,
                    40, menu.getScaledArrowProgress(),
                    40, 16);
        }
    }
}
