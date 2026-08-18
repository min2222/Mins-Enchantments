package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.enchantment.MEnchantments;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;

@Mixin(PiglinAi.class)
public class MixinPiglinAi
{
	@WrapOperation(method = "isWearingGold", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;makesPiglinsNeutral(Lnet/minecraft/world/entity/LivingEntity;)Z", remap = false))
	private static boolean minsenchantments$makesPiglinsNeutral(ItemStack instance, LivingEntity livingEntity, Operation<Boolean> original)
	{
		if(instance.getEnchantmentLevel(MEnchantments.PIGLIN_DECEPTION.get()) > 0)
		{
			return true;
		}
		return original.call(instance, livingEntity);
	}
}
