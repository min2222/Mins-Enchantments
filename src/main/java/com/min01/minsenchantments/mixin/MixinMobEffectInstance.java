package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(MobEffectInstance.class)
public class MixinMobEffectInstance
{
	@Shadow
	@Final
	private MobEffect effect;
	
	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;isDurationEffectTick(II)Z"))
	private boolean minsenchantments$isDurationEffectTick(MobEffect instance, int pDuration, int pAmplifier, Operation<Boolean> original, LivingEntity pEntity, Runnable pOnExpirationRunnable)
	{
		if(this.effect == MobEffects.WITHER)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.WITHER_PROTECTION.get(), pEntity);
			if(level > 0)
			{
				int percent = (int) MEUtil.percent(40, level * MEConfig.witherProtectionPercentPerLevel.get().floatValue());
				return MEUtil.isDurationEffectTickWither(pDuration, pAmplifier, 40 + percent);
			}
		}
		return original.call(instance, pDuration, pAmplifier);
	}
}
