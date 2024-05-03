package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.minsenchantments.event.EntityTickEvent;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;

@Mixin(Entity.class)
public class MixinEntity
{	
	@Inject(at = @At("TAIL"), method = "tick", cancellable = true)
    private void tick(CallbackInfo ci)
    {
		MinecraftForge.EVENT_BUS.post(new EntityTickEvent(Entity.class.cast(this)));
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
