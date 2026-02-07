/*
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
package io.github.scaredsmods.potion_totems.data.gen;


import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = PotionTotems.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent e) {
		DataGenerator generator = e.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper exFileHelper = e.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> provider = e.getLookupProvider();

		generator.addProvider(e.includeServer(), new ModRecipesProvider(packOutput, provider));
		generator.addProvider(e.includeClient(), new ModItemModelProvider(packOutput, exFileHelper));
		generator.addProvider(true, new ModEnglishLanguageProvider(packOutput));
	}

}
