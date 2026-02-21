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
package io.github.scaredsmods.potion_totems.init;

import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.block.AdvancedInfuserBlock;
import io.github.scaredsmods.potion_totems.block.InfuserBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
	public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, PotionTotems.MOD_ID);
	public static final HolderRegistryEntry<Block> INFUSER = registerBlock("infuser", (properties) -> new InfuserBlock(properties.noOcclusion()));
	public static final HolderRegistryEntry<Block> ADVANCED_INFUSER = registerBlock("advanced_infuser", (properties) -> new AdvancedInfuserBlock(properties.noOcclusion()));

	private static HolderRegistryEntry<Block> registerBlock(String name, Function<BlockBehaviour.Properties, Block> block) {
		ResourceKey<Block> bKey = PotionTotems.key(name, Registries.BLOCK);
		ResourceKey<Item> iKey = PotionTotems.key(name, Registries.ITEM);
		Supplier<BlockBehaviour.Properties> supplier = BlockBehaviour.Properties::of;
		HolderRegistryEntry<Block> entry = BLOCKS.registerHolder(name, () -> block.apply(supplier.get().setId(bKey)));
		ModItems.ITEMS.register(name, () -> new BlockItem(entry.get(), new Item.Properties().useBlockDescriptionPrefix().setId(iKey)));
		return entry;
	}

}
