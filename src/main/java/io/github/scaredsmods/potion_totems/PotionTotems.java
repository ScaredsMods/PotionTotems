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
package io.github.scaredsmods.potion_totems;

import io.github.scaredsmods.potion_totems.registry.*;
import io.github.scaredsmods.potion_totems.pack.Resourcepack;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@Mod(PotionTotems.MOD_ID)
public class PotionTotems {

	public static final String MOD_ID = "potion_totems";
	public static final Logger LOGGER = LoggerFactory.getLogger("PotionTotems");
	public static final Resourcepack GENERATED_PACK = new Resourcepack("PotionTotemsExtraAssets", "Adds more assets to PotionTotems.", 34);

	/*TODO:
		- Re-add BER
		- Re-add VillagerTrades
	*/

	public PotionTotems(IEventBus bus, ModContainer container) throws IOException {
		ModItems.ITEMS.init();
		ModItems.TABS.init();
		ModBlocks.BLOCKS.init();
		ModBlockEntities.TYPES.init();
		ModMenuTypes.MENUS.init();
		ModPotions.POTIONS.init();
		ModVillagers.VILLAGER_PROFESSIONS.init();
		ModVillagers.POI_TYPES.init();
		ModConfigs.init();
	}

	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}

	public static <T> ResourceKey<T> key(String name, ResourceKey<Registry<T>> key) {
		return ResourceKey.create(key, PotionTotems.id(name));
	}

}
