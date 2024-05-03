package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity
{
	public MixinLivingEntity(EntityType<?> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
	}
	
	@Inject(at = @At("HEAD"), method = "stopUsingItem", cancellable = true)
    private void stopUsingItem(CallbackInfo ci)
    {
		LivingEntity living = LivingEntity.class.cast(this);
		living.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
		{
			if(t.hasEnchantment(CustomEnchantments.AUTO_SHIELDING.get()))
			{
				ItemStack stack = living.getItemBySlot(EquipmentSlot.OFFHAND);
				if(stack.getItem() instanceof ShieldItem)
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
}
