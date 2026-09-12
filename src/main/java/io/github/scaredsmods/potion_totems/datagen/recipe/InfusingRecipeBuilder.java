package io.github.scaredsmods.potion_totems.datagen.recipe;

import io.github.scaredsmods.potion_totems.recipe.InfuserRecipe;
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

public class InfusingRecipeBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final List<ItemStackTemplate> results;
    private final List<Ingredient> ingredients;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private InfusingRecipeBuilder(RecipeCategory category, List<Ingredient> ingredient, List<ItemStackTemplate> results) {
        this.category = category;
        this.results = results;
        this.ingredients = ingredient;
    }

    public static InfusingRecipeBuilder infusing(RecipeCategory category, List<Ingredient> inputItem, List<ItemStackTemplate> items) {
        return new InfusingRecipeBuilder(category, inputItem, items);
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
        InfuserRecipe recipe = new InfuserRecipe(this.ingredients, this.results);
        output.accept(location, recipe, this.advancementBuilder.build(output, defaultId(), this.category));
    }
}
