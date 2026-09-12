/*
	This file is part of PotionTotems, licensed under the Lesser General Public License version 3 (LGPL-3.0)
	Copyright (C) 2025 ScaredRabbitNL

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU Lesser General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public License
	along with this program. If not, see <https://www.gnu.org/licenses/>.
*/
package io.github.scaredsmods.potion_totems.block;

import com.mojang.serialization.MapCodec;
import io.github.scaredsmods.potion_totems.PotionTotems;
import io.github.scaredsmods.potion_totems.block.entity.InfuserBlockEntityRework;
import io.github.scaredsmods.potion_totems.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class InfuserBlock extends AbstractHorizontalBlock {

	public static final MapCodec<InfuserBlock> CODEC = simpleCodec(InfuserBlock::new);

	public static final VoxelShape SHAPE = Stream.of(
			Stream.of(
					Block.box(0, 0, 12, 4, 12, 16),
					Block.box(0, 0, 0, 4, 12, 4),
					Block.box(12, 0, 12, 16, 12, 16),
					Block.box(12, 0, 0, 16, 12, 4),
					Block.box(0, 12, 0, 16, 16, 16)
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
			Stream.of(
			Block.box(5, 8, 5, 11, 11, 11),
			Stream.of(
			Block.box(4, 0, 4, 12, 6, 4),
			Block.box(4, 6, 4, 12, 6, 12),
			Block.box(12, 0, 4, 12, 6, 12),
			Block.box(4, 0, 12, 12, 6, 12),
			Block.box(4, 0, 4, 4, 6, 12)
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
			Stream.of(
			Block.box(6, 6, 6, 10, 8, 6),
			Block.box(10, 6, 6, 10, 8, 10),
			Block.box(6, 6, 10, 10, 8, 10),
			Block.box(6, 6, 6, 6, 8, 10)
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
			Shapes.join(Block.box(4.25, 0, 4.25, 11.75, 5.75, 11.75), Block.box(6.75, 0.1, 6.15, 9.25, 8.8, 9.9), BooleanOp.OR)
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

	public InfuserBlock(Properties properties) {
		super(properties, SHAPE);
		runCalculation(SHAPE);
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		return 2;
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	protected MapCodec<? extends AbstractHorizontalBlock> codec() {
		return CODEC;
	}


	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new InfuserBlockEntityRework(pos, state);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!level.isClientSide()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof InfuserBlockEntityRework baseInfuserBlockEntity) {
				player.openMenu(new SimpleMenuProvider(baseInfuserBlockEntity, Component.translatable(PotionTotems.MOD_ID + ".gui.infuser.title")), pos);
			}else {
				throw new IllegalStateException("Missing container provider");
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (level.isClientSide()){
			return null;
		}
		return createTickerHelper(blockEntityType, ModBlockEntities.BE_INFUSER.get(), (level1, pos, state1, blockEntity) ->
				blockEntity.tick(level1, pos, state1));
	}
}
