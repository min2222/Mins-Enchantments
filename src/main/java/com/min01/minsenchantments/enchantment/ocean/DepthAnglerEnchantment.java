package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DepthAnglerEnchantment extends MEnchantment
{
	public DepthAnglerEnchantment() 
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.FISHING_ROD, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.OCEAN);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return 5 + pLevel * 10;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
}
