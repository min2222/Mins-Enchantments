package com.min01.minsenchantments.mixin;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

@Mixin(ProjectileUtil.class)
public class MixinProjectileUtil
{
	@ModifyReturnValue(method = "getHitResultOnMoveVector", at = @At("RETURN"))
	private static HitResult minsenchantments$getHitResultOnMoveVector(HitResult original, Entity pProjectile, Predicate<Entity> pFilter)
	{
		if(original instanceof BlockHitResult blockHit && MEUtil.hasEnchantmentData(pProjectile, MEnchantments.CHORUS_PIERCING.get()))
		{
			return BlockHitResult.miss(blockHit.getLocation(), blockHit.getDirection(), blockHit.getBlockPos());
		}
		return original;
	}
}
