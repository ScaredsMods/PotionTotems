package io.github.scaredsmods.potion_totems.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.component.TotemFragmentComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModDataComponents {

    public static final ResourcefulRegistry<DataComponentType<?>> DATA_COMPONENTS = ResourcefulRegistries.create(BuiltInRegistries.DATA_COMPONENT_TYPE, PotionTotems.MOD_ID);
    public static final RegistryEntry<DataComponentType<TotemFragmentComponent>> TOTEM_FRAGMENT = DATA_COMPONENTS.register("totem_fragment", () -> DataComponentType.<TotemFragmentComponent>builder().persistent(TotemFragmentComponent.CODEC).networkSynchronized(TotemFragmentComponent.STREAM_CODEC).build());
}
