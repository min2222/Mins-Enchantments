package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EventHandlerForge;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

@Mixin(LivingEntity.class)
public class MixinLivingEntity
{
	@Inject(at = @At("HEAD"), method = "stopUsingItem", cancellable = true)
    private void stopUsingItem(CallbackInfo ci)
    {
		LivingEntity living = LivingEntity.class.cast(this);
		if(living.getPersistentData().contains(EventHandlerForge.AUTO_SHIELDING) && living.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ShieldItem)
		{
			ItemStack stack = living.getItemBySlot(EquipmentSlot.OFFHAND);
			int shielding = living.getPersistentData().getInt(EventHandlerForge.AUTO_SHIELDING);
			if(shielding > 0)
			{
				if(stack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get()) > 0)
				{
					ci.cancel();
				}
			}
		}
    }
}
