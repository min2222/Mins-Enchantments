package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EventHandlerForge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

@Mixin(Player.class)
public class MixinPlayer 
{
	@Inject(at = @At("TAIL"), method = "getProjectile", cancellable = true)
    private void getProjectile(ItemStack stack, CallbackInfoReturnable<ItemStack> ci)
    {
		if(Player.class.cast(this).isEyeInFluidType(ForgeMod.WATER_TYPE.get()))
		{
			if(stack.getEnchantmentLevel(CustomEnchantments.WATER_JET.get()) > 0 && ci.getReturnValue().isEmpty())
			{
				ItemStack arrow = new ItemStack(Items.ARROW);
				arrow.getOrCreateTag().putBoolean(EventHandlerForge.WATER_JET, true);
				ci.setReturnValue(arrow);
			}
		}
    }
	
	@ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 2)
	private boolean attack(boolean flag2) 
	{
		if(Player.class.cast(this).getMainHandItem().getEnchantmentLevel(CustomEnchantments.CRITICAL_STRIKE.get()) > 0)
		{
			if(this.isCrit())
			{
				return true;
			}
			else
			{
				return flag2;
			}
		}
		return flag2;
	}
	
	@Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/player/CriticalHitEvent;getDamageModifier()F"), remap = false)
	private float getDamageModifier(CriticalHitEvent event) 
	{
		if(Player.class.cast(this).getMainHandItem().getEnchantmentLevel(CustomEnchantments.CRITICAL_STRIKE.get()) > 0)
		{
			if(this.isCrit())
			{
				return 1.5F;
			}
			else
			{
				if(event != null)
				{
					return event.getDamageModifier();
				}
				else
				{
					return 1F;
				}
			}
		}
		return event.getDamageModifier();
	}
	
	private boolean isCrit()
	{
		int level = Player.class.cast(this).getMainHandItem().getEnchantmentLevel(CustomEnchantments.CRITICAL_STRIKE.get());
		return Math.random() <= (level * EnchantmentConfig.criticalStrikeChancePerLevel.get()) / 100;
	}
}
