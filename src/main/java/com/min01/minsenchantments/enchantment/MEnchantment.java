package com.min01.minsenchantments.enchantment;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.IMEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MEnchantment extends Enchantment implements IMEnchantment
{
	public final EnchantmentType type;
	
	public MEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots, EnchantmentType type) 
	{
		super(pRarity, pCategory, pApplicableSlots);
		this.type = type;
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
