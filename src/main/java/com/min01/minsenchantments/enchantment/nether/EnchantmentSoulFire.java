package com.min01.minsenchantments.enchantment.nether;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentSoulFire extends AbstractNetherEnchantment
{
	public EnchantmentSoulFire()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.soulFireMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.soulFireMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.soulFireMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		living.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t ->
		{
			if(t.hasEnchantment(this))
			{
				EnchantmentData data = t.getEnchantmentData(this);
				CompoundTag tag = data.getData();
				int remainingSoulFireTicks = tag.getInt(EnchantmentTags.SOUL_FIRE);
				if (remainingSoulFireTicks > 0) 
				{
					if (!living.fireImmune())
					{
			            if (remainingSoulFireTicks % 20 == 0 && !living.isInLava()) 
			            {
			            	living.hurt(DamageSource.ON_FIRE, 2.0F);
			            }
			            
			            tag.putInt(EnchantmentTags.SOUL_FIRE, remainingSoulFireTicks - 1);
					}
					t.setEnchantmentData(this, new EnchantmentData(data.getEnchantLevel(), tag));
				}
				else
				{
					t.removeEnchantment(this);
				}
			}
		});
	}
	
	@Override
	public void onLivingAttack(LivingEntity living, DamageSource damageSource, float amount)
	{
		Entity source = damageSource.getEntity();
		if(source != null && source instanceof LivingEntity attacker)
		{
			int level = attacker.getMainHandItem().getEnchantmentLevel(this);
			if(!living.isOnFire() && level > 0)
			{
				living.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t ->
				{
					CompoundTag tag = new CompoundTag();
					tag.putInt(EnchantmentTags.SOUL_FIRE, level * (EnchantmentConfig.soulFireDurationPerLevel.get() * 20));
					t.setEnchantmentData(this, new EnchantmentData(level, tag));
				});
			}
		}
	}
}
