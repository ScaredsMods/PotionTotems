package io.github.scaredsmods.potion_totems.block.entity;

import io.github.scaredsmods.potion_totems.component.TotemFragmentComponent;
import io.github.scaredsmods.potion_totems.recipe.CrusherRecipe;
import io.github.scaredsmods.potion_totems.recipe.input.CrusherRecipeInput;
import io.github.scaredsmods.potion_totems.registry.ModBlockEntities;
import io.github.scaredsmods.potion_totems.registry.ModDataComponents;
import io.github.scaredsmods.potion_totems.registry.ModRecipes;
import io.github.scaredsmods.potion_totems.screen.menu.CrusherMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
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

public class CrusherBlockEntity extends AbstractTickingBlockEntity {

    public final ItemStacksResourceHandler resourceHandler = new ItemStacksResourceHandler(10) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            CrusherBlockEntity.this.setChanged();
        }
    };

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT_1 = 1;
    public static final int OUTPUT_SLOT_2 = 2;
    public static final int OUTPUT_SLOT_3 = 3;
    public static final int OUTPUT_SLOT_4 = 4;
    public static final int OUTPUT_SLOT_5 = 5;
    public static final int OUTPUT_SLOT_6 = 6;
    public static final int OUTPUT_SLOT_7 = 7;
    public static final int OUTPUT_SLOT_8 = 8;
    public static final int OUTPUT_SLOT_9 = 9;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 450;
    private final int DEFAULT_MAX_PROGRESS = 450;

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BE_CRUSHER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CrusherBlockEntity.this.progress;
                    case 1 -> CrusherBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0:
                        CrusherBlockEntity.this.progress = pValue;
                    case 1:
                        CrusherBlockEntity.this.maxProgress = pValue;
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
        return Component.translatable("block.potion_totems.fragmenter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CrusherMenu(pContainerId, pPlayerInventory, this, this.resourceHandler, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        output.putInt("potion_totems.fragmenter.progress", progress);
        output.putInt("potion_totems.fragmenter.maxProgress", maxProgress);
        output.putChild("potion_totems.fragmenter.inv", resourceHandler);
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        progress = input.getInt("potion_totems.fragmenter.progress").get();
        maxProgress = input.getInt("potion_totems.fragmenter.maxProgress").get();
        input.child("potion_totems.fragmenter.inv").ifPresent(resourceHandler::deserialize);
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
        Optional<RecipeHolder<CrusherRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        List<ItemStack> outputs = recipe.get().value().getOutputs();
        return canInsertIntoSlot(outputs, resourceHandler);
    }

    @Override
    public boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    @Override
    public void craftItem() {
        Optional<RecipeHolder<CrusherRecipe>> recipe = getCurrentRecipe();
        List<ItemStack> outputs = recipe.get().value().getOutputs();

        try (Transaction transaction = Transaction.open(null)) {
            resourceHandler.extract(resourceHandler.getResource(INPUT_SLOT), 1, transaction);

            int slotCount = Math.min(9, outputs.size());
            for (int i = 1; i <= slotCount; i++) {
                ItemAccess itemAccess = ItemAccess.forHandlerIndex(resourceHandler, i);
                ItemStack outputStack = outputs.get(i - 1).copy();
                outputStack.set(ModDataComponents.TOTEM_FRAGMENT.get(), new TotemFragmentComponent(i));
                resourceHandler.set(i, ItemResource.of(outputStack), itemAccess.getAmount() + outputs.get(i - 1).getCount());
            }
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
    public Optional<RecipeHolder<CrusherRecipe>> getCurrentRecipe() {
        return ((ServerLevel) this.level).recipeAccess()
                .getRecipeFor(ModRecipes.CRUSHING_RECIPE_TYPE.get(),
                        new CrusherRecipeInput(resourceHandler.getResource(INPUT_SLOT).toStack()), level);
    }

    @Override
    public void drops() {
        SimpleContainer inv = new SimpleContainer(resourceHandler.size());
        for (int i = 0; i < resourceHandler.size(); i++) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(resourceHandler, 0);
            inv.setItem(i, new ItemStack(itemAccess.getResource().getItem(), itemAccess.getAmount()));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public boolean isOutputSlotEmptyOrReceivable() {
        for(int i = 0; i < 3; i++) {
            if(!(resourceHandler.getResource(i).isEmpty() ||
                    this.resourceHandler.getResource(i).toStack().getCount() <
                            this.resourceHandler.getResource(i).getMaxStackSize())) {
                return false;
            }
        }

        return true;
    }

}
