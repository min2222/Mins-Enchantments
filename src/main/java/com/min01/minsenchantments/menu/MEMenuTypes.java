package com.min01.minsenchantments.menu;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.api.EnchantmentType;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MEMenuTypes 
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MinsEnchantments.MODID);
    
    public static final RegistryObject<MenuType<MEnchantmentMenu>> OCEAN_ENCHANTMENT = MENU_TYPES.register("ocean_enchantment", () -> new MenuType<>((pContainerId, pPlayerInventory) -> new MEnchantmentMenu(pContainerId, pPlayerInventory, EnchantmentType.OCEAN), FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<MEnchantmentMenu>> NETHER_ENCHANTMENT = MENU_TYPES.register("nether_enchantment", () -> new MenuType<>((pContainerId, pPlayerInventory) -> new MEnchantmentMenu(pContainerId, pPlayerInventory, EnchantmentType.NETHER), FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<MEnchantmentMenu>> END_ENCHANTMENT = MENU_TYPES.register("end_enchantment", () -> new MenuType<>((pContainerId, pPlayerInventory) -> new MEnchantmentMenu(pContainerId, pPlayerInventory, EnchantmentType.END), FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<MEnchantmentMenu>> SCULK_ENCHANTMENT = MENU_TYPES.register("sculk_enchantment", () -> new MenuType<>((pContainerId, pPlayerInventory) -> new MEnchantmentMenu(pContainerId, pPlayerInventory, EnchantmentType.SCULK), FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<MEnchantmentMenu>> BLESSMENT = MENU_TYPES.register("blessment", () -> new MenuType<>((pContainerId, pPlayerInventory) -> new MEnchantmentMenu(pContainerId, pPlayerInventory, EnchantmentType.BLESS), FeatureFlags.DEFAULT_FLAGS));
}
