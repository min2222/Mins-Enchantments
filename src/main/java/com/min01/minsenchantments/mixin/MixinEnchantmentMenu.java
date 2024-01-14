package com.min01.minsenchantments.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.minsenchantments.enchantment.ICustomEnchantment;

import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

@Mixin(EnchantmentMenu.class)
public class MixinEnchantmentMenu 
{
	@Inject(at = @At("TAIL"), method = "getEnchantmentList", cancellable = true)
    private void getEnchantmentList(ItemStack p_39472_, int p_39473_, int p_39474_, CallbackInfoReturnable<List<EnchantmentInstance>> ci)
    {
		List<EnchantmentInstance> list = ci.getReturnValue();
		list.removeIf((instance) -> instance.enchantment instanceof ICustomEnchantment);
		ci.setReturnValue(list);
    }
}
