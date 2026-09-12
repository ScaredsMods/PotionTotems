package io.github.scaredsmods.potion_totems.screen.menu;

import io.github.scaredsmods.potion_totems.block.entity.CrusherBlockEntity;
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import io.github.scaredsmods.potion_totems.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class CrusherMenu extends AbstractContainerMenu {
    public final CrusherBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public CrusherMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()), new ItemStacksResourceHandler(10), new SimpleContainerData(2));
    }

    public CrusherMenu(int containerId, Inventory inv, BlockEntity blockEntity, ItemStacksResourceHandler resourceHandler, ContainerData data) {
        super(ModMenuTypes.CRUSHER_MENU.get(), containerId);
        this.blockEntity = (CrusherBlockEntity) blockEntity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerHotbar(inv);
        addPlayerInventory(inv);

        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.INPUT_SLOT, 13, 32));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_1, 103, 13));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_2, 121, 13));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_3, 139, 13));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_4, 103, 31));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_5, 121, 31));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_6, 139, 31));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_7, 103, 49));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_8, 121, 49));
        this.addSlot(new ResourceHandlerSlot(resourceHandler, resourceHandler::set, CrusherBlockEntity.OUTPUT_SLOT_9, 139, 49));


        addDataSlots(data);
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 19;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }
    public boolean isCrafting() {
        return data.get(0) > 0;
    }


    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 10;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.CRUSHER.get());
    }

    private void addPlayerInventory(Inventory playerInv) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInv) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInv, i, 8 + i * 18, 142));
        }
    }
}
