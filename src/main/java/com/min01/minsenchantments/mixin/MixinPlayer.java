package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

@Mixin(Player.class)
public class MixinPlayer 
{
	@Unique
	private boolean isCrit;
	
	@ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 2)
	private boolean attack(boolean flag2) 
	{
		if(Player.class.cast(this).getMainHandItem().getEnchantmentLevel(CustomEnchantments.CRITICAL_STRIKE.get()) > 0)
		{
			int level = Player.class.cast(this).getMainHandItem().getEnchantmentLevel(CustomEnchantments.CRITICAL_STRIKE.get());
			if(Math.random() <= (level * EnchantmentConfig.criticalStrikeChancePerLevel.get()) / 100)
			{
				this.isCrit = true;
				return true;
			}
			else
			{
				this.isCrit = false;
			}
		}
		return flag2;
	}
	
	@Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/player/CriticalHitEvent;getDamageModifier()F"), remap = false)
	private float getDamageModifier(CriticalHitEvent event) 
	{
		if(Player.class.cast(this).getMainHandItem().getEnchantmentLevel(CustomEnchantments.CRITICAL_STRIKE.get()) > 0)
		{
			return this.isCrit ? 1.5F : 1F;
		}
		return event.getDamageModifier();
	}
}
