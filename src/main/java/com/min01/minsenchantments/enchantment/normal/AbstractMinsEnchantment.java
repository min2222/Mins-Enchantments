package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.api.IMinsEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractMinsEnchantment extends Enchantment implements IMinsEnchantment
{
	public AbstractMinsEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) 
	{
		super(pRarity, pCategory, pApplicableSlots);
	}
}
