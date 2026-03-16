package com.min01.minsenchantments.enchantment.nether;

import com.min01.minsenchantments.enchantment.AbstractCustomEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractNetherEnchantment extends AbstractCustomEnchantment implements INetherEnchantment
{
	public AbstractNetherEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) 
	{
		super(pRarity, pCategory, pApplicableSlots);
	}
}
