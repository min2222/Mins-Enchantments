package com.min01.minsenchantments.api.event;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class ProjectileHitEvent extends ProjectileImpactEvent
{
	public ProjectileHitEvent(Projectile projectile, HitResult ray) 
	{
		super(projectile, ray);
	}
}
