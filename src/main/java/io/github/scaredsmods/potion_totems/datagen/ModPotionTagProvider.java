package io.github.scaredsmods.potion_totems.datagen;

import io.github.scaredsmods.potion_totems.registry.ModPotions;
import io.github.scaredsmods.potion_totems.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PotionTagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.alchemy.Potions;

import java.util.concurrent.CompletableFuture;

public class ModPotionTagProvider extends PotionTagsProvider {
    public ModPotionTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ModTags.Potions.TOTEM_MASTER_LEVEL_1_POTIONS)
                .add(Potions.INFESTED)
                .add(Potions.OOZING)
                .add(Potions.LUCK)
                .add(Potions.LEAPING)
                .add(Potions.HEALING);

        tag(ModTags.Potions.TOTEM_MASTER_LEVEL_2_POTIONS)
                .add(Potions.NIGHT_VISION)
                .add(Potions.STRONG_REGENERATION)
                .add(Potions.POISON)
                .add(TagEntry.element(ModPotions.AGGRESSION.getId()));
    }
}
