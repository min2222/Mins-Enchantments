package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ToolActions;

@Mixin(LivingEntity.class)
public class MixinLivingEntity
{
	@Inject(at = @At("HEAD"), method = "stopUsingItem", cancellable = true)
    private void stopUsingItem(CallbackInfo ci)
    {
		LivingEntity living = LivingEntity.class.cast(this);
		living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t -> 
		{
			if(t.hasEnchantment(CustomEnchantments.AUTO_SHIELDING.get()))
			{
				ItemStack stack = living.getOffhandItem();
				if(stack.isEmpty())
				{
					stack = living.getMainHandItem();
				}
				if(stack.canPerformAction(ToolActions.SHIELD_BLOCK))
				{
					int shielding = t.getEnchantmentData(CustomEnchantments.AUTO_SHIELDING.get()).getData().getInt(EnchantmentTags.AUTO_SHIELDING);
					if(shielding > 0 && stack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get()) > 0)
					{
						ci.cancel();
					}
				}
			}
		});
    }

	@WrapOperation(method = "travelRidden", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getRiddenSpeed(Lnet/minecraft/world/entity/player/Player;)F"))
    private float travelRidden(LivingEntity instance, Player pPlayer, Operation<Float> original)
    {
		int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.SAILING.get(), pPlayer);
		if(level > 0)
		{
			float scale = level * EnchantmentConfig.sailingSpeedPerLevel.get();
			return original.call(instance, pPlayer) + scale;
		}
		return original.call(instance, pPlayer);
	}
}
