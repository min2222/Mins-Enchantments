package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentCategory;

import net.minecraft.world.entity.EquipmentSlot;

public class HydrodynamicsEnchantment extends MEnchantment
{
	public HydrodynamicsEnchantment() 
	{
		super(Rarity.UNCOMMON, MEnchantmentCategory.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.OCEAN);
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
	public boolean requiredSummonContext() 
	{
		return true;
	}
}
