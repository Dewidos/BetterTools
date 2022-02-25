package me.dewidos.bettertools.items;

import me.dewidos.bettertools.BetterTools;
import me.dewidos.bettertools.items.tools.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterTools.MOD_ID);

    public static final RegistryObject<Item> CREEPERIUM_INGOT = ITEMS.register("creeperium_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> CREEPERIUM_SWORD = ITEMS.register("creeperium_sword", () -> new CreeperiumSword(ModTiers.CREEPERIUM, 2, -0.2f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> CREEPERIUM_PICKAXE = ITEMS.register("creeperium_pickaxe", () -> new CreeperiumPickaxe(ModTiers.CREEPERIUM, 1, 1f, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> CREEPERIUM_SHOVEL = ITEMS.register("creeperium_shovel", () -> new
            CreeperiumShovel(ModTiers.CREEPERIUM, 0, 1f, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> CREEPERIUM_AXE = ITEMS.register("creeperium_axe", () -> new CreeperiumAxe(ModTiers.CREEPERIUM, 4, 0f, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> CREEPERIUM_HOE = ITEMS.register("creeperium_hoe", () -> new CreeperiumHoe(ModTiers.CREEPERIUM, 0, 0f, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
}
