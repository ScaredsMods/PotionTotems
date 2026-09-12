package io.github.scaredsmods.potion_totems.component.property;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.scaredsmods.potion_totems.component.TotemFragmentComponent;
import io.github.scaredsmods.potion_totems.registry.ModDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record TotemFragmentProperty() implements RangeSelectItemModelProperty {

    public static final MapCodec<TotemFragmentProperty> CODEC = MapCodec.unit(TotemFragmentProperty::new);


    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        TotemFragmentComponent component = itemStack.get(ModDataComponents.TOTEM_FRAGMENT.get());
        return component != null ? component.fragmentId() : 0;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return CODEC;
    }
}
