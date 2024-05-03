package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@Mixin(Projectile.class)
public class MixinProjectile 
{
	@Inject(at = @At("HEAD"), method = "onHit", cancellable = true)
    private void onHit(HitResult p_37260_, CallbackInfo ci)
    {
		if(p_37260_.getType() == HitResult.Type.ENTITY)
		{
			EntityHitResult entityHit = (EntityHitResult) p_37260_;
			Entity entity = entityHit.getEntity();
			if(entity instanceof LivingEntity living)
			{
				if(living.getOffhandItem().getEnchantmentLevel(CustomEnchantments.MIRROR.get()) > 0 && living.isBlocking())
				{
					ci.cancel();
				}
			}
		}
		
		if(p_37260_.getType() == HitResult.Type.BLOCK)
		{
			Projectile.class.cast(this).getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
			{
				if(t.hasEnchantment(CustomEnchantments.RECOCHET.get()))
				{
					EnchantmentData data = t.getEnchantmentData(CustomEnchantments.RECOCHET.get());
					CompoundTag tag = data.getData();
				    int bounce = tag.getInt(EnchantmentTags.RECOCHET_BOUNCE);
				    int level = data.getEnchantLevel();
				    if(bounce < level * EnchantmentConfig.recochetBouncePerLevel.get())
				    {
						ci.cancel();
				    }
				}
				
				if(t.hasEnchantment(CustomEnchantments.WALLBREAK.get()))
				{
					ci.cancel();
				}
				
				if(t.hasEnchantment(CustomEnchantments.MINER.get()))
				{
					EnchantmentData data = t.getEnchantmentData(CustomEnchantments.MINER.get());
					CompoundTag tag = data.getData();
					int count = tag.getInt(EnchantmentTags.MINER_COUNT);
					int level = data.getEnchantLevel();
					if(count < level * EnchantmentConfig.minerMaxBlockPerLevel.get())
					{
						ci.cancel();
					}
				}
			});
		}
    }
}
