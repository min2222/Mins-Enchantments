package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

@Mixin(Projectile.class)
public interface ProjectileInvoker
{
    @Invoker("onHit")
    void minsenchantments$invoke_onHit(HitResult pResult);
}
