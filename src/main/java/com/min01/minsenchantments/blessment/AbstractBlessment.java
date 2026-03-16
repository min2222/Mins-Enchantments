package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.enchantment.AbstractCustomEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractBlessment extends AbstractCustomEnchantment implements IBlessment
{
	public AbstractBlessment(EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots)
	{
		super(Rarity.VERY_RARE, pCategory, pApplicableSlots);
	}
}
