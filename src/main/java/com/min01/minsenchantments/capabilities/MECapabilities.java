package com.min01.minsenchantments.capabilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class MECapabilities
{
	public static void onAttachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event)
	{
		event.addCapability(IMEnchantmentCapability.ID, new MEnchantmentCapabilityImpl());
	}
	
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
		event.addCapability(IMEnchantmentCapability.ID, new MEnchantmentCapabilityImpl());
	}
}
