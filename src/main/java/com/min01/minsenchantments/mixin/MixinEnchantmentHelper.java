package com.min01.minsenchantments.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper 
{
	@ModifyReturnValue(method = "getAvailableEnchantmentResults", at = @At("RETURN"))
	private static List<EnchantmentInstance> minsenchantments$getAvailableEnchantmentResults(List<EnchantmentInstance> original)
	{
		original.removeIf(t -> t.enchantment instanceof MEnchantment);
		return original;
	}
}
