package me.dewidos.bettertools.items.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

public class CreeperiumShovel extends ShovelItem {
    private MobEffectInstance hasteEffect = null;

    public CreeperiumShovel(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pLevel.random.nextFloat() < 0.1f) {
            pEntityLiving.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 3, false, false, false));
            hasteEffect = pEntityLiving.getEffect(MobEffects.DIG_SPEED);

            pLevel.playSound(null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(), SoundEvents.GENERIC_EXPLODE, pEntityLiving.getSoundSource(), 2.0F, (1.0F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.2F) * 0.7F);
        }

        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (hasteEffect == null) return;

        if (!pIsSelected && !pLevel.isClientSide) {
            if (pEntity instanceof LivingEntity livingEntity) {
                if (livingEntity.getEffect(MobEffects.DIG_SPEED) == hasteEffect) {
                    livingEntity.removeEffect(MobEffects.DIG_SPEED);
                }
                hasteEffect = null;
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
