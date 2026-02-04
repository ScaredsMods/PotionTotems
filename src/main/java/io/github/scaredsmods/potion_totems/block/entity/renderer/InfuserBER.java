/*
	Copyright (C) 2025 ScaredRabbitNL

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program. If not, see <https://www.gnu.org/licenses/>.
*/
package io.github.scaredsmods.potion_totems.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.scaredsmods.potion_totems.block.entity.InfuserBlockEntity;
import io.github.scaredsmods.potion_totems.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class InfuserBER implements BlockEntityRenderer<InfuserBlockEntity> {

	public InfuserBER(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(InfuserBlockEntity blockEntity, float partialTick, PoseStack poseStack,
					MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		ItemStack totem = blockEntity.itemStackHandler.getStackInSlot(0);
		ItemStack blackTotem = new ItemStack(ModItems.INFUSER_TOTEM_PH_1.get());
		ItemStack potion = blockEntity.itemStackHandler.getStackInSlot(1);
		Direction FACING = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

		switch (FACING) {
			case WEST -> {
				poseStack.pushPose();
				poseStack.translate(0.5f, 1.05f, -0.5f);
				poseStack.scale(0.35f, 0.35f, 0.35f);
				poseStack.mulPose(Axis.XP.rotationDegrees(270));
				poseStack.mulPose(Axis.ZP.rotationDegrees(270));
				itemRenderer.renderStatic(totem, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
				itemRenderer.renderStatic(blackTotem, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 2);
				poseStack.popPose();

				poseStack.pushPose();
				poseStack.translate(0.5f, 1.05f, (1 / 10000f));
				poseStack.scale(0.35f, 0.35f, 0.35f);
				poseStack.mulPose(Axis.XP.rotationDegrees(270));
				poseStack.mulPose(Axis.ZP.rotationDegrees(270));
				itemRenderer.renderStatic(potion, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 3);
				poseStack.popPose();
			}
			case NORTH -> {
				poseStack.pushPose();
				poseStack.translate(1.5f, 1.01f, 0.5f);
				poseStack.scale(0.35f, 0.35f, 0.35f);
				poseStack.mulPose(Axis.XP.rotationDegrees(270));
				poseStack.mulPose(Axis.ZP.rotationDegrees(180));
				itemRenderer.renderStatic(totem, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
				itemRenderer.renderStatic(blackTotem, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 2);
				poseStack.popPose();

				poseStack.pushPose();
				poseStack.translate(1f, 1.01f, 0.5f);
				poseStack.scale(0.35f, 0.35f, 0.35f);
				poseStack.mulPose(Axis.XP.rotationDegrees(270));
				poseStack.mulPose(Axis.ZP.rotationDegrees(180));
				itemRenderer.renderStatic(potion, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 3);
				poseStack.popPose();
			}
			case EAST -> {
				poseStack.pushPose();
				poseStack.translate(0.52f, 1.01f, 1.5f);
				poseStack.scale(0.35f, 0.35f, 0.35f);
				poseStack.mulPose(Axis.XP.rotationDegrees(270));
				poseStack.mulPose(Axis.ZP.rotationDegrees(90));
				itemRenderer.renderStatic(totem, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
				itemRenderer.renderStatic(blackTotem, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 2);
				poseStack.popPose();

				poseStack.pushPose();
				poseStack.translate(0.5f, 1.01f, 1f);
				poseStack.scale(0.35f, 0.35f, 0.35f);
				poseStack.mulPose(Axis.XP.rotationDegrees(270));
				poseStack.mulPose(Axis.ZP.rotationDegrees(90));
				itemRenderer.renderStatic(potion, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 3);
				poseStack.popPose();
			}
			case SOUTH -> {
				poseStack.pushPose();
				poseStack.translate(-0.5f, 1.01f, 0.5f);
				poseStack.scale(0.35f, 0.35f, 0.35f);
				poseStack.mulPose(Axis.XP.rotationDegrees(270));
				itemRenderer.renderStatic(totem, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
				itemRenderer.renderStatic(blackTotem, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 2);
				poseStack.popPose();

				poseStack.pushPose();
				poseStack.translate(1/ 1000f, 1.01f, 0.5f);
				poseStack.scale(0.35f, 0.35f, 0.35f);
				poseStack.mulPose(Axis.XP.rotationDegrees(270));
				itemRenderer.renderStatic(potion, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
						OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 3);
				poseStack.popPose();
			}
		}
	}
	private int getLightLevel(Level level, BlockPos pos) {
		int bLight = level.getBrightness(LightLayer.BLOCK, pos);
		int sLight = level.getBrightness(LightLayer.SKY, pos);
		return LightTexture.pack(bLight, sLight);
	}
}
