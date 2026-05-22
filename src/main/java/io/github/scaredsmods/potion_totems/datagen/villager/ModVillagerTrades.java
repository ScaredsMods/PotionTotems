package io.github.scaredsmods.potion_totems.datagen.villager;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.registry.ModItems;
import io.github.scaredsmods.potion_totems.registry.ModTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.functions.SetRandomPotionFunction;

import java.util.List;
import java.util.Optional;

public class ModVillagerTrades {

    public static final ResourceKey<VillagerTrade> TOTEM_MASTER_1_EMERALD_POTION = createKey("totem_master/1/emerald_potion");
    public static final ResourceKey<VillagerTrade> TOTEM_MASTER_1_EMERALD_INFUSED_TOTEM = createKey("totem_master/1/emerald_infused_totem");


    public static final ResourceKey<VillagerTrade> TOTEM_MASTER_2_EMERALD_POTION = createKey("totem_master/2/emerald_potion");
    public static final ResourceKey<VillagerTrade> TOTEM_MASTER_2_EMERALD_INFUSED_TOTEM = createKey("totem_master/2/emerald_infused_totem");
    public static void bootstrap(BootstrapContext<VillagerTrade> context) {

        Optional<HolderSet<Potion>> tier1Potions = context.lookup(Registries.POTION).get(ModTags.Potions.TOTEM_MASTER_LEVEL_1_POTIONS).map(named -> (HolderSet<Potion>) named);
        Optional<HolderSet<Potion>> tier2Potions = context.lookup(Registries.POTION).get(ModTags.Potions.TOTEM_MASTER_LEVEL_2_POTIONS).map(named -> (HolderSet<Potion>) named);
        RandomSource random = RandomSource.create();

        float baseEmeraldCost = 16;

        register(context, TOTEM_MASTER_1_EMERALD_INFUSED_TOTEM, new VillagerTrade(
                new TradeCost(Items.EMERALD, random.nextInt((int) baseEmeraldCost, 20)),
                new ItemStackTemplate(ModItems.INFUSED_TOTEM.get(), 1),
                12, 3, 0.07f, Optional.empty(),
                List.of(SetRandomPotionFunction.fromTagKey(tier1Potions).build())));

        register(context, TOTEM_MASTER_1_EMERALD_POTION, new VillagerTrade(
                new TradeCost(Items.EMERALD, random.nextInt((int) baseEmeraldCost, 20)),
                new ItemStackTemplate(Items.POTION, 1),
                12, 3, 0.07f, Optional.empty(),
                List.of(SetRandomPotionFunction.fromTagKey(tier1Potions).build())));

        register(context, TOTEM_MASTER_2_EMERALD_POTION, new VillagerTrade(
                new TradeCost(Items.EMERALD, random.nextInt((int) (baseEmeraldCost * 1.5F), 30)),
                new ItemStackTemplate(Items.POTION, 1),
                12, 2, 0.07f, Optional.empty(),
                List.of(SetRandomPotionFunction.fromTagKey(tier2Potions).build())));

        register(context, TOTEM_MASTER_2_EMERALD_INFUSED_TOTEM, new VillagerTrade(
                new TradeCost(Items.EMERALD, random.nextInt((int) (baseEmeraldCost * 1.5F), 30)),
                new ItemStackTemplate(ModItems.INFUSED_TOTEM.get(), 1),
                12, 2, 0.07f, Optional.empty(),
                List.of(SetRandomPotionFunction.fromTagKey(tier2Potions).build())));
    }


    private static ResourceKey<VillagerTrade> createKey(String id) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, PotionTotems.id(id));
    }

    private static void register(BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> key, VillagerTrade trade) {
        context.register(key, trade);
    }
}
