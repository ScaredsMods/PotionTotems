package io.github.scaredsmods.potion_totems.registry;

import io.github.scaredsmods.potion_totems.block.entity.AdvancedInfuserBlockEntity;
import io.github.scaredsmods.potion_totems.block.entity.InfuserBlockEntity;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class ModBlockTintSources {

    public static BlockTintSource infuserColor() {
        return new BlockTintSource() {

            @Override
            public int color(BlockState state) {
                return 0xFFFFFF;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                Optional<InfuserBlockEntity> blockEntity = level.getBlockEntity(pos, ModBlockEntities.BE_INFUSER.get());
                if (blockEntity.isEmpty()) {
                    return 0xFFFFFF;
                }
                ItemStack involvedStack;
                ItemStack potionStack = blockEntity.get().stackHandler.getResource(InfuserBlockEntity.POTION_INPUT_SLOT).toStack();

                if (!potionStack.isEmpty() && potionStack.has(DataComponents.POTION_CONTENTS)) {
                    involvedStack = potionStack;
                } else {
                    return 0xFFFFFF;
                }
                PotionContents contents = involvedStack.get(DataComponents.POTION_CONTENTS);

                if (contents == null) {
                    return 0xFFFFFF;
                }
                return ARGB.opaque(involvedStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
            }
        };
    }

    public static BlockTintSource advancedInfuserColor() {
        return new BlockTintSource() {

            @Override
            public int color(BlockState state) {
                return 0xFFFFFF;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                Optional<AdvancedInfuserBlockEntity> blockEntity =  level.getBlockEntity(pos, ModBlockEntities.BE_ADVANCED_INFUSER.get());
                if (blockEntity.isEmpty()) {
                    return 0xFFFFFF;
                }
                ItemStack involvedStack;
                ItemStack potionStack = blockEntity.get().stackHandler.getResource(AdvancedInfuserBlockEntity.POTION_INPUT_SLOT).toStack();

                if (!potionStack.isEmpty() && potionStack.has(DataComponents.POTION_CONTENTS)) {
                    involvedStack = potionStack;
                } else {
                    return 0xFFFFFF;
                }
                PotionContents contents = involvedStack.get(DataComponents.POTION_CONTENTS);

                if (contents == null) {
                    return 0xFFFFFF;
                }
                return ARGB.opaque(involvedStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
            }
        };
    }
}
