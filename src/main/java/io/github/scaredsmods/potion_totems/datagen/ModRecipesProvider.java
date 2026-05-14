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
package io.github.scaredsmods.potion_totems.datagen;

import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider   {


	protected ModRecipesProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
		super(registries, recipeOutput);
	}

	@Override
	protected void buildRecipes() {
		shaped(RecipeCategory.BREWING, ModBlocks.INFUSER.get())
				.pattern(" T ")
				.pattern("LLL")
				.pattern("LBL")
				.define('T', Items.TOTEM_OF_UNDYING)
				.define('B', Items.GLASS_BOTTLE)
				.define('L', ItemTags.LOGS)
				.unlockedBy("has_totem", has(Items.TOTEM_OF_UNDYING))
				.unlockedBy("has_bottle", has(Items.GLASS_BOTTLE))
				.unlockedBy("has_log", has(ItemTags.LOGS))
				.save(output);

		shaped(RecipeCategory.BREWING, ModBlocks.ADVANCED_INFUSER.get())
				.pattern("TTT")
				.pattern("LLL")
				.pattern("LBL")
				.define('T', Items.TOTEM_OF_UNDYING)
				.define('B', Items.GLASS_BOTTLE)
				.define('L', ItemTags.LOGS)
				.unlockedBy("has_totem", has(Items.TOTEM_OF_UNDYING))
				.unlockedBy("has_bottle", has(Items.GLASS_BOTTLE))
				.unlockedBy("has_log", has(ItemTags.LOGS))
				.save(output);
	}

	public static class Runner extends RecipeProvider.Runner {

		protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
			super(packOutput, registries);
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
			return new ModRecipesProvider(registries, output);
		}

		@Override
		public String getName() {
			return "PotionTotems - Recipes";
		}
	}
}
