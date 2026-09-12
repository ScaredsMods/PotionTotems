package io.github.scaredsmods.potion_totems.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record InfuserRecipeInput(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> input1;
            case 1 -> input2;
            case 2 -> input3;
            case 3 -> input4;
            case 4 -> input5;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 5;
    }
}
