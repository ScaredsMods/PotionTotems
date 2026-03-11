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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jetbrains.annotations.Nullable;

public abstract class BaseInfuserBlockEntity extends BlockEntity implements MenuProvider {

	private final Component displayName;

	public BaseInfuserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		this(type, pos, state, null);
	}

	public BaseInfuserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, Component displayName) {
		super(type, pos, blockState);
		this.displayName = displayName;
	}

	@Override
	public Component getDisplayName() {
		return this.displayName;
	}


	public boolean canInsertIntoSlot (ItemStack output, int slot, int count, ItemStacksResourceHandler itemStackHandler) {
		return canInsertItemIntoOutputSlot(output, slot, itemStackHandler) && canInsertAmountIntoOutputSlot(count, slot, itemStackHandler);
	}

	public boolean canInsertItemIntoOutputSlot(ItemStack output, int slot, ItemStacksResourceHandler itemStackHandler) {
		return itemStackHandler.getResource(slot).isEmpty() ||
				itemStackHandler.getResource(slot).getItem() == output.getItem();
	}
	public boolean canInsertAmountIntoOutputSlot(int count, int slot, ItemStacksResourceHandler itemStackHandler) {
		int maxCount = itemStackHandler.getResource(slot).isEmpty() ? 64 : itemStackHandler.getResource(slot).getMaxStackSize();
		int currentCount = itemStackHandler.getAmountFrom(itemStackHandler.getResource(slot).toStack());

		return maxCount >= currentCount + count;
	}

	protected abstract boolean hasRecipe(Level level, BlockPos pos);
	protected abstract boolean hasCraftingFinished();
	protected abstract void craftItem();
	protected abstract void increaseCraftingProgress();
	protected abstract void resetProgress();
	public abstract void drops();

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
