package com.min01.minsenchantments.capabilities;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class EnchantmentCapabilities
{
	public static void onAttachEntityCapability(AttachCapabilitiesEvent<Entity> event)
	{
    	Entity entity = event.getObject();
		event.addCapability(EnchantmentCapabilityImpl.ID, new EnchantmentCapabilityImpl(entity));
	}
}
