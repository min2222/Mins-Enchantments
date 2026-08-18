package com.min01.minsenchantments.event;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.block.MEBlocks;
import com.min01.minsenchantments.blockentity.renderer.BlessmentTableRenderer;
import com.min01.minsenchantments.blockentity.renderer.MEnchantmentTableRenderer;
import com.min01.minsenchantments.menu.MEMenuTypes;
import com.min01.minsenchantments.screen.BlessmentScreen;
import com.min01.minsenchantments.screen.EndEnchantmentScreen;
import com.min01.minsenchantments.screen.NetherEnchantmentScreen;
import com.min01.minsenchantments.screen.OceanEnchantmentScreen;
import com.min01.minsenchantments.screen.SculkEnchantmentScreen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler 
{
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event)
    {
    	event.enqueueWork(() -> 
    	{
    		MenuScreens.register(MEMenuTypes.OCEAN_ENCHANTMENT.get(), OceanEnchantmentScreen::new);
    		MenuScreens.register(MEMenuTypes.NETHER_ENCHANTMENT.get(), NetherEnchantmentScreen::new);
    		MenuScreens.register(MEMenuTypes.END_ENCHANTMENT.get(), EndEnchantmentScreen::new);
    		MenuScreens.register(MEMenuTypes.SCULK_ENCHANTMENT.get(), SculkEnchantmentScreen::new);
    		MenuScreens.register(MEMenuTypes.BLESSMENT.get(), BlessmentScreen::new);
    	});
    }
    
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
    	event.registerLayerDefinition(BlessmentTableRenderer.LAYER_LOCATION, BlessmentTableRenderer::createBodyLayer);
    }
    
    @SubscribeEvent
    public static void onRegisterBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(MEBlocks.ME_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), MEnchantmentTableRenderer::new);
    }
}
