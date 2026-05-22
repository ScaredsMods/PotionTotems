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
package io.github.scaredsmods.potion_totems.datagen.villager;


import io.github.scaredsmods.potion_totems.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.tags.TagEntry;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTags extends VillagerTradesTagsProvider {

	public ModVillagerTradeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}
	@Override
	protected void addTags(HolderLookup.Provider registries) {
		getOrCreateRawBuilder(ModTags.Trades.TOTEM_MASTER_LEVEL_1)
				.add(TagEntry.element(ModVillagerTrades.TOTEM_MASTER_1_EMERALD_INFUSED_TOTEM.identifier()))
				.add(TagEntry.element(ModVillagerTrades.TOTEM_MASTER_1_EMERALD_POTION.identifier()));

		getOrCreateRawBuilder(ModTags.Trades.TOTEM_MASTER_LEVEL_2)
				.add(TagEntry.element(ModVillagerTrades.TOTEM_MASTER_2_EMERALD_INFUSED_TOTEM.identifier()))
				.add(TagEntry.element(ModVillagerTrades.TOTEM_MASTER_2_EMERALD_POTION.identifier()));
	}
}
