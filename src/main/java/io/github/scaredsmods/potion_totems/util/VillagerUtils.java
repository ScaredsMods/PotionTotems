package io.github.scaredsmods.potion_totems.util;

import io.github.scaredsmods.potion_totems.init.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Optional;

public class VillagerUtils {

    private static MerchantOffer makeOffer(ItemLike itemCostA, ItemStack result, int maxUses, int xp, float priceMultiplier, int emeraldCost) {
        return new MerchantOffer(new ItemCost(itemCostA, emeraldCost), result, maxUses, xp, priceMultiplier);
    }
    private static MerchantOffer makeOffer(ItemLike itemCostA, ItemCost itemCostB ,ItemStack result, int maxUses, int xp, float priceMultiplier, int emeraldCost) {
        return new MerchantOffer(new ItemCost(itemCostA, emeraldCost), Optional.of(itemCostB), result, maxUses, xp, priceMultiplier);
    }
    private static MerchantOffer makeOffer(ItemLike itemCostA, ItemCost itemCostB ,ItemStack result, int uses, int maxUses, int xp, float priceMultiplier, int emeraldCost) {
        return new MerchantOffer(new ItemCost(itemCostA, emeraldCost), Optional.of(itemCostB), result, uses, maxUses, xp, priceMultiplier);
    }
    private static MerchantOffer makeOffer(ItemLike itemCostA, ItemCost itemCostB , ItemStack result, int uses, int maxUses, int xp, float priceMultiplier, int emeraldCost, int demand) {
        return new MerchantOffer(new ItemCost(itemCostA, emeraldCost), Optional.of(itemCostB), result, uses, maxUses, xp, priceMultiplier, demand);
    }

    public static void makeTrade(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades,
                                 List<Holder<Potion>> list, PotionType type,
                                 int index, int xp, float priceMultiplier, int maxUses, int emeraldCost

                                 ) {
        trades.get(index).add((entity, randomSource) -> {
            Holder<Potion> potionHolder = list.get(randomSource.nextInt(list.size()));
            PotionContents contents = new PotionContents(potionHolder);
            ItemStack stack;
            switch (type) {
                case POTION_TOTEM -> {
                    stack = new ItemStack(ModItems.INFUSED_TOTEM.get());
                    stack.set(DataComponents.POTION_CONTENTS, contents);
                    ItemStack itemCostB = new ItemStack(Items.POTION);
                    ItemCost potionCost = new ItemCost(itemCostB.getItemHolder(), 1, DataComponentPredicate.allOf(itemCostB.getComponents()), itemCostB);
                    return makeOffer(Items.EMERALD, potionCost, stack, maxUses, xp, priceMultiplier, emeraldCost);
                }

                case POTION -> {
                    stack = new ItemStack(Items.POTION);
                    stack.set(DataComponents.POTION_CONTENTS, contents);
                    return makeOffer(Items.EMERALD, stack, maxUses, xp, priceMultiplier, emeraldCost);
                }
                default -> {
                    return null;
                }
            }
        });

    }

    public static void makeTrade(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades,
                                 List<Holder<Potion>> list, PotionType type,
                                 int index, int maxUses, int xp, float priceMultiplier, int emeraldCost

    ) {
        trades.get(index).add((entity, randomSource) -> {
            Holder<Potion> potionHolder = list.get(randomSource.nextInt(list.size()));
            PotionContents contents = new PotionContents(potionHolder);
            ItemStack stack;
            switch (type) {
                case POTION_TOTEM -> {
                    stack = new ItemStack(ModItems.INFUSED_TOTEM.get());
                    stack.set(DataComponents.POTION_CONTENTS, contents);
                    ItemStack itemCostB = new ItemStack(Items.POTION);
                    itemCostB.set(DataComponents.POTION_CONTENTS, contents);
                    ItemCost potionCost = new ItemCost(itemCostB.getItemHolder(), 1, DataComponentPredicate.allOf(itemCostB.getComponents()), itemCostB);
                    return makeOffer(Items.EMERALD, potionCost, stack, maxUses, xp, priceMultiplier, emeraldCost);
                }

                case POTION -> {
                    stack = new ItemStack(Items.POTION);
                    stack.set(DataComponents.POTION_CONTENTS, contents);
                    return makeOffer(Items.EMERALD, stack, maxUses, xp, priceMultiplier, emeraldCost);
                }
                default -> {
                    return null;
                }
            }
        });

    }

    public static void makeTrade(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades,
                                 List<Holder<Potion>> list, PotionType type,
                                 int index, int uses, int maxUses, int xp, float priceMultiplier, int emeraldCost){
        trades.get(index).add((entity, randomSource) -> {
            Holder<Potion> potionHolder = list.get(randomSource.nextInt(list.size()));
            PotionContents contents = new PotionContents(potionHolder);
            ItemStack stack;
            switch (type) {
                case POTION_TOTEM -> {
                    stack = new ItemStack(ModItems.INFUSED_TOTEM.get());
                    stack.set(DataComponents.POTION_CONTENTS, contents);
                    ItemStack itemCostB = new ItemStack(Items.POTION);
                    itemCostB.set(DataComponents.POTION_CONTENTS, contents);
                    ItemCost potionCost = new ItemCost(itemCostB.getItemHolder(), 1, DataComponentPredicate.allOf(itemCostB.getComponents()), itemCostB);
                    return makeOffer(Items.EMERALD, potionCost, stack, uses, maxUses, xp, priceMultiplier, emeraldCost);
                }

                case POTION -> {
                    stack = new ItemStack(Items.POTION);
                    stack.set(DataComponents.POTION_CONTENTS, contents);
                    return makeOffer(Items.EMERALD, stack, maxUses, xp, priceMultiplier, emeraldCost);
                }
                default -> {
                    return null;
                }
            }
        });
    }

    public static void makeTrade(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades,
                                 List<Holder<Potion>> list, PotionType type,
                                 int index, int uses, int maxUses, int xp, float priceMultiplier, int demand, int emeraldCost){
        trades.get(index).add((entity, randomSource) -> {
            Holder<Potion> potionHolder = list.get(randomSource.nextInt(list.size()));
            PotionContents contents = new PotionContents(potionHolder);
            ItemStack stack;
            switch (type) {
                case POTION_TOTEM -> {
                    stack = new ItemStack(ModItems.INFUSED_TOTEM.get());
                    stack.set(DataComponents.POTION_CONTENTS, contents);
                    ItemStack itemCostB = new ItemStack(Items.POTION);
                    itemCostB.set(DataComponents.POTION_CONTENTS, contents);
                    ItemCost potionCost = new ItemCost(itemCostB.getItemHolder(), 1, DataComponentPredicate.allOf(itemCostB.getComponents()), itemCostB);
                    return makeOffer(Items.EMERALD, potionCost, stack, uses, maxUses, xp, priceMultiplier, emeraldCost, demand);
                }

                case POTION -> {
                    stack = new ItemStack(Items.POTION);
                    stack.set(DataComponents.POTION_CONTENTS, contents);
                    return makeOffer(Items.EMERALD, stack, maxUses, xp, priceMultiplier, emeraldCost);
                }
                default -> {
                    return null;
                }
            }
        });
    }
}
