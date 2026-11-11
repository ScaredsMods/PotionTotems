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
package io.github.scaredsmods.potion_totems.event;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.registry.ModPacks;
import io.github.scaredsmods.potion_totems.registry.ModPotions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.resource.ResourcePackLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@EventBusSubscriber(modid = PotionTotems.MOD_ID)
public class ModGameEvents {

	@SubscribeEvent
	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addMix(Potions.WATER, Items.NETHERITE_INGOT, ModPotions.POSITIVE.holder());
		event.getBuilder().addMix(Potions.WATER, Items.NETHER_STAR, ModPotions.NEGATIVE.holder());
		event.getBuilder().addMix(Potions.WATER, Items.DRAGON_HEAD, ModPotions.NEUTRAL.holder());
	}

}
