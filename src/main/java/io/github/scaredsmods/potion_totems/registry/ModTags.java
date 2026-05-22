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
package io.github.scaredsmods.potion_totems.registry;

import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.trading.VillagerTrade;

public class ModTags {


	public static class Trades {
		public static final TagKey<VillagerTrade> TOTEM_MASTER_LEVEL_1 = createTag("totem_master/level_1");
		public static final TagKey<VillagerTrade> TOTEM_MASTER_LEVEL_2 = createTag("totem_master/level_2");

		private static TagKey<VillagerTrade> createTag(String name) {
			return TagKey.create(Registries.VILLAGER_TRADE, PotionTotems.id(name));
		}
	}

	public static class Potions {
		public static final TagKey<Potion> TOTEM_MASTER_LEVEL_1_POTIONS =  createTag("totem_master/level_1");
		public static final TagKey<Potion> TOTEM_MASTER_LEVEL_2_POTIONS =  createTag("totem_master/level_2");

		private static TagKey<Potion> createTag(String name) {
			return TagKey.create(Registries.POTION, PotionTotems.id(name));
		}
	}
}
