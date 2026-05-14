package io.github.scaredsmods.potion_totems.datagen;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider {

    public static class Blocks extends BlockTagsProvider {

        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, PotionTotems.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(ModBlocks.INFUSER.get())
                    .add(ModBlocks.ADVANCED_INFUSER.get());

            tag(BlockTags.NEEDS_STONE_TOOL)
                    .add(ModBlocks.INFUSER.get())
                    .add(ModBlocks.ADVANCED_INFUSER.get())
                    .add(ModBlocks.INFUSER_FRAME.get());

            tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(ModBlocks.INFUSER_FRAME.get());
        }
    }
}
