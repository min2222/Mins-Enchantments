package com.min01.minsenchantments.misc;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.blockentity.renderer.NetherEnchantmentTableRenderer;
import com.min01.minsenchantments.blockentity.renderer.OceanEnchantmentTableRenderer;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.init.CustomMenuType;
import com.min01.minsenchantments.screen.NetherEnchantmentScreen;
import com.min01.minsenchantments.screen.OceanEnchantmentScreen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler 
{
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		MenuScreens.register(CustomMenuType.OCEAN_ENCHANTMENT.get(), OceanEnchantmentScreen::new);
		MenuScreens.register(CustomMenuType.NETHER_ENCHANTMENT.get(), NetherEnchantmentScreen::new);
		
        BlockEntityRenderers.register(CustomBlocks.OCEAN_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), OceanEnchantmentTableRenderer::new);
        BlockEntityRenderers.register(CustomBlocks.NETHER_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), NetherEnchantmentTableRenderer::new);
	}
}
