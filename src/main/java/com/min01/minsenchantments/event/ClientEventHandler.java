package com.min01.minsenchantments.event;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.blockentity.renderer.BlessmentTableRenderer;
import com.min01.minsenchantments.blockentity.renderer.EndEnchantmentTableRenderer;
import com.min01.minsenchantments.blockentity.renderer.NetherEnchantmentTableRenderer;
import com.min01.minsenchantments.blockentity.renderer.OceanEnchantmentTableRenderer;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.init.CustomMenuType;
import com.min01.minsenchantments.screen.BlessmentScreen;
import com.min01.minsenchantments.screen.EndEnchantmentScreen;
import com.min01.minsenchantments.screen.NetherEnchantmentScreen;
import com.min01.minsenchantments.screen.OceanEnchantmentScreen;
import com.min01.minsenchantments.screen.SculkEnchantmentScreen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler 
{
	@SubscribeEvent
	public static void onFMLClientSetup(FMLClientSetupEvent event)
	{
		MenuScreens.register(CustomMenuType.OCEAN_ENCHANTMENT.get(), OceanEnchantmentScreen::new);
		MenuScreens.register(CustomMenuType.NETHER_ENCHANTMENT.get(), NetherEnchantmentScreen::new);
		MenuScreens.register(CustomMenuType.END_ENCHANTMENT.get(), EndEnchantmentScreen::new);
		MenuScreens.register(CustomMenuType.SCULK_ENCHANTMENT.get(), SculkEnchantmentScreen::new);
		MenuScreens.register(CustomMenuType.BLESSMENT.get(), BlessmentScreen::new);
		
        BlockEntityRenderers.register(CustomBlocks.OCEAN_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), OceanEnchantmentTableRenderer::new);
        BlockEntityRenderers.register(CustomBlocks.NETHER_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), NetherEnchantmentTableRenderer::new);
        BlockEntityRenderers.register(CustomBlocks.END_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), EndEnchantmentTableRenderer::new);
        BlockEntityRenderers.register(CustomBlocks.BLESSMENT_TABLE_BLOCK_ENTITY.get(), BlessmentTableRenderer::new);
	}
	
	@SubscribeEvent
	public static void onRegisterLayerDefinitions(RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(BlessmentTableRenderer.LAYER_LOCATION, BlessmentTableRenderer::createBodyLayer);
	}
}
