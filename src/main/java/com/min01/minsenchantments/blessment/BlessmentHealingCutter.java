package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BlessmentHealingCutter extends AbstractBlessment
{
	public BlessmentHealingCutter() 
	{
		super(EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.healingCutterMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.healingCutterMinCost.get() + (pLevel - 1) * EnchantmentConfig.healingCutterMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public boolean onLivingAttack(LivingEntity entity, DamageSource damageSource, float amount)
	{
		Entity source = damageSource.getEntity();
		if(source instanceof LivingEntity attacker)
		{
			int level = attacker.getMainHandItem().getEnchantmentLevel(this);
			if(level > 0)
			{
				if(Math.random() <= (level * EnchantmentConfig.healingCutterChancePerLevel.get()) / 100)
				{
					entity.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t -> 
					{
						CompoundTag tag = new CompoundTag();
						tag.putFloat(EnchantmentTags.HEALING_CUTTER_DURATION, level * (EnchantmentConfig.healingCutterDurationPerLevel.get() * 20));
						t.setEnchantmentData(this, new EnchantmentData(level, tag));
					});
				}
			}
		}
		return super.onLivingAttack(entity, damageSource, amount);
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t -> 
		{
			if(t.hasEnchantment(this))
			{
				EnchantmentData data = t.getEnchantmentData(this);
				CompoundTag tag = data.getData();
				tag.putFloat(EnchantmentTags.HEALING_CUTTER_DURATION, tag.getFloat(EnchantmentTags.HEALING_CUTTER_DURATION) - 1);
				if(tag.getFloat(EnchantmentTags.HEALING_CUTTER_DURATION) <= 0)
				{
					t.removeEnchantment(this);
				}
			}
		});
	}
	
	@Override
	public float onLivingHeal(LivingEntity living, float amount) 
	{
		if(living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).isPresent())
		{
			IEnchantmentCapability t = living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).orElse(new EnchantmentCapabilityImpl());
			if(t.hasEnchantment(this))
			{
				EnchantmentData data = t.getEnchantmentData(this);
				int level = data.getEnchantLevel();
				
				return amount - (level * EnchantmentConfig.healingCutterAmountPerLevel.get());
			}
		}
		return super.onLivingHeal(living, amount);
	}
}
