package com.min01.minsenchantments.api.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class EntityTeleportToEvent extends EntityEvent
{
	public EntityTeleportToEvent(Entity entity) 
	{
		super(entity);
	}
}
