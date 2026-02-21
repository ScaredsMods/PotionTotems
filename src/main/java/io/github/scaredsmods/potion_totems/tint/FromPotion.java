package io.github.scaredsmods.potion_totems.tint;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.Nullable;

public record FromPotion(int defaultColor) implements ItemTintSource {

    public static final MapCodec<FromPotion> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(FromPotion::defaultColor)
            ).apply(instance, FromPotion::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        return ARGB.opaque(stack.get(DataComponents.POTION_CONTENTS).getColor());
    }

    @Override
    public MapCodec<FromPotion> type() {
        return MAP_CODEC;
    }
}
