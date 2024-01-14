package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.item.enchantment.Enchantment;

@Mixin(Enchantment.class)
public class MixinEnchantment
{
	@Inject(at = @At("HEAD"), method = "isCompatibleWith(Lnet/minecraft/world/item/enchantment/Enchantment;)Z", cancellable = true)
    private void isCompatibleWith(CallbackInfoReturnable<Boolean> ci)
    {
		if(EnchantmentConfig.noEnchantCap.get())
		{
			ci.setReturnValue(true);
		}
    }
}
