package io.github.scaredsmods.potion_totems.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.scaredsmods.potion_totems.recipe.input.InfuserRecipeInput;
import io.github.scaredsmods.potion_totems.registry.ModRecipeBookCategories;
import io.github.scaredsmods.potion_totems.registry.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record InfuserRecipe(List<Ingredient> inputs, List<ItemStackTemplate> outputs) implements Recipe<InfuserRecipeInput> {

    public static final MapCodec<InfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(InfuserRecipe::inputs),
            ItemStackTemplate.CODEC.listOf().fieldOf("outputs").forGetter(InfuserRecipe::outputs)
    ).apply(inst, InfuserRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, InfuserRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), InfuserRecipe::inputs,
            ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()), InfuserRecipe::outputs,
            InfuserRecipe::new
    );

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(inputs);
        return list;
    }

    public List<ItemStack> getOutputs() {
        return outputs.stream().map(ItemStackTemplate::create).toList();
    }

    @Override
    public boolean matches(InfuserRecipeInput infuserRecipeInput, Level level) {
        if (level.isClientSide()) return false;
        for (int i = 0; i < inputs.size(); i++) {
            if(!inputs.get(i).test(infuserRecipeInput.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(InfuserRecipeInput infuserRecipeInput) {
        throw new IllegalStateException();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Infusing";
    }

    @Override
    public RecipeSerializer<InfuserRecipe> getSerializer() {
        return ModRecipes.INFUSER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<InfuserRecipe> getType() {
        return ModRecipes.INFUSER_RECIPE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.INFUSING.get();
    }
}
