package com.min01.minsenchantments.enchantment.nether;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentFlameThorn extends AbstractNetherEnchantment
{
	public EnchantmentFlameThorn()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.flameThornMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.flameThornMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.flameThornMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
}