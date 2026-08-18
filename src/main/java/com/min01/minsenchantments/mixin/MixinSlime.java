package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.enchantment.MEnchantments;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(Slime.class)
public class MixinSlime
{
	@WrapOperation(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Slime;getSize()I"))
	private int minsenchantments$getSize(Slime instance, Operation<Integer> original)
	{
		LivingEntity entity = instance.getLastHurtByMob();
		if(entity != null)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.CORE_DESTRUCTION.get(), entity);
			if(level > 0)
			{
				return 0;
			}
		}
		return original.call(instance);
	}
}
