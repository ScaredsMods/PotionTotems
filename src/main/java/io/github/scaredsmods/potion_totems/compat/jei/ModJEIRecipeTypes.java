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
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ModJEIRecipeTypes {
	/*

	public static final IRecipeType<RecipeHolder<InfuserRecipe>> INFUSER =
			create(PotionTotems.MOD_ID, "infusing", InfuserRecipe.class);
	public static final IRecipeType<RecipeHolder<AdvancedInfuserRecipe>> ADVANCED_INFUSER =
			create(PotionTotems.MOD_ID, "advanced_infusing", AdvancedInfuserRecipe.class);

	 */

	// From Occultism: https://github.com/klikli-dev/occultism/blob/version/26.1.2/src/main/java/com/klikli_dev/occultism/integration/jei/impl/JeiRecipeTypes.java
	// Under MIT-License
	public static <R extends Recipe<?>> IRecipeType<RecipeHolder<R>> create(String name) {
		Identifier uid = PotionTotems.id(name);
		@SuppressWarnings({"unchecked", "RedundantCast"})
		Class<? extends RecipeHolder<R>> holderClass = (Class<? extends RecipeHolder<R>>) (Object) RecipeHolder.class;
		return IRecipeType.create(uid, holderClass);
	}
}
