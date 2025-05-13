package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
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
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.timeBreakMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.timeBreakMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.timeBreakMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingAttack(LivingEntity entity, DamageSource damageSource, float amount)
	{
		Entity source = damageSource.getEntity();
		
		if(source != null && source instanceof LivingEntity attacker)
		{
			int level = attacker.getMainHandItem().getEnchantmentLevel(this);
			if(level > 0)
			{
				if(Math.random() <= (level * EnchantmentConfig.timeBreakChancePerLevel.get()) / 100)
				{
					entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
					{
						CompoundTag tag = new CompoundTag();
						t.setEnchantmentData(this, new EnchantmentData(level, tag));
						EnchantmentUtil.setTickrateWithTime(entity, 0, level * (EnchantmentConfig.timeBreakDurationPerLevel.get() * 20));
					});
				}
			}
		}
	}
}
