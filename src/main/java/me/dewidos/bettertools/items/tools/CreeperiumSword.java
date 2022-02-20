package me.dewidos.bettertools.items.tools;

import me.dewidos.bettertools.items.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CreeperiumSword extends SwordItem {
    public CreeperiumSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!entity.level.isClientSide) {
            if (entity instanceof Creeper) {
                entity.hurt(DamageSource.playerAttack(player), ((Creeper) entity).getMaxHealth());

                if (player.getAttackStrengthScale(0.5F) == 1f && stack.canPerformAction(ToolActions.SWORD_SWEEP))
                {
                    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
                    player.sweepAttack();
                }
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        Entity deadEntity = event.getEntity();
        Level level = deadEntity.level;

        if (!level.isClientSide) {
            Entity damageCauser = event.getSource().getEntity();
            if (damageCauser instanceof Player player) {
                if (player.getItemInHand(player.getUsedItemHand()).is(ModItems.CREEPERIUM_SWORD.get())) {
                    Vec3 deadPos = deadEntity.getPosition(1f);

                    level.explode(player, deadPos.x, deadPos.y, deadPos.z, 5f, Explosion.BlockInteraction.NONE);
                }
            }
        }
    }
}
