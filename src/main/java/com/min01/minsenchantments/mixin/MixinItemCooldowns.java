package com.min01.minsenchantments.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.blessment.BlessmentGodHand;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemCooldowns.class)
public class MixinItemCooldowns 
{
	@Shadow
	@Final
	private Map<Item, ItemCooldowns.CooldownInstance> cooldowns;
	
	@Shadow
	private int tickCount;
	
	@Inject(at = @At("HEAD"), method = "addCooldown", cancellable = true)
	private void addCooldown(Item p_41525_, int p_41526_, CallbackInfo ci)
	{
		ItemStack stack = BlessmentGodHand.ITEM_MAP.get(p_41525_);
		if(stack != null)
		{
			if(stack.getEnchantmentLevel(CustomEnchantments.GOD_HAND.get()) > 0)
			{
				ci.cancel();
				int level = stack.getEnchantmentLevel(CustomEnchantments.GOD_HAND.get());
				int cooldown = p_41526_ - (level * (EnchantmentConfig.godHandCooldownPerLevel.get() * 20));
				if(cooldown < 0)
				{
					cooldown = 0;
				}
				this.cooldowns.put(p_41525_, new ItemCooldowns.CooldownInstance(this.tickCount, this.tickCount + cooldown));
				this.onCooldownStarted(p_41525_, cooldown);
			}
		}
	}
	
	@Shadow
	protected void onCooldownStarted(Item p_41529_, int p_41530_)
	{
		
	}
}
