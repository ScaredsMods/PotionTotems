package io.github.scaredsmods.potion_totems.registry;

import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.trading.VillagerTrade;

public class ModTags {


    public static class Trades {
        public static final TagKey<VillagerTrade> TOTEM_MASTER_LEVEL_1 = createTag("totem_master/level_1");
        public static final TagKey<VillagerTrade> TOTEM_MASTER_LEVEL_2 = createTag("totem_master/level_2");

        private static TagKey<VillagerTrade> createTag(String name) {
            return TagKey.create(Registries.VILLAGER_TRADE, PotionTotems.id(name));
        }
    }

    public static class Potions {
        public static final TagKey<Potion> TOTEM_MASTER_LEVEL_1_POTIONS =  createTag("totem_master/level_1");
        public static final TagKey<Potion> TOTEM_MASTER_LEVEL_2_POTIONS =  createTag("totem_master/level_2");

        private static TagKey<Potion> createTag(String name) {
            return TagKey.create(Registries.POTION, PotionTotems.id(name));
        }
    }
}
