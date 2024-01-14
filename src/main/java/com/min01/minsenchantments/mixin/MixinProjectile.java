package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.misc.EventHandlerForge;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

@Mixin(Projectile.class)
public class MixinProjectile 
{
	@Inject(at = @At("HEAD"), method = "onHit", cancellable = true)
    private void onHit(HitResult p_37260_, CallbackInfo ci)
    {
		if(p_37260_.getType() == HitResult.Type.BLOCK)
		{
			if(Projectile.class.cast(this).getPersistentData().contains(EventHandlerForge.WALLBREAK) || Projectile.class.cast(this).getPersistentData().contains(EventHandlerForge.RECOCHET))
			{
				ci.cancel();
			}
		}
    }
}
