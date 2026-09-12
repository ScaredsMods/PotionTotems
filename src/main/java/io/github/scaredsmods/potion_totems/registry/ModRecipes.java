package io.github.scaredsmods.potion_totems.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.recipe.CrusherRecipe;
import io.github.scaredsmods.potion_totems.recipe.InfuserRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {

    public static final ResourcefulRegistry<RecipeType<?>> RECIPE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_TYPE, PotionTotems.MOD_ID);
    public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_SERIALIZER, PotionTotems.MOD_ID);

    public static final RegistryEntry<RecipeType<InfuserRecipe>> INFUSER_RECIPE_TYPE = RECIPE_TYPES.register("infusing", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "infusing";
        }
    });

    public static final RegistryEntry<RecipeSerializer<InfuserRecipe>> INFUSER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("infusing", () -> new RecipeSerializer<>(InfuserRecipe.CODEC, InfuserRecipe.STREAM_CODEC));

    public static final RegistryEntry<RecipeType<CrusherRecipe>> CRUSHING_RECIPE_TYPE = RECIPE_TYPES.register("crushing", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "crushing";
        }
    });

    public static final RegistryEntry<RecipeSerializer<CrusherRecipe>> CRUSHING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crushing", () -> new RecipeSerializer<>(CrusherRecipe.CODEC, CrusherRecipe.STREAM_CODEC));




}
