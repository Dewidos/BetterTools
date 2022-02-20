package me.dewidos.bettertools.items.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

public class CreeperiumPickaxe extends PickaxeItem {
    public CreeperiumPickaxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        float randomFloat = pLevel.random.nextFloat();

        if (randomFloat < 0.1f)
            pLevel.explode(pEntityLiving, pPos.getX(), pPos.getY(), pPos.getZ(), 8f, Explosion.BlockInteraction.BREAK);
        else if (randomFloat > 0.8f)
            pLevel.playSound(null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(), SoundEvents.PARROT_IMITATE_CREEPER, pEntityLiving.getSoundSource(), 1.0F, 1.0F);

        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }
}
