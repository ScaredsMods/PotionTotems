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


import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.registry.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.Optional;

public class ModTradeSets {

	public static final ResourceKey<TradeSet> TOTEM_MASTER_LEVEL_1 = create("totem_master/level_1");
	public static final ResourceKey<TradeSet> TOTEM_MASTER_LEVEL_2 = create("totem_master/level_2");

	public static void bootstrap(BootstrapContext<TradeSet> context) {
		register(context, TOTEM_MASTER_LEVEL_1, ModTags.Trades.TOTEM_MASTER_LEVEL_1);
		register(context, TOTEM_MASTER_LEVEL_2, ModTags.Trades.TOTEM_MASTER_LEVEL_2);
	}

	private static ResourceKey<TradeSet> create(final String id) {
		return ResourceKey.create(Registries.TRADE_SET, PotionTotems.id(id));
	}

	public static Holder.Reference<TradeSet> register(final BootstrapContext<TradeSet> context,
													final ResourceKey<TradeSet> resourceKey, final TagKey<VillagerTrade> tradeTag) {
		return register(context, resourceKey, tradeTag, ConstantValue.exactly(2.0F));
	}

	public static Holder.Reference<TradeSet> register(final BootstrapContext<TradeSet> context, final ResourceKey<TradeSet> resourceKey,
													final TagKey<VillagerTrade> tradeTag, final NumberProvider numberProvider) {
		return context.register(resourceKey, new TradeSet(context.lookup(Registries.VILLAGER_TRADE).getOrThrow(tradeTag),
				numberProvider, false, Optional.of(resourceKey.identifier().withPrefix("trade_set/"))));
	}
}
