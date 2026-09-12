/*
	This file is part of PotionTotems, licensed under the Lesser General Public License version 3 (LGPL-3.0)
	Copyright (C) 2025 ScaredRabbitNL

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU Lesser General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public License
	along with this program. If not, see <https://www.gnu.org/licenses/>.
*/
package io.github.scaredsmods.potion_totems.compat.jei.category;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.compat.jei.ModJEIRecipeTypes;
import io.github.scaredsmods.potion_totems.recipe.InfuserRecipe;
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

public class InfusingRecipeCategory implements IRecipeCategory<RecipeHolder<InfuserRecipe>> {

    private static final Identifier GUI_TEXTURE = PotionTotems.id("textures/gui/infuser/infuser_gui_rework.png");
    private final IDrawable icon;
    private final IDrawable overlay;

    public InfusingRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(GUI_TEXTURE,0 ,0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.INFUSER.get()));
    }
    @Override
    public IRecipeType<RecipeHolder<InfuserRecipe>> getRecipeType() {
        return ModJEIRecipeTypes.INFUSING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.potion_totems.infuser");
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(RecipeHolder<InfuserRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0,0);
    }
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<InfuserRecipe> recipe, IFocusGroup focuses) {
        builder.addInputSlot(39, 9).add(recipe.value().getIngredients().getFirst());
        builder.addInputSlot(65, 35).add(recipe.value().getIngredients().get(1));
        builder.addInputSlot(39, 61).add(recipe.value().getIngredients().get(2));
        builder.addInputSlot(13, 35).add(recipe.value().getIngredients().get(3));
        builder.addInputSlot(39, 35).add(recipe.value().getIngredients().get(4));

        builder.addOutputSlot(139, 17).add(recipe.value().getOutputs().getFirst());
        builder.addOutputSlot(139, 51).add(recipe.value().getOutputs().getLast());
    }
}
