package com.min01.minsenchantments.enchantment.sculk;

import com.min01.minsenchantments.enchantment.AbstractCustomEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractSculkEnchantment extends AbstractCustomEnchantment implements ISculkEnchantment
{
	public AbstractSculkEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots)
	{
		super(pRarity, pCategory, pApplicableSlots);
	}
}
