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

import io.github.scaredsmods.potion_totems.registry.ModPotions;
import io.github.scaredsmods.potion_totems.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PotionTagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.alchemy.Potions;

import java.util.concurrent.CompletableFuture;

public class ModPotionTagProvider extends PotionTagsProvider {
	public ModPotionTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		tag(ModTags.Potions.TOTEM_MASTER_LEVEL_1_POTIONS)
				.add(Potions.INFESTED)
				.add(Potions.OOZING)
				.add(Potions.LUCK)
				.add(Potions.LEAPING)
				.add(Potions.HEALING);

		tag(ModTags.Potions.TOTEM_MASTER_LEVEL_2_POTIONS)
				.add(Potions.NIGHT_VISION)
				.add(Potions.STRONG_REGENERATION)
				.add(Potions.POISON)
				.add(TagEntry.element(ModPotions.AGGRESSION.getId()));
	}
}
