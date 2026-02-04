/*
	Copyright (C) 2025 ScaredRabbitNL

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program. If not, see <https://www.gnu.org/licenses/>.
*/
package io.github.scaredsmods.potion_totems.init;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.block.entity.AdvancedInfuserBlockEntity;
import io.github.scaredsmods.potion_totems.block.entity.InfuserBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

	public static final ResourcefulRegistry<BlockEntityType<?>> TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PotionTotems.MOD_ID);

	public static final RegistryEntry<BlockEntityType<InfuserBlockEntity>> BE_INFUSER = TYPES.register("infuser" , () ->
			BlockEntityType.Builder.of(InfuserBlockEntity::new, ModBlocks.INFUSER.get()).build(null));

	public static final RegistryEntry<BlockEntityType<AdvancedInfuserBlockEntity>> BE_ADVANCED_INFUSER = TYPES.register("advanced_infuser", () ->
			BlockEntityType.Builder.of(AdvancedInfuserBlockEntity::new, ModBlocks.ADVANCED_INFUSER.get()).build(null));
}
