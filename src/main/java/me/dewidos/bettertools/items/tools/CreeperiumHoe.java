package me.dewidos.bettertools.items.tools;

import me.dewidos.bettertools.util.BlockData;
import me.dewidos.bettertools.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.dewidos.bettertools.items.tools.CreeperiumAxe.addBlockToList;

public class CreeperiumHoe extends HoeItem {
    public CreeperiumHoe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!level.isClientSide) {
            if (level.random.nextFloat() < 0.3f && pContext.getClickedFace() != Direction.DOWN) {
                BlockPos blockPos = pContext.getClickedPos();
                Player player = Objects.requireNonNull(pContext.getPlayer());
                InteractionHand interactionHand = pContext.getHand();
                ItemStack itemStack = pContext.getItemInHand();

                int radius = 3;

                BoundingBox boundingBox = new BoundingBox(blockPos.getX() - radius, blockPos.getY(), blockPos.getZ() - radius, blockPos.getX() + radius, blockPos.getY(), blockPos.getZ() + radius);
                List<BlockData> blockDataList = new ArrayList<>();

                touchingBlocks(level, blockPos, ModTags.Blocks.TILLABLE, blockDataList, boundingBox, true);

                if (blockDataList.size() > 0) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 2.0F, (1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.2F) * 0.7F);

                    for (BlockData blockData : blockDataList) {
                        if (level.getBlockState(blockData.getBlockPos().above()).isAir())
                            tillBlock(player, level, blockData.getBlockPos(), itemStack, interactionHand);
                    }
                }
            }
        }

        return super.useOn(pContext);
    }

    private void tillBlock(Player player, Level level, BlockPos blockPos, ItemStack itemStack, InteractionHand hand) {
        level.setBlock(blockPos, Blocks.FARMLAND.defaultBlockState(), 11);

        itemStack.hurtAndBreak(1, player, itemOwner -> itemOwner.broadcastBreakEvent(hand));
    }

    public static void touchingBlocks(Level level, BlockPos blockPos, Tag<Block> blockTag, List<BlockData> blockDataList, BoundingBox boundingBox, boolean removeStartBlock) {
        BlockData startBlock = new BlockData(level, level.getBlockState(blockPos), blockPos);
        addBlockToList(startBlock, blockDataList, blockTag);

        // Testing in X direction

        BlockPos testedBlockPosX1 = new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ());

        if (boundingBox.intersects(new BoundingBox(testedBlockPosX1)) && addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosX1), testedBlockPosX1), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosX1, blockTag, blockDataList, boundingBox, false);

        BlockPos testedBlockPosX2 = new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ());

        if (boundingBox.intersects(new BoundingBox(testedBlockPosX2)) && addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosX2), testedBlockPosX2), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosX2, blockTag, blockDataList, boundingBox, false);

        // Testing in Z direction

        BlockPos testedBlockPosZ1 = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1);

        if (boundingBox.intersects(new BoundingBox(testedBlockPosZ1)) && addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosZ1), testedBlockPosZ1), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosZ1, blockTag, blockDataList, boundingBox, false);

        BlockPos testedBlockPosZ2 = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1);

        if (boundingBox.intersects(new BoundingBox(testedBlockPosZ2)) && addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosZ2), testedBlockPosZ2), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosZ2, blockTag, blockDataList, boundingBox, false);

        // Testing in Y direction

        BlockPos testedBlockPosY1 = new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());

        if (boundingBox.intersects(new BoundingBox(testedBlockPosY1)) && addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosY1), testedBlockPosY1), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosY1, blockTag, blockDataList, boundingBox, false);

        BlockPos testedBlockPosY2 = new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ());

        if (boundingBox.intersects(new BoundingBox(testedBlockPosY2)) && addBlockToList(new BlockData(level, level.getBlockState(testedBlockPosY2), testedBlockPosY2), blockDataList, blockTag))
            touchingBlocks(level, testedBlockPosY2, blockTag, blockDataList, boundingBox, false);

        if (removeStartBlock)
            blockDataList.remove(startBlock);
    }
}
