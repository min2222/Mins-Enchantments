package com.min01.minsenchantments.init;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.menu.EndEnchantmentMenu;
import com.min01.minsenchantments.menu.NetherEnchantmentMenu;
import com.min01.minsenchantments.menu.OceanEnchantmentMenu;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomMenuType
{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MinsEnchantments.MODID);
    
    public static final RegistryObject<MenuType<OceanEnchantmentMenu>> OCEAN_ENCHANTMENT = MENUS.register("ocean_enchantment", () -> new MenuType<>(OceanEnchantmentMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<NetherEnchantmentMenu>> NETHER_ENCHANTMENT = MENUS.register("nether_enchantment", () -> new MenuType<>(NetherEnchantmentMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<EndEnchantmentMenu>> END_ENCHANTMENT = MENUS.register("end_enchantment", () -> new MenuType<>(EndEnchantmentMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
