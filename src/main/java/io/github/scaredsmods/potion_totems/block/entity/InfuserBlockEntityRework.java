package io.github.scaredsmods.potion_totems.block.entity;

import io.github.scaredsmods.potion_totems.recipe.InfuserRecipe;
import io.github.scaredsmods.potion_totems.recipe.input.InfuserRecipeInput;
import io.github.scaredsmods.potion_totems.registry.ModBlockEntities;
import io.github.scaredsmods.potion_totems.registry.ModRecipes;
import io.github.scaredsmods.potion_totems.screen.menu.InfuserMenuRework;
import io.github.scaredsmods.potion_totems.util.PotionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class InfuserBlockEntityRework extends AbstractTickingBlockEntity {

    public final ItemStacksResourceHandler itemStacksResourceHandler = new ItemStacksResourceHandler(7) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            InfuserBlockEntityRework.this.setChanged();
        }
    };

    public static final int INPUT_SLOT_1 = 0;
    public static final int INPUT_SLOT_2 = 1;
    public static final int INPUT_SLOT_3 = 2;
    public static final int INPUT_SLOT_4 = 3;
    public static final int INPUT_SLOT_5 = 4;
    public static final int OUTPUT_SLOT_1 = 5;
    public static final int OUTPUT_SLOT_2 = 6;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 600;
    private final int DEFAULT_MAX_PROGRESS = 600;

    public InfuserBlockEntityRework(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BE_INFUSER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int dataId) {
                return switch (dataId) {
                    case 0 -> InfuserBlockEntityRework.this.progress;
                    case 1 -> InfuserBlockEntityRework.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataId, int value) {
                switch (dataId) {
                    case 0 -> InfuserBlockEntityRework.this.progress = value;
                    case 1 -> InfuserBlockEntityRework.this.maxProgress = value;
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
        return Component.translatable("block.potion_totems.infuser");
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        progress = input.getInt("potion_totems.infuser.currentProgress").get();
        maxProgress = input.getInt("potion_totems.infuser.max_progress").get();
        input.child("inv").ifPresent(itemStacksResourceHandler::deserialize);
    }

    @Override
    public void saveAdditional(ValueOutput output) {
        output.putInt("potion_totems.infuser.currentProgress", progress);
        output.putInt("potion_totems.infuser.max_progress", maxProgress);
        super.saveAdditional(output);
    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {
        if (hasRecipe() && isOutputSlotEmptyOrReceivable()) {
            increaseCraftingProgress();
            setChanged(level, pPos, pState);

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
        Optional<RecipeHolder<InfuserRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        List<ItemStack> outputs = recipe.get().value().getOutputs();
        return canInsertIntoSlot(outputs, itemStacksResourceHandler);
    }

    @Override
    public boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    @Override
    public void craftItem() {
        Optional<RecipeHolder<InfuserRecipe>> recipe = getCurrentRecipe();
        List<ItemStack> outputs = recipe.get().value().getOutputs();

        try (Transaction transaction = Transaction.open(null)) {
            ItemStack potion = itemStacksResourceHandler.getResource(INPUT_SLOT_5).toStack(); // read first

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
    public Optional<RecipeHolder<InfuserRecipe>> getCurrentRecipe() {
        return ((ServerLevel) this.level).recipeAccess()
                .getRecipeFor(ModRecipes.INFUSER_RECIPE_TYPE.get(),
                        new InfuserRecipeInput(
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
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(itemStacksResourceHandler, 0);
            inv.setItem(i, new ItemStack(itemAccess.getResource().getItem(), itemAccess.getAmount()));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new InfuserMenuRework(containerId, inventory, this, this.itemStacksResourceHandler, this.data);
    }
}
