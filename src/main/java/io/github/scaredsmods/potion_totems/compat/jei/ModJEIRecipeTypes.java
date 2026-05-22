package io.github.scaredsmods.potion_totems.compat.jei;

import io.github.scaredsmods.potion_totems.PotionTotems;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ModJEIRecipeTypes {
    /*

    public static final IRecipeType<RecipeHolder<InfuserRecipe>> INFUSER =
            create(PotionTotems.MOD_ID, "infusing", InfuserRecipe.class);
    public static final IRecipeType<RecipeHolder<AdvancedInfuserRecipe>> ADVANCED_INFUSER =
            create(PotionTotems.MOD_ID, "advanced_infusing", AdvancedInfuserRecipe.class);

     */

    // From Occultism: https://github.com/klikli-dev/occultism/blob/version/26.1.2/src/main/java/com/klikli_dev/occultism/integration/jei/impl/JeiRecipeTypes.java
    // Under MIT-License
    public static <R extends Recipe<?>> IRecipeType<RecipeHolder<R>> create(String name) {
        Identifier uid = PotionTotems.id(name);
        @SuppressWarnings({"unchecked", "RedundantCast"})
        Class<? extends RecipeHolder<R>> holderClass = (Class<? extends RecipeHolder<R>>) (Object) RecipeHolder.class;
        return IRecipeType.create(uid, holderClass);
    }
}
