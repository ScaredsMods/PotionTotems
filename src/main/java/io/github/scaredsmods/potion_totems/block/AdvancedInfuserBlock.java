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
import io.github.scaredsmods.potion_totems.block.entity.AdvancedInfuserBlockEntity;
import io.github.scaredsmods.potion_totems.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class AdvancedInfuserBlock extends BaseHorizontalBlock {
	public AdvancedInfuserBlock(Properties properties) {
		super(properties, SHAPE);
		runCalculation(SHAPE);
	}

	public static final MapCodec<AdvancedInfuserBlock> CODEC = simpleCodec(AdvancedInfuserBlock::new);

	public static final VoxelShape SHAPE = Stream.of(
			Stream.of(
					Block.box(0, 12, 0, 32, 16, 16),
					Block.box(28, 0, 0, 32, 12, 4),
					Block.box(28, 0, 12, 32, 12, 16),
					Block.box(0, 0, 12, 4, 12, 16),
					Block.box(0, 0, 0, 4, 12, 4),
					Stream.of(
							Block.box(13, 2.5, 4, 13, 4.5, 14),
							Block.box(6, 2.5, 4, 13, 2.5, 14),
							Block.box(7, 2.5, 5, 13, 4.5, 13),
							Block.box(6, 4.5, 4, 13, 4.5, 14)
					).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
					Stream.of(
							Block.box(13, 0.25, 4, 13, 2.25, 14),
							Block.box(6, 0.25, 4, 13, 0.25, 14),
							Block.box(7, 0.25, 5, 13, 2.25, 13),
							Block.box(6, 2.25, 4, 13, 2.25, 14)
					).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
					Stream.of(
							Block.box(13, 4.75, 4, 13, 6.75, 14),
							Block.box(6, 4.75, 4, 13, 4.75, 14),
							Block.box(7, 4.75, 5, 13, 6.75, 13),
							Block.box(6, 6.75, 4, 13, 6.75, 14)
					).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
			Stream.of(
					Block.box(4, 24, 5, 10, 27, 11),
					Stream.of(
							Block.box(3, 16, 4, 11, 22, 4),
							Block.box(3, 22, 4, 11, 22, 12),
							Block.box(11, 16, 4, 11, 22, 12),
							Block.box(3, 16, 12, 11, 22, 12),
							Block.box(3, 16, 4, 3, 22, 12)
					).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
					Stream.of(
							Block.box(5, 22, 6, 9, 24, 6),
							Block.box(9, 22, 6, 9, 24, 10),
							Block.box(5, 22, 10, 9, 24, 10),
							Block.box(5, 22, 6, 5, 24, 10)
					).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
			Shapes.join(Block.box(3.25, 16, 4.25, 10.75, 21.75, 11.75), Block.box(5.75, 16.1, 6.15, 8.25, 24.8, 9.899999999999999), BooleanOp.OR)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AdvancedInfuserBlockEntity(pos, state);
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		return 2;
	}

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}


	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!level.isClientSide()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof AdvancedInfuserBlockEntity baseInfuserBlockEntity) {
				player.openMenu(new SimpleMenuProvider(baseInfuserBlockEntity, Component.translatable(PotionTotems.MOD_ID + ".gui.advanced_infuser.title")), pos);
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
		return createTickerHelper(blockEntityType, ModBlockEntities.BE_ADVANCED_INFUSER.get(), (level1, pos, state1, blockEntity) ->
				blockEntity.tick(level1, pos, state1));
	}




}
