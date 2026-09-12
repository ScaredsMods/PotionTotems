package io.github.scaredsmods.potion_totems.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public class ModRecipeBookCategories {
    public static final ResourcefulRegistry<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, PotionTotems.MOD_ID);
    public static final RegistryEntry<RecipeBookCategory> CRUSHING = RECIPE_BOOK_CATEGORIES.register("crushing", RecipeBookCategory::new);
    public static final RegistryEntry<RecipeBookCategory> INFUSING = RECIPE_BOOK_CATEGORIES.register("infusing", RecipeBookCategory::new);
}
