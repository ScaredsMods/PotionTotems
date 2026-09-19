package io.github.scaredsmods.potion_totems.recipe;

import com.google.common.collect.Iterables;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.scaredsmods.potion_totems.recipe.input.AdvancedInfuserRecipeInput;
import io.github.scaredsmods.potion_totems.registry.ModRecipeBookCategories;
import io.github.scaredsmods.potion_totems.registry.ModRecipes;
import io.github.scaredsmods.potion_totems.util.PotionUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record AdvancedInfuserRecipe(List<Ingredient> inputs, List<ItemStackTemplate> outputs) implements Recipe<AdvancedInfuserRecipeInput> {

    public static final MapCodec<AdvancedInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(AdvancedInfuserRecipe::inputs),
            ItemStackTemplate.CODEC.listOf().fieldOf("outputs").forGetter(AdvancedInfuserRecipe::outputs)
    ).apply(instance, AdvancedInfuserRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AdvancedInfuserRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), AdvancedInfuserRecipe::inputs,
            ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()), AdvancedInfuserRecipe::outputs,
            AdvancedInfuserRecipe::new
    );

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(this.inputs);
        return list;
    }

    public List<ItemStack> getOutputs() {
        return this.outputs.stream().map(ItemStackTemplate::create).toList();
    }


    @Override
    public boolean matches(AdvancedInfuserRecipeInput advancedInfuserRecipeInput, Level level) {
        if (!level.isClientSide()) return false;
        for (int i = 0; i < inputs.size(); i++) {
            if (!inputs.get(i).test(advancedInfuserRecipeInput.getItem(i))) {
                return false;
            }
        }

        ItemStack potion = advancedInfuserRecipeInput.getItem(4);
        if(PotionUtils.hasComponent(potion, DataComponents.POTION_CONTENTS)) return false;
        PotionContents contents = potion.get(DataComponents.POTION_CONTENTS);
        if (Iterables.isEmpty(contents.getAllEffects())) return false;
        return true;
    }

    @Override
    public ItemStack assemble(AdvancedInfuserRecipeInput advancedInfuserRecipeInput) {
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
    public RecipeSerializer<AdvancedInfuserRecipe> getSerializer() {
        return ModRecipes.ADVANCED_INFUSER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<AdvancedInfuserRecipe> getType() {
        return ModRecipes.ADVANCED_INFUSER_RECIPE_TYPE.get();
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
