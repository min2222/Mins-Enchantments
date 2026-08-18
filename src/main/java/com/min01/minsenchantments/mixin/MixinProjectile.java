package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.min01.minsenchantments.api.event.ProjectileHitEvent;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;

@Mixin(Projectile.class)
public class MixinProjectile
{
	@WrapMethod(method = "onHit")
	private void minsenchantments$onHit(HitResult pResult, Operation<Void> original)
	{
		Projectile projectile = (Projectile) (Object) this;
		if(MinecraftForge.EVENT_BUS.post(new ProjectileHitEvent(projectile, pResult)))
		{
			return;
		}
		original.call(pResult);
	}
}
