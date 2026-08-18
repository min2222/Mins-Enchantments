package com.min01.minsenchantments.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.min01.minsenchantments.api.context.CooldownContext;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantments;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemCooldowns.class)
public class MixinItemCooldowns 
{
	@WrapMethod(method = "addCooldown")
	private void minsenchantments$addCooldown(Item pItem, int pTicks, Operation<Void> original)
	{
		Optional<ItemStack> optional = CooldownContext.peek();
		if(optional.isPresent())
		{
			ItemStack stack = optional.get();
			int level = stack.getEnchantmentLevel(MEnchantments.MASTER_TOUCH.get());
			int tick = pTicks - (level * MEConfig.masterTouchCooldownPerLevel.get());
			if(tick > 0)
			{
				original.call(pItem, tick);
			}
			return;
		}
		original.call(pItem, pTicks);
	}
}
