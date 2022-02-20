package me.dewidos.bettertools.items;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier CREEPERIUM = new ForgeTier(2, 700, 5.5f, 2, 10, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ModItems.CREEPERIUM_INGOT.get()));
}
