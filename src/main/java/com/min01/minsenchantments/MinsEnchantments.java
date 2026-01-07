package com.min01.minsenchantments;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.init.CustomItems;
import com.min01.minsenchantments.init.CustomMenuType;
import com.min01.minsenchantments.network.EnchantmentNetwork;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MinsEnchantments.MODID)
public class MinsEnchantments
{
	public static final String MODID = "minsenchantments";
	
	public MinsEnchantments(FMLJavaModLoadingContext ctx) 
	{
		IEventBus bus = ctx.getModEventBus();
		CustomBlocks.BLOCKS.register(bus);
		CustomBlocks.BLOCK_ENTITIES.register(bus);
		CustomItems.ITEMS.register(bus);
		CustomMenuType.MENUS.register(bus);
		CustomEnchantments.ENCHANTMENTS.register(bus);
		CustomEnchantments.LOOT_MODIFIERS.register(bus);
		EnchantmentNetwork.registerMessages();

		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, EnchantmentCapabilities::attachEntityCapability);
		ctx.registerConfig(Type.COMMON, EnchantmentConfig.CONFIG_SPEC, "mins-enchantments.toml");
	}
}
