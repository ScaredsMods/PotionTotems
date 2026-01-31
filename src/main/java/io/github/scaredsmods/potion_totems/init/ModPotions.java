package io.github.scaredsmods.potion_totems.init;

import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModPotions {

    public static final ResourcefulRegistry<Potion> POTIONS = ResourcefulRegistries.create(BuiltInRegistries.POTION, PotionTotems.MOD_ID);

    public static final HolderRegistryEntry<Potion> AGGRESSION = POTIONS.registerHolder("aggression", () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1),
            new MobEffectInstance(MobEffects.REGENERATION, 6000, 1), new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 1),
            new MobEffectInstance(MobEffects.INFESTED, 6000)
    ));

    public static final HolderRegistryEntry<Potion> POSITIVE = POTIONS.registerHolder("positive", () -> new Potion(positiveEffects()));
    public static final HolderRegistryEntry<Potion> NEGATIVE = POTIONS.registerHolder("negative", () -> new Potion(negativeEffects()));
    public static final HolderRegistryEntry<Potion> NEUTRAL = POTIONS.registerHolder("neutral", () -> new Potion(neutralEffects()));

    private static MobEffectInstance[] positiveEffects() {
        return BuiltInRegistries.MOB_EFFECT.holders()
                .filter(effect -> effect.value().isBeneficial())
                .map(effect -> new MobEffectInstance(effect, 12000, 1))
                .toArray(MobEffectInstance[]::new);
    }

    private static MobEffectInstance[] negativeEffects() {
        return BuiltInRegistries.MOB_EFFECT.holders()
                .filter(effect -> effect.value().getCategory() == MobEffectCategory.HARMFUL)
                .map(effect -> new MobEffectInstance(effect, 12000, 1))
                .toArray(MobEffectInstance[]::new);
    }
    private static MobEffectInstance[] neutralEffects() {
        return BuiltInRegistries.MOB_EFFECT.holders()
                .filter(effect -> effect.value().getCategory() == MobEffectCategory.NEUTRAL)
                .map(effect -> new MobEffectInstance(effect, 12000, 1))
                .toArray(MobEffectInstance[]::new);
    }
}
