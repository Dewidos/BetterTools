package me.dewidos.bettertools.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockData {
    private Level level;
    private BlockState blockState;
    private BlockPos blockPos;

    public BlockData(Level level, BlockState blockState, BlockPos blockPos) {
        this.level = level;
        this.blockPos = blockPos;
        this.blockState = blockState;
    }

    public Level getLevel() {
        return level;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}