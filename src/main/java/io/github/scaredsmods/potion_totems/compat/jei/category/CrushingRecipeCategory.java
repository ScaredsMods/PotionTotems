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
import io.github.scaredsmods.potion_totems.recipe.CrusherRecipe;
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

public class CrushingRecipeCategory implements IRecipeCategory<RecipeHolder<CrusherRecipe>> {

    private static final Identifier GUI_TEXTURE = PotionTotems.id("textures/gui/crusher/crusher_gui.png");
    private final IDrawable icon;
    private final IDrawable overlay;

    public CrushingRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(GUI_TEXTURE,0 ,0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CRUSHER.get()));
    }

    @Override
    public IRecipeType<RecipeHolder<CrusherRecipe>> getRecipeType() {
        return ModJEIRecipeTypes.CRUSHING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.potion_totems.crusher");
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
    public void draw(RecipeHolder<CrusherRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0,0);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CrusherRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 13, 32).add(recipe.value().getIngredients().getFirst());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 13).add(recipe.value().getOutputs().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 13).add(recipe.value().getOutputs().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 139,13).add(recipe.value().getOutputs().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 31).add(recipe.value().getOutputs().get(3));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 31).add(recipe.value().getOutputs().get(4));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 139,31).add(recipe.value().getOutputs().get(5));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 49).add(recipe.value().getOutputs().get(6));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 49).add(recipe.value().getOutputs().get(7));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 139,49).add(recipe.value().getOutputs().get(8));
    }
}
