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

import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import io.github.scaredsmods.potion_totems.registry.ModItems;
import io.github.scaredsmods.potion_totems.tint.item.ItemFromPotion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public class ModModelProvider extends ModelProvider {

	public ModModelProvider(PackOutput output ) {
		super(output, PotionTotems.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		itemModels.generateLayeredItem(ModItems.INFUSED_TOTEM.get(), new Material(PotionTotems.id("item/totem_base")), new Material(PotionTotems.id("item/totem_eye")));
		itemModels.generateFlatItem(ModItems.INFUSER_TOTEM_PH_1.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ModItems.INFUSER_TOTEM_PH_2.get(), ModelTemplates.FLAT_ITEM);

		itemModels.itemModelOutput.accept(
				ModItems.INFUSED_TOTEM.get(),
				new CuboidItemModelWrapper.Unbaked(
						ModelLocationUtils.getModelLocation(ModItems.INFUSED_TOTEM.get()),
						Optional.empty(),
						List.of(new ItemFromPotion(0x000000))
				)
		);
		itemModels.generateFlatItem(ModItems.INFUSER_CORE.get(), ModelTemplates.FLAT_ITEM);
		blockModels.createTrivialCube(ModBlocks.INFUSER_FRAME.get());


	}

	@Override
	protected Stream<? extends Holder<Block>> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream()
				.filter(e -> e instanceof HolderRegistryEntry<Block>)
				.map(e -> ((HolderRegistryEntry<Block>) e).holder())
				.filter(h -> !h.is(ModBlocks.INFUSER.holder()) && !h.is(ModBlocks.ADVANCED_INFUSER.holder()));
	}

	@Override
	protected Stream<? extends Holder<Item>> getKnownItems() {
		return ModItems.ITEMS.getEntries().stream()
				.filter(e -> e instanceof HolderRegistryEntry<Item>)
				.map(e -> ((HolderRegistryEntry<Item>) e).holder())
				.filter(h -> !h.is(PotionTotems.id("infuser")) && !h.is(PotionTotems.id("advanced_infuser")) && !h.is(PotionTotems.id("infuser_frame")));

	}
}
