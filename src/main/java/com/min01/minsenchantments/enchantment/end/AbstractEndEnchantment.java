package com.min01.minsenchantments.enchantment.end;

import com.min01.minsenchantments.enchantment.AbstractCustomEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractEndEnchantment extends AbstractCustomEnchantment implements IEndEnchantment
{
	public AbstractEndEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) 
	{
		super(pRarity, pCategory, pApplicableSlots);
	}
}
