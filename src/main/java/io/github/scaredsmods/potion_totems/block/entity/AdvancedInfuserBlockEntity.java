/*
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

import io.github.scaredsmods.potion_totems.init.ModBlockEntities;
import io.github.scaredsmods.potion_totems.init.ModItems;
import io.github.scaredsmods.potion_totems.screen.menu.AdvancedInfuserMenu;
import io.github.scaredsmods.potion_totems.util.PotionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class AdvancedInfuserBlockEntity extends BaseInfuserBlockEntity {

	public final ItemStackHandler stackHandler = new ItemStackHandler(4) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
			if (!(level == null) && !level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
	};


	//Infused Totem input  -> Infused totem output
	//Potion input -> Glass bottle output
	public static final int INFUSED_TOTEM_INPUT_SLOT = 0;
	public static final int POTION_INPUT_SLOT = 1;
	public static final int INFUSED_TOTEM_OUTPUT_SLOT = 2;
	public static final int BOTTLE_OUTPUT_SLOT = 3;
	private int maxProgress = 1200;
	private int currentProgress = 0;

	private final ContainerData data = new ContainerData() {
		@Override
		public int get(int index) {

			return switch (index) {
				case 0 -> AdvancedInfuserBlockEntity.this.currentProgress;
				case 1 -> AdvancedInfuserBlockEntity.this.maxProgress;
				default -> 2;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index){
				case 0: AdvancedInfuserBlockEntity.this.currentProgress = value;
				case 1: AdvancedInfuserBlockEntity.this.maxProgress = value;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
		if(hasRecipe(level, blockPos)) {
			increaseCraftingProgress();
			setChanged(level, blockPos, blockState);

			if(hasCraftingFinished()) {
				craftItem();
				resetProgress();
			}
		} else {
			resetProgress();
		}
	}
	public AdvancedInfuserBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.BE_ADVANCED_INFUSER.get(), pos, blockState, Component.translatable("potion_totems.be.advanced_infuser.name"));
	}
	protected void craftItem() {
		ItemStack in1 = stackHandler.getStackInSlot(INFUSED_TOTEM_INPUT_SLOT);     // TOTEM
		ItemStack in2 = stackHandler.getStackInSlot(POTION_INPUT_SLOT);    // POTION
		ItemStack output1 = new ItemStack(ModItems.INFUSED_TOTEM.get(), 1);
		ItemStack output2 = new ItemStack(Items.GLASS_BOTTLE, 1);
		PotionContents contents = in1.get(DataComponents.POTION_CONTENTS);

		if (contents.hasEffects()) {
			PotionUtils.copyContents(output1, in1, in2);
		} else {
			PotionUtils.copyContents(in2, output1);
		}
		stackHandler.extractItem(INFUSED_TOTEM_INPUT_SLOT, 1, false);
		stackHandler.extractItem(POTION_INPUT_SLOT, 1, false);

		ItemStack currentOutput1 = stackHandler.getStackInSlot(INFUSED_TOTEM_OUTPUT_SLOT);
		if (currentOutput1.isEmpty()) {
			stackHandler.setStackInSlot(INFUSED_TOTEM_OUTPUT_SLOT, output1);
		} else {

			currentOutput1.grow(1);
		}
		ItemStack currentOutput2 = stackHandler.getStackInSlot(BOTTLE_OUTPUT_SLOT);
		if (currentOutput2.isEmpty()) {
			stackHandler.setStackInSlot(BOTTLE_OUTPUT_SLOT, output2);
		} else {
			currentOutput2.grow(1);
		}
	}


	protected boolean hasCraftingFinished() {
		return this.currentProgress >= this.maxProgress;
	}

	protected void increaseCraftingProgress() {
		currentProgress++;
	}

	protected void resetProgress() {
		currentProgress = 0;
		maxProgress = 1200;
	}

	public void drops() {
		SimpleContainer inventory = new SimpleContainer(stackHandler.getSlots());
		for (int i = 0; i < stackHandler.getSlots(); i++) {
			inventory.setItem(i, stackHandler.getStackInSlot(i));
		}
		Containers.dropContents(this.level, this.worldPosition, inventory);
	}
	protected boolean hasRecipe(Level level, BlockPos pos) {

		ItemStack in1 = stackHandler.getStackInSlot(INFUSED_TOTEM_INPUT_SLOT);
		ItemStack in2 = stackHandler.getStackInSlot(POTION_INPUT_SLOT);

		ItemStack output2 = new ItemStack(Items.GLASS_BOTTLE);
		ItemStack output1 = new ItemStack(ModItems.INFUSED_TOTEM.get());

		return (in1.is(ModItems.INFUSED_TOTEM.get()) && canInsertIntoSlot(output1, INFUSED_TOTEM_OUTPUT_SLOT, output1.getCount(), stackHandler))
				&&
				(in2.is(Items.POTION) && canInsertIntoSlot(output2, BOTTLE_OUTPUT_SLOT, output2.getCount(), stackHandler));
	}
	@Override
	public void setChanged() {
		super.setChanged();
		if (level != null && level.isClientSide) {
			// Request a re-render when data changes
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
		}
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new AdvancedInfuserMenu(containerId, playerInventory, this, this.data);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		stackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
		currentProgress = tag.getInt("potion_totems.advanced_infuser.currentProgress");
		maxProgress = tag.getInt("potion_totems.advanced_infuser.max_progress");
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		tag.put("inventory", stackHandler.serializeNBT(registries));
		tag.putInt("potion_totems.advanced_infuser.currentProgress", currentProgress);
		tag.putInt("potion_totems.advanced_infuser.max_progress", maxProgress);

		super.saveAdditional(tag, registries);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveWithoutMetadata(registries);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
		loadAdditional(pkt.getTag(), lookupProvider);
	}
}
