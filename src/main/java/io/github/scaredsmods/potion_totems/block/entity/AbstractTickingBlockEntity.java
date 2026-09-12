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
package io.github.scaredsmods.potion_totems.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class AbstractTickingBlockEntity extends BlockEntity implements MenuProvider {

	private final Component displayName;

	public AbstractTickingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		this(type, pos, state, null);
	}

	public AbstractTickingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, Component displayName) {
		super(type, pos, blockState);
		this.displayName = displayName;
	}

	@Override
	public Component getDisplayName() {
		return this.displayName;
	}

	// Single overload - ItemStackHandler is gone in 26.1, everyone uses ItemStacksResourceHandler
	public boolean canInsertIntoSlot(List<ItemStack> outputs, ItemStacksResourceHandler itemStacksResourceHandler) {
		return canInsertItemIntoOutputSlot(outputs, itemStacksResourceHandler)
				&& canInsertAmountIntoOutputSlot(outputs, itemStacksResourceHandler);
	}

	public boolean canInsertIntoSlot(ItemStack output, ItemStacksResourceHandler itemStacksResourceHandler) {
		List<ItemStack> outputs = List.of(output);
		return canInsertItemIntoOutputSlot(outputs, itemStacksResourceHandler)
				&& canInsertAmountIntoOutputSlot(outputs, itemStacksResourceHandler);
	}

	private boolean canInsertItemIntoOutputSlot(List<ItemStack> outputs, ItemStacksResourceHandler itemStacksResourceHandler) {
		for(int i = 0; i < outputs.size(); i++) {
			if(!(itemStacksResourceHandler.getResource(i).isEmpty() ||
					itemStacksResourceHandler.getResource(i).getItem() == outputs.get(i).getItem())) {
				return false;
			}
		}
		return true;
	}

	private boolean canInsertAmountIntoOutputSlot(List<ItemStack> outputs, ItemStacksResourceHandler itemStacksResourceHandler) {
		int maxCount;
		int currentCount;

		for(int i = 0; i < outputs.size(); i++) {
			maxCount = itemStacksResourceHandler.getResource(i).isEmpty() ? 64
					: itemStacksResourceHandler.getResource(i).getMaxStackSize();
			currentCount = itemStacksResourceHandler.getAmountAsInt(i);

			if(!(maxCount >= currentCount + outputs.get(i).getCount())) {
				return false;
			}
		}
		return true;
	}

	public abstract boolean hasRecipe();
	public abstract boolean hasCraftingFinished();
	public abstract void craftItem();
	public abstract void increaseCraftingProgress();
	public abstract void resetProgress();
	public abstract void drops();
	public abstract boolean isOutputSlotEmptyOrReceivable();
	@SuppressWarnings("unchecked")
	public <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> getCurrentRecipe() {
		return Optional.empty();
	}

	@Override
	public void onDataPacket(Connection net, ValueInput valueInput) {
		loadAdditional(valueInput);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveWithoutMetadata(registries);
	}
}
