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

import io.github.scaredsmods.potion_totems.block.entity.AdvancedInfuserBlockEntity;
import io.github.scaredsmods.potion_totems.block.entity.InfuserBlockEntityRework;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class ModBlockTintSources {

	public static BlockTintSource infuserColor() {
		return new BlockTintSource() {

			@Override
			public int color(BlockState state) {
				return 0xFFFFFF;
			}

			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				Optional<InfuserBlockEntityRework> blockEntity = level.getBlockEntity(pos, ModBlockEntities.BE_INFUSER.get());
				if (blockEntity.isEmpty()) {
					return 0xFFFFFF;
				}
				ItemStack involvedStack;
				ItemStack potionStack = blockEntity.get().itemStacksResourceHandler.getResource(InfuserBlockEntityRework.INPUT_SLOT_5).toStack();

				if (!potionStack.isEmpty() && potionStack.has(DataComponents.POTION_CONTENTS)) {
					involvedStack = potionStack;
				} else {
					return 0xFFFFFF;
				}
				PotionContents contents = involvedStack.get(DataComponents.POTION_CONTENTS);

				if (contents == null) {
					return 0xFFFFFF;
				}
				return ARGB.opaque(involvedStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
			}
		};
	}

	public static BlockTintSource advancedInfuserColor() {
		return new BlockTintSource() {

			@Override
			public int color(BlockState state) {
				return 0xFFFFFF;
			}

			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				Optional<AdvancedInfuserBlockEntity> blockEntity =  level.getBlockEntity(pos, ModBlockEntities.BE_ADVANCED_INFUSER.get());
				if (blockEntity.isEmpty()) {
					return 0xFFFFFF;
				}
				ItemStack involvedStack;
				ItemStack potionStack = blockEntity.get().stackHandler.getResource(AdvancedInfuserBlockEntity.POTION_INPUT_SLOT).toStack();

				if (!potionStack.isEmpty() && potionStack.has(DataComponents.POTION_CONTENTS)) {
					involvedStack = potionStack;
				} else {
					return 0xFFFFFF;
				}
				PotionContents contents = involvedStack.get(DataComponents.POTION_CONTENTS);

				if (contents == null) {
					return 0xFFFFFF;
				}
				return ARGB.opaque(involvedStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
			}
		};
	}
}
