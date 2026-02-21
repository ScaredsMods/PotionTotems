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

import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModResourceKeys {


	// Items
	public static final ResourceKey<Item> INFUSED_TOTEM = createItem("infused_totem");
	public static final ResourceKey<Item> INFUSER_TOTEM_PH_1 = createItem("infuser_totem_placeholder_1");
	public static final ResourceKey<Item> INFUSER_TOTEM_PH_2 = createItem("infuser_totem_placeholder_2");

	// Block Items
	public static final ResourceKey<Item> INFUSER = createItem("infuser");
	public static final ResourceKey<Item> ADVANCED_INFUSER = createItem("advanced_infuser");

	private static ResourceKey<Item> createItem(String key) {
		return ResourceKey.create(Registries.ITEM, PotionTotems.id(key));
	}
	private static ResourceKey<Block> createBlock(String key) {
		return ResourceKey.create(Registries.BLOCK, PotionTotems.id(key));
	}
}
