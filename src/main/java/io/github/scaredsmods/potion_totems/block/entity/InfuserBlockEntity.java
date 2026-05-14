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

import io.github.scaredsmods.potion_totems.registry.ModBlockEntities;
import io.github.scaredsmods.potion_totems.registry.ModItems;
import io.github.scaredsmods.potion_totems.screen.menu.InfuserMenu;
import io.github.scaredsmods.potion_totems.util.PotionUtils;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public class InfuserBlockEntity extends BaseInfuserBlockEntity {

	public final ItemStacksResourceHandler stackHandler = new ItemStacksResourceHandler(4) {
		@Override
		protected void onContentsChanged(int index, ItemStack previousContents) {
			setChanged();
			if (!level.isClientSide()) {
				level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		}
		@Override
		public boolean isValid(int index, ItemResource resource) {
			return switch (index) {
				case TOTEM_INPUT_SLOT -> resource.is(Items.TOTEM_OF_UNDYING);
				case POTION_INPUT_SLOT -> resource.is(Items.POTION);
				case INFUSED_TOTEM_OUTPUT_SLOT, BOTTLE_OUTPUT_SLOT -> false; // output only
				default -> false;
			};
		}

		@Override
		public boolean matches(ItemStack stack, ItemResource resource) {
			return super.matches(stack, resource);
		}
	};

	public static final int TOTEM_INPUT_SLOT = 0;
	public static final int POTION_INPUT_SLOT = 1;
	public static final int INFUSED_TOTEM_OUTPUT_SLOT = 2;
	public static final int BOTTLE_OUTPUT_SLOT = 3;

	private int maxProgress = 600;
	private int currentProgress = 0;

	private final ContainerData data = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0  -> InfuserBlockEntity.this.currentProgress;
				case 1  -> InfuserBlockEntity.this.maxProgress;
				default -> 2;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> InfuserBlockEntity.this.currentProgress = value;
				case 1 -> InfuserBlockEntity.this.maxProgress = value;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public InfuserBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.BE_INFUSER.get(), pos, blockState);
	}

	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
		if (hasRecipe(level, blockPos)) {
			increaseCraftingProgress();
			setChanged(level, blockPos, blockState);
			if (hasCraftingFinished()) {
				craftItem();
				resetProgress();
			}
		} else {
			resetProgress();
		}
	}

	@Override
	protected void resetProgress() {
		currentProgress = 0;
		maxProgress = 600;
	}

	@Override
	protected void increaseCraftingProgress() {
		currentProgress++;
	}

	@Override
	protected boolean hasCraftingFinished() {
		return this.currentProgress >= this.maxProgress;
	}

	@Override
	protected void craftItem() {
		ItemResource totemResource = stackHandler.getResource(TOTEM_INPUT_SLOT);
		ItemResource potionResource = stackHandler.getResource(POTION_INPUT_SLOT);

		ItemStack potionStack = potionResource.toStack();
		ItemStack output1 = new ItemStack(ModItems.INFUSED_TOTEM.get());
		ItemStack output2 = new ItemStack(Items.GLASS_BOTTLE, 1);
		PotionUtils.copyContents(potionStack, output1);

		try (Transaction tx = Transaction.openRoot()) {
			int extracted1 = stackHandler.extract(TOTEM_INPUT_SLOT, totemResource, 1, tx);
			int extracted2 = stackHandler.extract(POTION_INPUT_SLOT, potionResource, 1, tx);
			if (extracted1 != 1 || extracted2 != 1) return;

			ItemResource outResource1 = ItemResource.of(output1);
			int currentAmount1 = stackHandler.getAmountAsInt(INFUSED_TOTEM_OUTPUT_SLOT);
			stackHandler.set(INFUSED_TOTEM_OUTPUT_SLOT, outResource1, currentAmount1 + 1);

			ItemResource outResource2 = ItemResource.of(output2);
			int currentAmount2 = stackHandler.getAmountAsInt(BOTTLE_OUTPUT_SLOT);
			stackHandler.set(BOTTLE_OUTPUT_SLOT, outResource2, currentAmount2 + 1);

			tx.commit();
		}
	}

	@Override
	protected boolean hasRecipe(Level level, BlockPos pos) {
		ItemStack in1 = stackHandler.getResource(TOTEM_INPUT_SLOT).toStack();
		ItemStack in2 = stackHandler.getResource(POTION_INPUT_SLOT).toStack();
		ItemStack output1 = new ItemStack(ModItems.INFUSED_TOTEM.get());
		ItemStack output2 = new ItemStack(Items.GLASS_BOTTLE);

		return (in1.is(Items.TOTEM_OF_UNDYING)
				&& canInsertIntoSlot(output1, INFUSED_TOTEM_OUTPUT_SLOT, output1.getCount(), stackHandler))
				&& (in2.is(Items.POTION)
				&& canInsertIntoSlot(output2, BOTTLE_OUTPUT_SLOT, output2.getCount(), stackHandler));
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		stackHandler.deserialize(input);
		currentProgress = input.getInt("potion_totems.infuser.currentProgress").get();
		maxProgress = input.getInt("potion_totems.infuser.max_progress").get();
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		stackHandler.serialize(output);
		output.putInt("potion_totems.infuser.currentProgress", currentProgress);
		output.putInt("potion_totems.infuser.max_progress", maxProgress);
		super.saveAdditional(output);
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (level != null && level.isClientSide()) {
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
		}
	}

	@Override
	public void drops() {
		SimpleContainer inventory = new SimpleContainer(stackHandler.size());
		for (int i = 0; i < stackHandler.size(); i++) {
			inventory.setItem(i, stackHandler.getResource(i).toStack());
		}
		Containers.dropContents(this.level, this.worldPosition, inventory);
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new InfuserMenu(containerId, playerInventory, this, this.data);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
}