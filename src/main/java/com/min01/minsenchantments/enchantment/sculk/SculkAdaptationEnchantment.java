package com.min01.minsenchantments.enchantment.sculk;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SculkAdaptationEnchantment extends MEnchantment
{
	public SculkAdaptationEnchantment()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[] {EquipmentSlot.HEAD}, EnchantmentType.SCULK);
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
	
	@Override
	public int getMaxLevel() 
	{
		return 2;
	}
}
