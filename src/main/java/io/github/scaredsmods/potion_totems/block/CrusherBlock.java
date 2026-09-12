package io.github.scaredsmods.potion_totems.block;

import com.mojang.serialization.MapCodec;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.block.entity.CrusherBlockEntity;
import io.github.scaredsmods.potion_totems.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class CrusherBlock extends AbstractHorizontalBlock {

    public static final MapCodec<CrusherBlock> CODEC = simpleCodec(CrusherBlock::new);
    public static final VoxelShape SHAPE = Shapes.block();

    public CrusherBlock(Properties properties) {
        super(properties, SHAPE);
        runCalculation(SHAPE);
    }

    @Override
    protected MapCodec<CrusherBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new CrusherBlockEntity(worldPosition, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CrusherBlockEntity fragmenterBlockEntity) {
                player.openMenu(new SimpleMenuProvider(fragmenterBlockEntity, Component.translatable(PotionTotems.MOD_ID + ".gui.fragmenter.title")), pos);
            }else {
                throw new IllegalStateException("Missing container provider");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @org.jetbrains.annotations.Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()){
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.BE_CRUSHER.get(), (level1, pos, state1, blockEntity) ->
                blockEntity.tick(level1, pos, state1));
    }
}
