package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class UpstreamEnchantment extends MEnchantment
{
	public UpstreamEnchantment() 
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}, EnchantmentType.OCEAN);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return 5 + pLevel * 7;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return 50;
	}
}
