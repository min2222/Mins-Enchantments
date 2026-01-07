package com.min01.minsenchantments.mixin;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.blessment.BlessmentGodHand;
import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ServerItemCooldowns;

@Mixin(ServerItemCooldowns.class)
public class MixinServerItemCooldowns extends ItemCooldowns
{
	@Shadow
	@Final
	private ServerPlayer player;
	
	@Override
	public void addCooldown(Item p_41525_, int p_41526_)
	{
		if(BlessmentGodHand.ITEM_MAP.containsKey(this.player.getUUID()))
		{
			Pair<ItemStack, Integer> pair = BlessmentGodHand.ITEM_MAP.get(this.player.getUUID());
			int level = pair.getRight();
			ItemStack stack = pair.getLeft();
			if(stack.is(p_41525_))
			{
				int cooldown = p_41526_ - (level * (EnchantmentConfig.godHandCooldownPerLevel.get() * 20));
				cooldown = Math.max(cooldown, 0);
				super.addCooldown(p_41525_, cooldown);
			}
		}
		else
		{
			super.addCooldown(p_41525_, p_41526_);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "onCooldownEnded", cancellable = true)
	private void onCooldownEnded(Item p_43072_, CallbackInfo ci)
	{
		if(BlessmentGodHand.ITEM_MAP.containsKey(this.player.getUUID()))
		{
			Pair<ItemStack, Integer> pair = BlessmentGodHand.ITEM_MAP.get(this.player.getUUID());
			ItemStack stack = pair.getLeft();
			if(stack.is(p_43072_))
			{
				BlessmentGodHand.ITEM_MAP.remove(this.player.getUUID());
			}
		}
	}
}
