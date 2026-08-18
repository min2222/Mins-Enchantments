package com.min01.minsenchantments;

import com.min01.minsenchantments.block.MEBlocks;
import com.min01.minsenchantments.capabilities.MECapabilities;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.item.MEItems;
import com.min01.minsenchantments.menu.MEMenuTypes;
import com.min01.minsenchantments.network.MENetwork;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
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

		MEItems.ITEMS.register(bus);
		MEBlocks.BLOCKS.register(bus);
		MEBlocks.BLOCK_ENTITIES.register(bus);
		MEnchantments.ENCHANTMENTS.register(bus);
		MEMenuTypes.MENU_TYPES.register(bus);
		MENetwork.registerMessages();
		
		ctx.registerConfig(Type.COMMON, MEConfig.CONFIG_SPEC, "minsenchantments.toml");
		MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, MECapabilities::onAttachItemStackCapabilities);
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, MECapabilities::onAttachEntityCapabilities);
	}
}
