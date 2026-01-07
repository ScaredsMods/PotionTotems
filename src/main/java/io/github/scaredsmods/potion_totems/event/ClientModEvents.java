package io.github.scaredsmods.potion_totems.event;


import io.github.scaredsmods.potion_totems.block.entity.BlockEntityAdvancedInfuser;
import io.github.scaredsmods.potion_totems.block.entity.BlockEntityInfuser;
import io.github.scaredsmods.potion_totems.block.entity.renderer.BERAdvancedInfuser;
import io.github.scaredsmods.potion_totems.block.entity.renderer.BERInfuser;
import io.github.scaredsmods.potion_totems.init.PTBlockEntities;
import io.github.scaredsmods.potion_totems.init.PTBlocks;
import io.github.scaredsmods.potion_totems.init.PTItems;
import io.github.scaredsmods.potion_totems.init.PTMenuTypes;
import io.github.scaredsmods.potion_totems.screen.AdvancedInfuserScreen;
import io.github.scaredsmods.potion_totems.screen.InfuserScreen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.Optional;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(PTBlocks.INFUSER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(PTBlocks.ADVANCED_INFUSER.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, tintIndex) -> tintIndex > 0
                        ? -1
                        : FastColor.ARGB32.opaque(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor()),
                PTItems.INFUSED_TOTEM.get()
        );

    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (tintIndex <= 0) {
                return 0xFFFFFF;
            }
            if (level == null || pos == null) {
                return 0xFFFFFF;
            }

            Optional<BlockEntityInfuser> blockEntity = level.getBlockEntity(pos, PTBlockEntities.BE_INFUSER.get());
            if (blockEntity.isEmpty()) {
                return 0xFFFFFF;
            }
            ItemStack involvedStack = null;
            ItemStack potionStack = blockEntity.get().itemStackHandler.getStackInSlot(BlockEntityInfuser.POTION_INPUT_SLOT);
            ItemStack infusedTotemStack = blockEntity.get().itemStackHandler.getStackInSlot(BlockEntityInfuser.INFUSED_TOTEM_OUTPUT_SLOT);

            if (!potionStack.isEmpty() && potionStack.has(DataComponents.POTION_CONTENTS)) {
                involvedStack = potionStack;
            } else if (!infusedTotemStack.isEmpty() && infusedTotemStack.has(DataComponents.POTION_CONTENTS)) {
                involvedStack = infusedTotemStack;
            } else {
                return 0xFFFFFF;
            }
            PotionContents contents = involvedStack.get(DataComponents.POTION_CONTENTS);

            if (contents == null) {
                return 0xFFFFFF;
            }
            return FastColor.ARGB32.opaque(involvedStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
        }, PTBlocks.INFUSER.get());

        event.register((state, level, pos, tintIndex) -> {
            if (tintIndex <= 0) {
                return 0xFFFFFF;
            }
            if (level == null || pos == null) {
                return 0xFFFFFF;
            }
            Optional<BlockEntityAdvancedInfuser> blockEntity = level.getBlockEntity(pos, PTBlockEntities.BE_ADVANCED_INFUSER.get());
            if (blockEntity.isEmpty()) {
                return 0xFFFFFF;
            }
            ItemStack involvedStack = null;
            ItemStack potionStack = blockEntity.get().stackHandler.getStackInSlot(BlockEntityAdvancedInfuser.POTION_INPUT_SLOT);
            ItemStack infusedTotemStack = blockEntity.get().stackHandler.getStackInSlot(BlockEntityAdvancedInfuser.INFUSED_TOTEM_OUTPUT_SLOT);

            if (!potionStack.isEmpty() && potionStack.has(DataComponents.POTION_CONTENTS)) {
                involvedStack = potionStack;
            } else if (!infusedTotemStack.isEmpty() && infusedTotemStack.has(DataComponents.POTION_CONTENTS)) {
                involvedStack = infusedTotemStack;
            } else {
                return 0xFFFFFF;
            }
            PotionContents contents = involvedStack.get(DataComponents.POTION_CONTENTS);

            if (contents == null) {
                return 0xFFFFFF;
            }
            return FastColor.ARGB32.opaque(involvedStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
        }, PTBlocks.ADVANCED_INFUSER.get());
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(PTMenuTypes.INFUSER_MENU.get(), InfuserScreen::new);
        event.register(PTMenuTypes.ADVANCED_INFUSER_MENU.get(), AdvancedInfuserScreen::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(PTBlockEntities.BE_INFUSER.get(), BERInfuser::new);
        event.registerBlockEntityRenderer(PTBlockEntities.BE_ADVANCED_INFUSER.get(), BERAdvancedInfuser::new);
    }



}
