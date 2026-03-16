package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.enchantment.AbstractCustomEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractOceanEnchantment extends AbstractCustomEnchantment implements IOceanEnchantment
{
	public AbstractOceanEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots)
	{
		super(pRarity, pCategory, pApplicableSlots);
	}
}
