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

import io.github.scaredsmods.potion_totems.recipe.AdvancedInfuserRecipe;
import io.github.scaredsmods.potion_totems.recipe.InfuserRecipe;
import io.github.scaredsmods.potion_totems.recipe.input.AdvancedInfuserRecipeInput;
import io.github.scaredsmods.potion_totems.recipe.input.InfuserRecipeInput;
import io.github.scaredsmods.potion_totems.registry.ModBlockEntities;
import io.github.scaredsmods.potion_totems.registry.ModItems;
import io.github.scaredsmods.potion_totems.registry.ModRecipes;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AdvancedInfuserBlockEntity extends AbstractTickingBlockEntity {

	public final ItemStacksResourceHandler itemStacksResourceHandler = new ItemStacksResourceHandler(7) {
		@Override
		protected void onContentsChanged(int index, ItemStack previousContents) {
			super.onContentsChanged(index, previousContents);
			AdvancedInfuserBlockEntity.this.setChanged();
		}
	};

	public static final int INPUT_SLOT_1 = 0;
	public static final int INPUT_SLOT_2 = 1;
	public static final int INPUT_SLOT_3 = 2;
	public static final int INPUT_SLOT_4 = 3;
	public static final int INPUT_SLOT_5 = 4;
	public static final int OUTPUT_SLOT_1 = 5;
	public static final int OUTPUT_SLOT_2 = 6;

	private int progress = 0;
	private int maxProgress = 1200;
	private final int DEFAULT_MAX_PROGRESS = 1200;
	private final ContainerData data;

	public AdvancedInfuserBlockEntity(BlockPos pos, BlockState blockState) {
		super(ModBlockEntities.BE_ADVANCED_INFUSER.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int dataId) {
				return switch (dataId) {
					case 0 -> AdvancedInfuserBlockEntity.this.progress;
					case 1 -> AdvancedInfuserBlockEntity.this.maxProgress;
					default -> 0;
				};
			}

			@Override
			public void set(int dataId, int value) {
				switch (dataId) {
					case 0 -> AdvancedInfuserBlockEntity.this.progress = value;
					case 1 -> AdvancedInfuserBlockEntity.this.maxProgress = value;
				}
			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.potion_totems.advanced_infuser");
	}

	@Override
	public void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		progress = input.getInt("potion_totems.advanced_infuser.progress").get();
		maxProgress = input.getInt("potion_totems.advanced_infuser.max_progress").get();
		input.child("potion_totems.advanced_infuser.inv").ifPresent(itemStacksResourceHandler::deserialize);
	}

	@Override
	public void saveAdditional(ValueOutput output) {
		output.putInt("potion_totems.advanced_infuser.progress", progress);
		output.putInt("potion_totems.advanced_infuser.max_progress", maxProgress);
		output.putChild("potion_totems.advanced_infuser.inv", itemStacksResourceHandler);
		super.saveAdditional(output);
	}

	public void tick(Level level, BlockPos blockPos, BlockState blockState) {
		if (hasRecipe()) {
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
	public boolean hasRecipe() {
		Optional<RecipeHolder<AdvancedInfuserRecipe>> recipe = getCurrentRecipe();
		if (recipe.isEmpty()) {
			return false;
		}

		List<ItemStack> outputs = recipe.get().value().getOutputs();
		return canInsertOutput(outputs.get(0), OUTPUT_SLOT_1) && canInsertOutput(outputs.get(1), OUTPUT_SLOT_2);
	}

	private boolean canInsertOutput(ItemStack output, int slot) {
		ItemResource resource = itemStacksResourceHandler.getResource(slot);
		boolean itemMatches = resource.isEmpty() || resource.getItem() == output.getItem();
		if (!itemMatches) return false;

		int maxCount = resource.isEmpty() ? output.getMaxStackSize() : resource.getMaxStackSize();
		int currentCount = itemStacksResourceHandler.getAmountAsInt(slot);
		return maxCount >= currentCount + output.getCount();
	}

	@Override
	public boolean hasCraftingFinished() {
		return this.progress >= this.maxProgress;
	}

	@Override
	public void craftItem() {
		Optional<RecipeHolder<AdvancedInfuserRecipe>> recipe = getCurrentRecipe();
		List<ItemStack> outputs = recipe.get().value().getOutputs();

		try (Transaction transaction = Transaction.open(null)) {
			ItemStack potion = itemStacksResourceHandler.getResource(INPUT_SLOT_5).toStack();

			for (int i = 0; i < 5; i++) {
				itemStacksResourceHandler.extract(itemStacksResourceHandler.getResource(i), 1, transaction);
			}

			ItemAccess output1 = ItemAccess.forHandlerIndex(itemStacksResourceHandler, OUTPUT_SLOT_1);
			ItemAccess output2 = ItemAccess.forHandlerIndex(itemStacksResourceHandler, OUTPUT_SLOT_2);

			ItemStack infTotemStack = outputs.getFirst().copy();
			PotionUtils.copyContents(potion, infTotemStack);

			itemStacksResourceHandler.set(OUTPUT_SLOT_1, ItemResource.of(infTotemStack), output1.getAmount() + outputs.get(0).getCount());
			itemStacksResourceHandler.set(OUTPUT_SLOT_2, ItemResource.of(outputs.get(1)), output2.getAmount() + outputs.get(1).getCount());

			transaction.commit();
		}
	}

	@Override
	public void increaseCraftingProgress() {
		progress++;
	}

	@Override
	public void resetProgress() {
		this.progress = 0;
		this.maxProgress = DEFAULT_MAX_PROGRESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<RecipeHolder<AdvancedInfuserRecipe>> getCurrentRecipe() {
		return ((ServerLevel) this.level).recipeAccess()
				.getRecipeFor(ModRecipes.ADVANCED_INFUSER_RECIPE_TYPE.get(),
						new AdvancedInfuserRecipeInput(
								itemStacksResourceHandler.getResource(INPUT_SLOT_1).toStack(),
								itemStacksResourceHandler.getResource(INPUT_SLOT_2).toStack(),
								itemStacksResourceHandler.getResource(INPUT_SLOT_3).toStack(),
								itemStacksResourceHandler.getResource(INPUT_SLOT_4).toStack(),
								itemStacksResourceHandler.getResource(INPUT_SLOT_5).toStack()), level);
	}
	@Override
	public boolean isOutputSlotEmptyOrReceivable() {
		for (int i : new int[]{OUTPUT_SLOT_1, OUTPUT_SLOT_2}) {
			ItemResource resource = itemStacksResourceHandler.getResource(i);
			if (!(resource.isEmpty() || itemStacksResourceHandler.getAmountAsInt(i) < resource.getMaxStackSize())) {
				return false;
			}
		}
		return true;
	}
	@Override
	public void drops() {
		SimpleContainer inv = new SimpleContainer(itemStacksResourceHandler.size());
		for (int i = 0; i < itemStacksResourceHandler.size(); i++) {
			ItemAccess itemAccess = ItemAccess.forHandlerIndex(itemStacksResourceHandler, i);
			inv.setItem(i, new ItemStack(itemAccess.getResource().getItem(), itemAccess.getAmount()));
		}

		Containers.dropContents(this.level, this.worldPosition, inv);
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return new AdvancedInfuserMenu(containerId, inventory, this, this.itemStacksResourceHandler, this.data);
	}

}
