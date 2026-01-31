package io.github.scaredsmods.potion_totems.init;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeModeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.item.PotionTotemItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItems {

    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, PotionTotems.MOD_ID);
    public static final ResourcefulRegistry<Item> PLACEHOLDER_ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, PotionTotems.MOD_ID);
    public static final ResourcefulRegistry<CreativeModeTab> TABS = ResourcefulRegistries.create(BuiltInRegistries.CREATIVE_MODE_TAB, PotionTotems.MOD_ID);


    public static final RegistryEntry<Item> INFUSED_TOTEM = ITEMS.register("infused_totem", PotionTotemItem::new);
    public static final RegistryEntry<Item> INFUSER_TOTEM_PH_1 = PLACEHOLDER_ITEMS.register("infuser_totem_placeholder_1", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> INFUSER_TOTEM_PH_2 = PLACEHOLDER_ITEMS.register("infuser_totem_placeholder_2", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> INFUSER_BLOCK_ITEM = ITEMS.register("infuser", () -> new BlockItem(ModBlocks.INFUSER.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ADVANCED_INFUSER_BLOCK_ITEM = ITEMS.register("advanced_infuser", () -> new BlockItem(ModBlocks.ADVANCED_INFUSER.get(), new Item.Properties()));

    public static final RegistryEntry<CreativeModeTab> TOTEMS = TABS.register("totems", () -> new ResourcefulCreativeModeTab(PotionTotems.id("totems"))
            .setItemIcon(() -> Items.TOTEM_OF_UNDYING)
            .addRegistry(ITEMS)
            .build());



}
