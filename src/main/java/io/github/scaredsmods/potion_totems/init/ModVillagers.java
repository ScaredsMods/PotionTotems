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

import com.google.common.collect.ImmutableSet;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;


public class ModVillagers {
	public static final ResourcefulRegistry<PoiType> POI_TYPES = ResourcefulRegistries.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, PotionTotems.MOD_ID);
	public static final ResourcefulRegistry<VillagerProfession> VILLAGER_PROFESSIONS = ResourcefulRegistries.create(BuiltInRegistries.VILLAGER_PROFESSION, PotionTotems.MOD_ID);
	public static final HolderRegistryEntry<PoiType> TOTEM_MASTER_POI = POI_TYPES.registerHolder("totem_master_poi", () ->
			new PoiType(ImmutableSet.copyOf(ModBlocks.INFUSER.get().getStateDefinition().getPossibleStates()), 1, 1));

	public static final HolderRegistryEntry<VillagerProfession> TOTEM_MASTER = VILLAGER_PROFESSIONS.registerHolder("totem_master", () ->
			new VillagerProfession(Component.literal("totem_master"), holder -> holder.value() == TOTEM_MASTER_POI.holder().value(),
					poiTypeHolder -> poiTypeHolder.value() == TOTEM_MASTER_POI.holder().value(), ImmutableSet.of(), ImmutableSet.of(),
					SoundEvents.VILLAGER_WORK_CLERIC));

}
