package me.dewidos.bettertools.items.tools;

import me.dewidos.bettertools.util.BlockData;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

public class CreeperiumAxe extends AxeItem {
    public CreeperiumAxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide && pState.is(BlockTags.LOGS) && pLevel.random.nextFloat() > 0.88f) {
            ArrayList<BlockData> touchingBlocksList = new ArrayList<>();
            touchingBlocks(pLevel, pPos, BlockTags.LOGS, touchingBlocksList);

            for (BlockData blockData : touchingBlocksList) {
                if (pStack.getDamageValue() < pStack.getMaxDamage()) {
                    blockData.getLevel().destroyBlock(blockData.getBlockPos(), true);
                    pStack.hurtAndBreak(1, pEntityLiving, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
                else break;
            }

            pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.2F) * 0.7F);

            return true;
        }

        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }

    public static void touchingBlocks(Level level, BlockPos blockPos, Tag<Block> blockTag, List<BlockData> blockDataList) {
        addBlockToList(new BlockData(level, level.getBlockState(blockPos), blockPos), blockDataList, blockTag);

        // Testing in X direction

        BlockPos testedBlockPosX1 = new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ());

        if (addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosX1), testedBlockPosX1), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosX1, blockTag, blockDataList);

        BlockPos testedBlockPosX2 = new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ());

        if (addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosX2), testedBlockPosX2), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosX2, blockTag, blockDataList);

        // Testing in Z direction

        BlockPos testedBlockPosZ1 = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1);

        if (addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosZ1), testedBlockPosZ1), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosZ1, blockTag, blockDataList);

        BlockPos testedBlockPosZ2 = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1);

        if (addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosZ2), testedBlockPosZ2), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosZ2, blockTag, blockDataList);

        // Testing in Y direction

        BlockPos testedBlockPosY1 = new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());

        if (addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosY1), testedBlockPosY1), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosY1, blockTag, blockDataList);

        BlockPos testedBlockPosY2 = new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ());

        if (addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosY2), testedBlockPosY2), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosY2, blockTag, blockDataList);
    }

    public static boolean addBlockToList(BlockData block, List<BlockData> list, Tag<Block> tag) {
        if (isAbsentOnBlockList(list, block.getBlockPos()) && block.getBlockState().is(tag)) {
            list.add(block);
            return true;
        } else return false;
    }

    public static boolean isAbsentOnBlockList(List<BlockData> list, BlockPos pos) {
        boolean isPresent = false;

        for (BlockData posToCheck : list) {
            BlockPos checkPos = posToCheck.getBlockPos();

            if (checkPos.getX() == pos.getX() && checkPos.getY() == pos.getY() && checkPos.getZ() == pos.getZ())
                isPresent = true;
        }

        return !isPresent;
    }
}
