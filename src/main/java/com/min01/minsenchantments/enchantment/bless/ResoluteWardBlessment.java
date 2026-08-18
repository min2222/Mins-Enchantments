package com.min01.minsenchantments.enchantment.bless;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentCategory;

import net.minecraft.world.entity.EquipmentSlot;

public class ResoluteWardBlessment extends MEnchantment
{
	public ResoluteWardBlessment()
	{
		super(Rarity.UNCOMMON, MEnchantmentCategory.SHIELD, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.BLESS);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 10;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
}
