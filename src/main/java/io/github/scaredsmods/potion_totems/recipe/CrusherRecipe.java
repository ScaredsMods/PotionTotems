package io.github.scaredsmods.potion_totems.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.scaredsmods.potion_totems.recipe.input.CrusherRecipeInput;
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

public record CrusherRecipe(Ingredient inputItem, List<ItemStackTemplate> outputs) implements Recipe<CrusherRecipeInput> {

    public static MapCodec<CrusherRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredients").forGetter(CrusherRecipe::inputItem),
            ItemStackTemplate.CODEC.listOf().fieldOf("results").forGetter(CrusherRecipe::outputs)
    ).apply(inst, CrusherRecipe::new));

    public static StreamCodec<RegistryFriendlyByteBuf, CrusherRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,
                    CrusherRecipe::inputItem,

                    ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()),
                    CrusherRecipe::outputs,

                    CrusherRecipe::new);

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    public List<ItemStack> getOutputs() {
        return outputs.stream().map(ItemStackTemplate::create).toList();
    }

    @Override
    public boolean matches(CrusherRecipeInput input, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return inputItem.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(CrusherRecipeInput input) {
        throw new IllegalStateException();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Crushing";
    }

    @Override
    public RecipeSerializer<CrusherRecipe> getSerializer() {
        return ModRecipes.CRUSHING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<CrusherRecipe> getType() {
        return ModRecipes.CRUSHING_RECIPE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.CRUSHING.get();
    }
}
