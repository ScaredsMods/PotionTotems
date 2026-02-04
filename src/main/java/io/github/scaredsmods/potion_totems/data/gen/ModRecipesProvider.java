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
package io.github.scaredsmods.potion_totems.data.gen;

import io.github.scaredsmods.potion_totems.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {

	public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ModBlocks.INFUSER.get())
				.pattern(" T ")
				.pattern("LLL")
				.pattern("LBL")
				.define('T', Items.TOTEM_OF_UNDYING)
				.define('B', Items.GLASS_BOTTLE)
				.define('L', ItemTags.LOGS)
				.unlockedBy("has_totem", has(Items.TOTEM_OF_UNDYING))
				.unlockedBy("has_bottle", has(Items.GLASS_BOTTLE))
				.unlockedBy("has_log", has(ItemTags.LOGS))
				.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ModBlocks.ADVANCED_INFUSER.get())
				.pattern("TTT")
				.pattern("LLL")
				.pattern("LBL")
				.define('T', Items.TOTEM_OF_UNDYING)
				.define('B', Items.GLASS_BOTTLE)
				.define('L', ItemTags.LOGS)
				.unlockedBy("has_totem", has(Items.TOTEM_OF_UNDYING))
				.unlockedBy("has_bottle", has(Items.GLASS_BOTTLE))
				.unlockedBy("has_log", has(ItemTags.LOGS))
				.save(recipeOutput);
	}
}
