package io.github.scaredsmods.potion_totems.block.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.scaredsmods.potion_totems.block.entity.AdvancedInfuserBlockEntity;
import io.github.scaredsmods.potion_totems.block.entity.render.state.InfuserRenderState;
import io.github.scaredsmods.potion_totems.registry.ModItems;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class AdvancedInfuserBER implements BlockEntityRenderer<AdvancedInfuserBlockEntity, InfuserRenderState> {
    private final ItemModelResolver itemModelResolver;

    public AdvancedInfuserBER(BlockEntityRendererProvider.Context context) {
        itemModelResolver = context.itemModelResolver();
    }


    @Override
    public InfuserRenderState createRenderState() {
        return new InfuserRenderState();
    }

    @Override
    public void submit(InfuserRenderState state, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        int light = getLightLevel(state.level, state.pos);

        switch (state.facing) {
            case WEST -> {
                poseStack.pushPose();
                poseStack.translate(0.5f, 1.01f, 0.5f);
                poseStack.scale(0.35f, 0.35f, 0.35f);
                poseStack.mulPose(Axis.XP.rotationDegrees(270));
                poseStack.mulPose(Axis.ZP.rotationDegrees(270));
                state.totemRenderState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
                state.blackTotemRenderState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
            case NORTH -> {
                poseStack.pushPose();
                poseStack.translate(0.5f, 1.01f, 0.5f);
                poseStack.scale(0.35f, 0.35f, 0.35f);
                poseStack.mulPose(Axis.XP.rotationDegrees(270));
                poseStack.mulPose(Axis.ZP.rotationDegrees(180));
                state.totemRenderState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
                state.blackTotemRenderState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
            case EAST -> {
                poseStack.pushPose();
                poseStack.translate(0.52f, 1.01f, 0.5f);
                poseStack.scale(0.35f, 0.35f, 0.35f);
                poseStack.mulPose(Axis.XP.rotationDegrees(270));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));
                state.totemRenderState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
                state.blackTotemRenderState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
            case SOUTH -> {
                poseStack.pushPose();
                poseStack.translate(0.5f, 1.01f, 0.5f);
                poseStack.scale(0.35f, 0.35f, 0.35f);
                poseStack.mulPose(Axis.XP.rotationDegrees(270));
                state.totemRenderState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
                state.blackTotemRenderState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
        }
    }


    @Override
    public void extractRenderState(AdvancedInfuserBlockEntity blockEntity, InfuserRenderState state,
                                   float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.pos = blockEntity.getBlockPos();
        state.level = blockEntity.getLevel();
        state.facing = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();

        itemModelResolver.updateForTopItem(state.totemRenderState,
                blockEntity.stackHandler.getResource(AdvancedInfuserBlockEntity.INFUSED_TOTEM_INPUT_SLOT).toStack(),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 1);

        itemModelResolver.updateForTopItem(state.blackTotemRenderState,
                new ItemStack(ModItems.INFUSER_TOTEM_PH_2.get()),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 2);
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightCoordsUtil.pack(bLight, sLight);
    }
}
