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
package io.github.scaredsmods.potion_totems.compat.jei;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.compat.jei.category.CrushingRecipeCategory;
import io.github.scaredsmods.potion_totems.compat.jei.category.InfusingRecipeCategory;
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import io.github.scaredsmods.potion_totems.registry.ModRecipes;
import io.github.scaredsmods.potion_totems.screen.CrusherScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.List;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {

	private static RecipeMap syncedRecipes = RecipeMap.EMPTY;

	@Override
	public Identifier getPluginUid() {
		return PotionTotems.id("jei_plugin");
	}

	// From Occultism
	// Under MIT License
	@SuppressWarnings({"unchecked", "rawtypes"})
	private <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeMap recipeMap, RecipeType<T> type) {
		return (List) recipeMap.byType(type);
	}


	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new CrushingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new InfusingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(ModJEIRecipeTypes.INFUSING, this.getRecipes(syncedRecipes, ModRecipes.INFUSER_RECIPE_TYPE.get()));
		registration.addRecipes(ModJEIRecipeTypes.CRUSHING, this.getRecipes(syncedRecipes, ModRecipes.CRUSHING_RECIPE_TYPE.get()));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(CrusherScreen.class, 74, 30, 22, 20,
				ModJEIRecipeTypes.CRUSHING);

		registration.addRecipeClickArea(CrusherScreen.class, 74, 30, 22, 20,
				ModJEIRecipeTypes.INFUSING);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addCraftingStation(ModJEIRecipeTypes.CRUSHING, new ItemStack(ModBlocks.CRUSHER.get().asItem()));
		registration.addCraftingStation(ModJEIRecipeTypes.INFUSING, new ItemStack(ModBlocks.INFUSER.get().asItem()));
	}

	@EventBusSubscriber(modid = PotionTotems.MOD_ID)
	public static class ServerRecipeSync {
		@SubscribeEvent
		public static void onDatapackSync(OnDatapackSyncEvent event) {
			event.sendRecipes(ModRecipes.CRUSHING_RECIPE_TYPE.get(), ModRecipes.INFUSER_RECIPE_TYPE.get());
		}
	}

	@EventBusSubscriber(modid = PotionTotems.MOD_ID, value = Dist.CLIENT)
	public static class ClientRecipeSync {
		@SubscribeEvent
		public static void onRecipeReceived(RecipesReceivedEvent event) {
			syncedRecipes = event.getRecipeMap();
		}
	}
}
