package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.misc.MEnchantmentTags;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;

@Mixin(VibrationSystem.Listener.class)
public class MixinVibrationSystemListener
{
	@WrapMethod(method = "handleGameEvent")
	private boolean minsenchantments$handleGameEvent(ServerLevel pLevel, GameEvent pGameEvent, GameEvent.Context pContext, Vec3 pPos, Operation<Boolean> original)
	{
        Entity entity = pContext.sourceEntity();
        if(entity instanceof LivingEntity living) 
        {
    		if(pGameEvent.is(MEnchantmentTags.MOVE))
    		{
            	int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.SILENT_STEP.get(), living);
            	if(level > 0)
            	{
        			return false;
            	}
    		}
    		if(pGameEvent.is(MEnchantmentTags.INTERACTIONS))
    		{
            	int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.SILENT_TOUCH.get(), living);
            	if(level > 0)
            	{
        			return false;
            	}
    		}
        }
		return original.call(pLevel, pGameEvent, pContext, pPos);
	}
}
