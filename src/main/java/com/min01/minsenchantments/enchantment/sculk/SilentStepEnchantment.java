package com.min01.minsenchantments.enchantment.sculk;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SilentStepEnchantment extends MEnchantment
{
	public SilentStepEnchantment()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET}, EnchantmentType.SCULK);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 15;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
}
