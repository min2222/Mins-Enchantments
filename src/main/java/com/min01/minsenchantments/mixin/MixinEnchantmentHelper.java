package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper
{
	@Inject(at = @At("TAIL"), method = "getDamageBonus", cancellable = true)
    private static void getDamageBonus(ItemStack p_44834_, MobType p_44835_, CallbackInfoReturnable<Float> ci)
    {
		if(p_44834_.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get()) > 0)
		{
			int level = p_44834_.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get());
			int currentDurability = p_44834_.getDamageValue();
			if(currentDurability % (EnchantmentConfig.skillfulDurabilityPerLevel.get() / level) == 0)
			{
				float damage = level * EnchantmentConfig.skillfulDamagePerLevel.get();
				if(damage <= level * EnchantmentConfig.skillfulMaxDamagePerLevel.get())
				{
					ci.setReturnValue(ci.getReturnValue() + damage);
				}
				if(damage > level * EnchantmentConfig.skillfulMaxDamagePerLevel.get())
				{
					ci.setReturnValue(ci.getReturnValue() + (level * EnchantmentConfig.skillfulMaxDamagePerLevel.get()));
				}
			}
		}
    }
}
