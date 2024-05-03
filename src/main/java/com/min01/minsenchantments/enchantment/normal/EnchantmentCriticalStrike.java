package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentCriticalStrike extends AbstractMinsEnchantment
{
	public EnchantmentCriticalStrike()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.criticalStrikeMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.criticalStrikeMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.criticalStrikeMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
}
