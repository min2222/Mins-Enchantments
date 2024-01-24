package com.min01.minsenchantments.misc;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.init.CustomBlocks;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Bus.MOD)
public class EventHandler
{
    @SubscribeEvent
    public static void registerCreativeTab(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) 
        {
            event.accept(CustomBlocks.OCEAN_ENCHANTMENT_TABLE.get());
            event.accept(CustomBlocks.NETHER_ENCHANTMENT_TABLE.get());
            event.accept(CustomBlocks.END_ENCHANTMENT_TABLE.get());
            event.accept(CustomBlocks.SCULK_ENCHANTMENT_TABLE.get());
            event.accept(CustomBlocks.BLESSMENT_TABLE.get());
        }
    }
}
