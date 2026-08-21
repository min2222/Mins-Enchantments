package com.min01.minsenchantments.api.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;

public class EntityAddedToWorldEvent extends EntityEvent
{
	public EntityAddedToWorldEvent(Entity entity) 
	{
		super(entity);
	}
}
