package io.github.scaredsmods.potion_totems.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record TotemFragmentComponent(int fragmentId) {

    public static final Codec<TotemFragmentComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("fragmentId").forGetter(TotemFragmentComponent::fragmentId)
            ).apply(instance, TotemFragmentComponent::new)
    );
    public static final StreamCodec<ByteBuf, TotemFragmentComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, TotemFragmentComponent::fragmentId,
            TotemFragmentComponent::new
    );
}
