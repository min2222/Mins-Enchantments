package com.min01.minsenchantments;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.init.CustomItems;
import com.min01.minsenchantments.init.CustomMenuType;
import com.min01.minsenchantments.network.EnchantmentNetwork;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(MinsEnchantments.MODID)
public class MinsEnchantments
{
	public static final String MODID = "minsenchantments";
	
	public MinsEnchantments() 
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		CustomBlocks.BLOCKS.register(bus);
		CustomBlocks.BLOCK_ENTITIES.register(bus);
		CustomItems.ITEMS.register(bus);
		CustomMenuType.MENUS.register(bus);
		CustomEnchantments.ENCHANTMENTS.register(bus);
		CustomEnchantments.LOOT_MODIFIERS.register(bus);
		EnchantmentNetwork.registerMessages();
		
        EnchantmentConfig.loadConfig(EnchantmentConfig.CONFIG, FMLPaths.CONFIGDIR.get().resolve("mins-enchantments.toml").toString());
	}
}
