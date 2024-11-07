package com.min01.minsenchantments.event;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.api.IMinsEnchantment;
import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Bus.MOD)
public class EventHandler
{
    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event)
    {
		ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(t -> t instanceof IMinsEnchantment || t instanceof IProjectileEnchantment).forEach(t -> 
		{
			CustomEnchantments.LIST.add(t);
		});
    }
}
