package com.min01.minsenchantments.event;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.api.IMinsEnchantment;
import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.init.CustomItems;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Bus.MOD)
public class EventHandler
{
    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) 
        {
            event.accept(CustomBlocks.OCEAN_ENCHANTMENT_TABLE.get());
            event.accept(CustomBlocks.NETHER_ENCHANTMENT_TABLE.get());
            event.accept(CustomBlocks.END_ENCHANTMENT_TABLE.get());
            event.accept(CustomBlocks.SCULK_ENCHANTMENT_TABLE.get());
            event.accept(CustomBlocks.BLESSMENT_TABLE.get());
        }
        
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) 
        {
        	event.accept(CustomItems.HOLY_EMBLEM.get());
        }
    }
    
    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event)
    {
		ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(t -> t instanceof IMinsEnchantment || t instanceof IProjectileEnchantment).forEach(t -> 
		{
			CustomEnchantments.LIST.add(t);
		});
    }
}
