package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.min01.minsenchantments.enchantment.MEnchantments;

import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(targets = "net.minecraft.client.renderer.FogRenderer$DarknessFogFunction")
public class MixinDarknessFogFunction
{
	@WrapMethod(method = "setupFog")
	private void minsenchantments$setupFog(FogRenderer.FogData data, LivingEntity entity, MobEffectInstance effect, float farPlane, float partialTick, Operation<Void> original)
	{
		int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.SCULK_ADAPTATION.get(), entity);
		if(level > 0)
		{
			data.start = 0.0F;
			data.end = farPlane;
			return;
		}
        original.call(data, entity, effect, farPlane, partialTick);
    }
}
