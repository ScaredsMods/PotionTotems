package io.github.scaredsmods.potion_totems.data.gen;

import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PotionTotems.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItemWithParent(ModItems.INFUSED_TOTEM.get(), PotionTotems.id("item/totem_base"));
        basicItem(ModItems.INFUSER_TOTEM_PH_1.get());
        basicItem(ModItems.INFUSER_TOTEM_PH_2.get());
    }

    public ItemModelBuilder basicItemWithParent(Item item, ResourceLocation parentId) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        String itemName = itemId.getPath();
        return withExistingParent(itemName, parentId);
    }
}
