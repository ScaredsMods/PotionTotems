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

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.component.TotemFragmentComponent;
import io.github.scaredsmods.potion_totems.datagen.recipe.CrusherRecipeBuilder;
import io.github.scaredsmods.potion_totems.datagen.recipe.InfusingRecipeBuilder;
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import io.github.scaredsmods.potion_totems.registry.ModDataComponents;
import io.github.scaredsmods.potion_totems.registry.ModItems;
import io.github.scaredsmods.potion_totems.registry.ModRecipeBookCategories;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider   {


	protected ModRecipesProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
		super(registries, recipeOutput);
	}

	@Override
	protected void buildRecipes() {
		shaped(RecipeCategory.BREWING, ModBlocks.INFUSER.get())
				.pattern("DDD")
				.pattern("TCB")
				.pattern("DFD")
				.define('T', Items.TOTEM_OF_UNDYING)
				.define('B', Items.GLASS_BOTTLE)
				.define('F', ModBlocks.INFUSER_FRAME.get())
				.define('D', Items.BLUE_DYE)
				.define('C', ModItems.MACHINE_CORE.get())
				.unlockedBy("has_totem", has(Items.TOTEM_OF_UNDYING))
				.unlockedBy("has_bottle", has(Items.GLASS_BOTTLE))
				.unlockedBy("has_infuser_frame", has(ModBlocks.INFUSER_FRAME.get()))
				.unlockedBy("has_blue_dye", has(Items.BLUE_DYE))
				.unlockedBy("has_machine_core", has(ModItems.MACHINE_CORE.get()))
				.save(output);

		shaped(RecipeCategory.BREWING, ModBlocks.ADVANCED_INFUSER.get())
				.pattern("RRR")
				.pattern("TCB")
				.pattern("RFR")
				.define('T', Items.TOTEM_OF_UNDYING)
				.define('B', Items.GLASS_BOTTLE)
				.define('F', ModBlocks.INFUSER_FRAME.get())
				.define('R', Items.RED_DYE)
				.define('C', ModItems.MACHINE_CORE.get())
				.unlockedBy("has_totem", has(Items.TOTEM_OF_UNDYING))
				.unlockedBy("has_bottle", has(Items.GLASS_BOTTLE))
				.unlockedBy("has_infuser_frame", has(ModBlocks.INFUSER_FRAME.get()))
				.unlockedBy("has_red_dye", has(Items.RED_DYE))
				.unlockedBy("has_machine_core", has(ModItems.MACHINE_CORE.get()))
				.save(output);

		shaped(RecipeCategory.DECORATIONS, ModBlocks.INFUSER_FRAME.get())
				.pattern("   ")
				.pattern("LLL")
				.pattern("L L")
				.define('L', ItemTags.LOGS)
				.unlockedBy("has_logs", has(ItemTags.LOGS))
				.save(output);

		shaped(RecipeCategory.MISC, ModItems.MACHINE_CORE.get())
				.pattern("BIL")
				.pattern("INI")
				.pattern("LIB")
				.define('B', Items.BLUE_DYE)
				.define('I', Items.NETHERITE_INGOT)
				.define('L', Items.LIGHT_BLUE_DYE)
				.define('N', Items.NETHER_STAR)
				.unlockedBy("has_blue_dye", has(Items.BLUE_DYE))
				.unlockedBy("has_light_blue_dye", has(Items.LIGHT_BLUE_DYE))
				.unlockedBy("has_netherite_ingot", has(Items.NETHERITE_INGOT))
				.unlockedBy("has_nether_star", has(Items.NETHER_STAR))
				.save(output);

		shaped(RecipeCategory.MISC, ModBlocks.CRUSHER.get())
				.pattern("GGG")
				.pattern("LCL")
				.pattern("L L")
				.define('G', Items.GLASS)
				.define('L', ItemTags.LOGS)
				.define('C', ModItems.MACHINE_CORE.get())
				.unlockedBy("has_glass", has(Items.GLASS))
				.unlockedBy("has_logs", has(ItemTags.LOGS))
				.unlockedBy("has_machine_core", has(ModItems.MACHINE_CORE.get()))
				.save(output);


		InfusingRecipeBuilder.infusing(RecipeCategory.MISC,
						List.of(
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(1)).build())),
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(2)).build())),
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(3)).build())),
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(4)).build())),
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(5)).build())),
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(6)).build())),
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(7)).build())),
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(8)).build())),
								DataComponentIngredient.of(true, new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(9)).build())))
						,List.of(
				new ItemStackTemplate(ModItems.INFUSED_TOTEM.get()),
				new ItemStackTemplate(Items.GLASS_BOTTLE)
				))
				.unlockedBy("has_totem_fragment", has(ModItems.TOTEM_FRAGMENT.get())).save(output, PotionTotems.resourceKey(Registries.RECIPE, "infused_totem_from_infusing"));

		CrusherRecipeBuilder.crushing(RecipeCategory.MISC, Ingredient.of(Items.TOTEM_OF_UNDYING), List.of(
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(1)).build()),
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(2)).build()),
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(3)).build()),
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(4)).build()),
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(5)).build()),
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(6)).build()),
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(7)).build()),
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(8)).build()),
				new ItemStackTemplate(ModItems.TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(9)).build())
		)).unlockedBy("has_totem_of_undying", has(Items.TOTEM_OF_UNDYING)).save(output);

		CrusherRecipeBuilder.crushing(RecipeCategory.MISC, Ingredient.of(ModItems.INFUSED_TOTEM.get()), List.of(
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(1)).build()),
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(2)).build()),
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(3)).build()),
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(4)).build()),
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(5)).build()),
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(6)).build()),
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(7)).build()),
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(8)).build()),
				new ItemStackTemplate(ModItems.INFUSED_TOTEM_FRAGMENT.get(), 1, DataComponentPatch.builder().set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(9)).build())
		)).unlockedBy("has_infused_totem", has(ModItems.INFUSED_TOTEM.get())).save(output);
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
