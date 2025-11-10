package io.github.scaredsmods.potion_totems.lib.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class BlockEntityBaseInfuser extends BlockEntity implements MenuProvider {

    private final Component displayName;

    public BlockEntityBaseInfuser(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, null);
    }

    public BlockEntityBaseInfuser(BlockEntityType<?> type, BlockPos pos, BlockState blockState, Component displayName) {
        super(type, pos, blockState);
        this.displayName = displayName;
    }

    @Override
    public Component getDisplayName() {
        return this.displayName;
    }


    public boolean canInsertIntoSlot (ItemStack output, int slot, int count, ItemStackHandler itemStackHandler) {
        return canInsertItemIntoOutputSlot(output, slot, itemStackHandler) && canInsertAmountIntoOutputSlot(count, slot, itemStackHandler);
    }

    public boolean canInsertItemIntoOutputSlot(ItemStack output, int slot, ItemStackHandler itemStackHandler) {
        return itemStackHandler.getStackInSlot(slot).isEmpty() ||
                itemStackHandler.getStackInSlot(slot).getItem() == output.getItem();
    }
    public boolean canInsertAmountIntoOutputSlot(int count, int slot, ItemStackHandler itemStackHandler) {
        int maxCount = itemStackHandler.getStackInSlot(slot).isEmpty() ? 64 : itemStackHandler.getStackInSlot(slot).getMaxStackSize();
        int currentCount = itemStackHandler.getStackInSlot(slot).getCount();

        return maxCount >= currentCount + count;
    }

    protected abstract boolean hasRecipe(Level level, BlockPos pos);
    protected abstract boolean hasCraftingFinished();
    protected abstract void craftItem();
    protected abstract void increaseCraftingProgress();
    protected abstract void resetProgress();
    public abstract void drops();

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        loadAdditional(pkt.getTag(), lookupProvider);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }


}
