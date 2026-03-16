package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BlessmentTimeBreak extends AbstractBlessment
{
	public BlessmentTimeBreak()
	{
		super(EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.timeBreakMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.timeBreakMinCost.get() + (pLevel - 1) * EnchantmentConfig.timeBreakMaxCost.get();
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
				if(Math.random() <= (level * EnchantmentConfig.timeBreakChancePerLevel.get()) / 100)
				{
					entity.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t -> 
					{
						CompoundTag tag = new CompoundTag();
						t.setEnchantmentData(this, new EnchantmentData(level, tag));
						EnchantmentUtil.setTickrateWithTime(entity, 0, level * (EnchantmentConfig.timeBreakDurationPerLevel.get() * 20));
					});
				}
			}
		}
		return super.onLivingAttack(entity, damageSource, amount);
	}
}
