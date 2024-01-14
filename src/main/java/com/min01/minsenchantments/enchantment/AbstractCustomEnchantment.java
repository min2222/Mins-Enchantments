package com.min01.minsenchantments.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractCustomEnchantment extends Enchantment
{
	public AbstractCustomEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) 
	{
		super(p_44676_, p_44677_, p_44678_);
	}
	
	@Override
	public boolean isTradeable()
	{
		return false;
	}
	
	@Override
	public boolean isDiscoverable() 
	{
		return false;
	}
}
