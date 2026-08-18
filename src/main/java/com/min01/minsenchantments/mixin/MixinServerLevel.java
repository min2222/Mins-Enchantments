package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.min01.minsenchantments.api.context.SummonContext;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

@Mixin(ServerLevel.class)
public class MixinServerLevel
{
	@WrapMethod(method = "addFreshEntity")
	private boolean minsenchantments$addFreshEntity(Entity pEntity, Operation<Boolean> original)
	{
		SummonContext.peek().ifPresent(stack -> 
		{
			if(stack.enchantment() != null)
			{
				MEUtil.addEnchantmentData(pEntity, stack.stack(), stack.enchantment());
			}
		});
		return original.call(pEntity);
	}
}
