package com.min01.minsenchantments.enchantment.nether;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class WitherProtectionEnchantment extends MEnchantment
{
	public WitherProtectionEnchantment() 
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}, EnchantmentType.NETHER);
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
		return 4;
	}
}
