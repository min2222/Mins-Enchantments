package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.projectile.AbstractArrow;

@Mixin(AbstractArrow.class)
public interface AbstractArrowInvoker
{
	@Invoker("getWaterInertia")
	public float Invoke_getWaterInertia();
}
