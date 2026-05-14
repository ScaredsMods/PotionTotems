package io.github.scaredsmods.potion_totems.block.entity.render.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public class InfuserRenderState extends BlockEntityRenderState {
    public BlockPos pos;
    public Level level;
    public Direction facing = Direction.SOUTH;

    public final ItemStackRenderState totemRenderState = new ItemStackRenderState();
    public final ItemStackRenderState blackTotemRenderState = new ItemStackRenderState();
}