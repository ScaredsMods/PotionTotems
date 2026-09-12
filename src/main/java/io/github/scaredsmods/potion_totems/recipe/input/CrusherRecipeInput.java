package io.github.scaredsmods.potion_totems.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jspecify.annotations.NonNull;

public record CrusherRecipeInput(ItemStack input) implements RecipeInput {


    @Override
    public @NonNull ItemStack getItem(int index) {
        return input;
    }

    @Override
    public int size() {
        return 1;
    }
}
