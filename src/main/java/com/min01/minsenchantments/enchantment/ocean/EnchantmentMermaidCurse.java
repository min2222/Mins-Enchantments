package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.enchantment.curse.AbstractCurseEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentMermaidCurse extends AbstractCurseEnchantment
{
	public EnchantmentMermaidCurse()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.mermaidsCurseMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.mermaidsCurseMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.mermaidsCurseMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
}
