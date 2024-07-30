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
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidType;

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

    @Inject(method = "isInWater", at = @At("TAIL"), cancellable = true)
    private void isInWater(CallbackInfoReturnable<Boolean> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.AIR_SWIMMING.get(), living);
    		if(level > 0)
    		{
    			cir.setReturnValue(true);
    		}
    	}
    }

    @Inject(method = "getEyeInFluidType", at = @At("TAIL"), cancellable = true, remap = false)
    private void getEyeInFluidType(CallbackInfoReturnable<FluidType> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.AIR_SWIMMING.get(), living);
    		if(level > 0)
    		{
    			cir.setReturnValue(ForgeMod.WATER_TYPE.get());
    		}
    	}
    }
}
