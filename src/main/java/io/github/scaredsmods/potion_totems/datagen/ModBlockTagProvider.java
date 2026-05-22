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
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {

	public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, PotionTotems.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
				.add(ModBlocks.INFUSER.get())
				.add(ModBlocks.ADVANCED_INFUSER.get());

		tag(BlockTags.NEEDS_STONE_TOOL)
				.add(ModBlocks.INFUSER.get())
				.add(ModBlocks.ADVANCED_INFUSER.get())
				.add(ModBlocks.INFUSER_FRAME.get());

		tag(BlockTags.MINEABLE_WITH_AXE)
				.add(ModBlocks.INFUSER_FRAME.get());
	}
}
