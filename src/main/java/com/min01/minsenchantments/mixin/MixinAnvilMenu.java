package com.min01.minsenchantments.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.config.MEConfig;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;

@Mixin(AnvilMenu.class)
public class MixinAnvilMenu
{
	@WrapOperation(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;canEnchant(Lnet/minecraft/world/item/ItemStack;)Z"))
	private boolean minsenchantments$createResult(Enchantment instance, ItemStack pStack, Operation<Boolean> original)
	{
		boolean flag = original.call(instance, pStack);
		if(MEConfig.forceEnchanting.get())
		{
			return flag || !pStack.isStackable();
		}
		return flag;
	}
}