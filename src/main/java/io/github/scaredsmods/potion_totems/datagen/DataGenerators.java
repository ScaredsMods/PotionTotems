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
import net.minecraft.core.HolderLookup;
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
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = PotionTotems.MOD_ID)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherClientData(GatherDataEvent.Client e) {
		DataGenerator generator = e.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> provider = e.getLookupProvider();

		generator.addProvider(true, new ModRecipesProvider.Runner(packOutput, provider));
		generator.addProvider(true, new ModModelProvider(packOutput));
		generator.addProvider(true, new ModEnglishLanguageProvider(packOutput));
		generator.addProvider(true, new ModDataPackProvider(packOutput, provider));
		generator.addProvider(true , new LootTableProvider(packOutput, Collections.emptySet(),
				List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), provider));
		BlockTagsProvider blockTagsProvider = new ModTagProvider.Blocks(packOutput, provider);
		generator.addProvider(true, blockTagsProvider);
	}

	@SubscribeEvent
	public static void gatherServerData(GatherDataEvent.Server e) {
		DataGenerator generator = e.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> provider = e.getLookupProvider();

		generator.addProvider(true, new ModRecipesProvider.Runner(packOutput, provider));
		generator.addProvider(true, new ModModelProvider(packOutput));
		generator.addProvider(true, new ModEnglishLanguageProvider(packOutput));
		generator.addProvider(true, new ModDataPackProvider(packOutput, provider));
		generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
				List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), provider));
		BlockTagsProvider blockTagsProvider = new ModTagProvider.Blocks(packOutput, provider);
		generator.addProvider(true, blockTagsProvider);
	}

}
