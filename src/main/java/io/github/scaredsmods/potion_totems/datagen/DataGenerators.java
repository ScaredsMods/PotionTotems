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
import io.github.scaredsmods.potion_totems.datagen.villager.ModPOITags;
import io.github.scaredsmods.potion_totems.datagen.villager.ModVillagerTradeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = PotionTotems.MOD_ID)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherClientData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		var lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new ModDatapackProvider(packOutput, lookupProvider));
		generator.addProvider(true, new ModPOITags(packOutput, lookupProvider));
		generator.addProvider(true, new ModVillagerTradeTags(packOutput, lookupProvider));
		generator.addProvider(true, new ModPotionTagProvider(packOutput, lookupProvider));
		generator.addProvider(true, new ModRecipesProvider.Runner(packOutput, lookupProvider));
		generator.addProvider(true, new ModModelProvider(packOutput));
		generator.addProvider(true, new ModEnglishLanguageProvider(packOutput));
		generator.addProvider(true , new LootTableProvider(packOutput, Collections.emptySet(),
				List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
		BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, lookupProvider);
		generator.addProvider(true, blockTagsProvider);
	}
}
