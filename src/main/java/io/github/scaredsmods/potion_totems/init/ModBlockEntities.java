package io.github.scaredsmods.potion_totems.init;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.block.entity.AdvancedInfuserBlockEntity;
import io.github.scaredsmods.potion_totems.block.entity.InfuserBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final ResourcefulRegistry<BlockEntityType<?>> TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PotionTotems.MOD_ID);

    public static final RegistryEntry<BlockEntityType<InfuserBlockEntity>> BE_INFUSER = TYPES.register("infuser" , () ->
            BlockEntityType.Builder.of(InfuserBlockEntity::new, ModBlocks.INFUSER.get()).build(null));

    public static final RegistryEntry<BlockEntityType<AdvancedInfuserBlockEntity>> BE_ADVANCED_INFUSER = TYPES.register("advanced_infuser", () ->
            BlockEntityType.Builder.of(AdvancedInfuserBlockEntity::new, ModBlocks.ADVANCED_INFUSER.get()).build(null));
}
