package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EventHandlerForge;
import com.min01.minsenchantments.misc.IExtraEntity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

@Mixin(Entity.class)
public class MixinEntity 
{
	@Inject(at = @At("HEAD"), method = "extinguishFire", cancellable = true)
    private void extinguishFire(CallbackInfo ci)
    {
		if(Entity.class.cast(this) instanceof LivingEntity living)
		{
			if(((IExtraEntity) living).isSoulFire())
			{
				Level level = living.level();
				if(!level.isClientSide)
				{
					living.playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
				}
				living.getPersistentData().remove(EventHandlerForge.SOUL_FIRE);
			}
		}
    }
	
	@Inject(at = @At("HEAD"), method = "dampensVibrations", cancellable = true)
    private void dampensVibrations(CallbackInfoReturnable<Boolean> ci)
    {
		if(Entity.class.cast(this) instanceof LivingEntity living)
		{
			if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CONCEALMENT.get(), living) > 0)
			{
				ci.setReturnValue(true);
			}
		}
    }

	@Inject(at = @At("HEAD"), method = "playSound", cancellable = true)
	private void playSound(SoundEvent p_19938_, float p_19939_, float p_19940_, CallbackInfo ci)
	{
		if(Entity.class.cast(this) instanceof LivingEntity living)
		{
			if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CONCEALMENT.get(), living) > 0)
			{
				ci.cancel();
			}
		}
	}
}
