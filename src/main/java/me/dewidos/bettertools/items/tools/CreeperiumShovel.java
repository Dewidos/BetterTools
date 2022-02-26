package me.dewidos.bettertools.items.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Date;

public class CreeperiumShovel extends ShovelItem {
    private Date abilityEndDate = new Date();

    public CreeperiumShovel(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pLevel.random.nextFloat() < 0.1f) {
            abilityEndDate = new Date(System.currentTimeMillis() + 5000);
            pLevel.playSound(null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(), SoundEvents.GENERIC_EXPLODE, pEntityLiving.getSoundSource(), 2.0F, (1.0F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.2F) * 0.7F);
        }

        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }

    @SubscribeEvent
    public static void creeperiumShovelAbility(PlayerEvent.BreakSpeed event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity == null) return;

        Item item = livingEntity.getMainHandItem().getItem();

        if (item instanceof CreeperiumShovel creeperiumShovel) {
            if (new Date().before(creeperiumShovel.abilityEndDate)) {
                event.setNewSpeed(event.getOriginalSpeed() * 3f);
            }
        }
    }
}
