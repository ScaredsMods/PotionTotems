package io.github.scaredsmods.potion_totems.init;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.block.entity.BlockEntityAdvancedInfuser;
import io.github.scaredsmods.potion_totems.block.entity.BlockEntityInfuser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PTBlockEntities {

    public static final ResourcefulRegistry<BlockEntityType<?>> TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PotionTotems.MOD_ID);

    public static final RegistryEntry<BlockEntityType<BlockEntityInfuser>> BE_INFUSER = TYPES.register("infuser" , () ->
            BlockEntityType.Builder.of(BlockEntityInfuser::new, PTBlocks.INFUSER.get()).build(null));

    public static final RegistryEntry<BlockEntityType<BlockEntityAdvancedInfuser>> BE_ADVANCED_INFUSER = TYPES.register("advanced_infuser", () ->
            BlockEntityType.Builder.of(BlockEntityAdvancedInfuser::new, PTBlocks.ADVANCED_INFUSER.get()).build(null));
}
