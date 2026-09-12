package io.github.scaredsmods.potion_totems.datagen.recipe;

import io.github.scaredsmods.potion_totems.recipe.CrusherRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class CrusherRecipeBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final List<ItemStackTemplate> results;
    private final Ingredient ingredient;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private CrusherRecipeBuilder(RecipeCategory category, Ingredient ingredient, List<ItemStackTemplate> items) {
        this.category = category;
        this.results = items;
        this.ingredient = ingredient;
    }

    public static CrusherRecipeBuilder crushing(RecipeCategory recipeCategory, Ingredient input, List<ItemStackTemplate> outputs) {
        return new CrusherRecipeBuilder(recipeCategory, input, outputs);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.results.getFirst());
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> location) {
        CrusherRecipe recipe = new CrusherRecipe(this.ingredient, this.results);
        output.accept(location, recipe, this.advancementBuilder.build(output, location, this.category));
    }
}
