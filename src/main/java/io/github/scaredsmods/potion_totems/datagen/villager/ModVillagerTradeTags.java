package io.github.scaredsmods.potion_totems.datagen.villager;


import io.github.scaredsmods.potion_totems.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.tags.TagEntry;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTags extends VillagerTradesTagsProvider {

    public ModVillagerTradeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }
    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(ModTags.Trades.TOTEM_MASTER_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.TOTEM_MASTER_1_EMERALD_INFUSED_TOTEM.identifier()))
                .add(TagEntry.element(ModVillagerTrades.TOTEM_MASTER_1_EMERALD_POTION.identifier()));

        getOrCreateRawBuilder(ModTags.Trades.TOTEM_MASTER_LEVEL_2)
                .add(TagEntry.element(ModVillagerTrades.TOTEM_MASTER_2_EMERALD_INFUSED_TOTEM.identifier()))
                .add(TagEntry.element(ModVillagerTrades.TOTEM_MASTER_2_EMERALD_POTION.identifier()));
    }
}