package com.min01.minsenchantments.enchantment.sculk;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SilentTouchEnchantment extends MEnchantment
{
	public SilentTouchEnchantment()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.SCULK);
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
}
