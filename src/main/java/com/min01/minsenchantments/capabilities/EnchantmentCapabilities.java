package com.min01.minsenchantments.capabilities;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class EnchantmentCapabilities
{
	public static void onAttachEntityCapability(AttachCapabilitiesEvent<Entity> event)
	{
		EnchantmentCapabilityImpl cap = new EnchantmentCapabilityImpl();
    	Entity entity = event.getObject();
    	cap.setEntity(entity);
		event.addCapability(EnchantmentCapabilityImpl.ID, cap);
	}
}
