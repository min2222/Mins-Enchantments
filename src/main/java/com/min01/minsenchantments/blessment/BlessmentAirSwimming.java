package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BlessmentAirSwimming extends AbstractBlessment
{
	public BlessmentAirSwimming() 
	{
		super(EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[] {EquipmentSlot.CHEST});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.airSwimmingMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.airSwimmingMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.airSwimmingMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 1;
	}
}
