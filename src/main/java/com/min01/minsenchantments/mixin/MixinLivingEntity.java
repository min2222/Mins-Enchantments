package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(LivingEntity.class)
public class MixinLivingEntity
{
	@ModifyReturnValue(method = "getDamageAfterArmorAbsorb", at = @At("RETURN"))
	private float minsenchantments$getDamageAfterArmorAbsorb(float original, DamageSource pDamageSource, float pDamageAmount)
	{
		Entity sourceEntity = pDamageSource.getEntity();
		if(sourceEntity instanceof LivingEntity attacker)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.SONIC_CLEAVE.get(), attacker);
			if(level > 0)
			{
				return pDamageAmount;
			}
		}
		return original;
	}
	
	@ModifyExpressionValue(method = "travel", at = @At(value = "CONSTANT", args = "doubleValue=0.05"))
	private double minsenchantments$travel(double original)
	{
		LivingEntity living = (LivingEntity) (Object) this;
		int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.GRAVITY_STABILITY.get(), living);
		if(level > 0)
		{
			float percent = MEUtil.percent((float) original, level * MEConfig.gravityStabilityPercentPerLevel.get().floatValue());
			original -= percent;
		}
		return original;
	}
	
	@ModifyReturnValue(method = "isBlocking", at = @At("RETURN"))
	private boolean minsenchantments$isBlocking(boolean original)
	{
		LivingEntity living = (LivingEntity) (Object) this;
		int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.RESOLUTE_WARD.get(), living);
		if(level > 0)
		{
			double chance = level * MEConfig.resoluteWardChancePerLevel.get();
			if(Math.random() <= chance / 100.0F)
			{
				return true;
			}
		}
		return original;
	}
	
	@WrapMethod(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z")
	private boolean minsenchantments$addEffect(MobEffectInstance pEffectInstance, Entity pEntity, Operation<Boolean> original)
	{
		LivingEntity living = (LivingEntity) (Object) this;
		MobEffect effect = pEffectInstance.getEffect();
		if(effect == MobEffects.WITHER)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.WITHER_PROTECTION.get(), living);
			if(level > 0)
			{
				int duration = pEffectInstance.getDuration();
				int percent = (int) MEUtil.percent(duration, level * MEConfig.witherProtectionPercentPerLevel.get().floatValue());
				pEffectInstance = new MobEffectInstance(effect, Math.max(duration - percent, 0), pEffectInstance.getAmplifier(), pEffectInstance.isAmbient(), pEffectInstance.isVisible(), pEffectInstance.showIcon(), pEffectInstance.hiddenEffect, pEffectInstance.getFactorData());
			}
		}
		return original.call(pEffectInstance, pEntity);
	}
	
	@WrapMethod(method = "updatingUsingItem")
	private void minsenchantments$updatingUsingItem(Operation<Void> original)
	{
		LivingEntity living = (LivingEntity) (Object) this;
		int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.ACCELERATE.get(), living);
		if(level > 0)
		{
			for(int i = 0; i < level; i++)
			{
				original.call();
			}
			return;
		}
		original.call();
	}
}
