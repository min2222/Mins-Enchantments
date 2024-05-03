package com.min01.minsenchantments.enchantment.curse;

import com.min01.minsenchantments.api.IMinsEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractCurseEnchantment extends Enchantment implements IMinsEnchantment
{
	public AbstractCurseEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_)
	{
		super(p_44676_, p_44677_, p_44678_);
	}
	
	@Override
	public boolean isTreasureOnly() 
	{
		return true;
	}
	
	@Override
	public boolean isCurse() 
	{
		return true;
	}
}
