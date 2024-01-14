package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.enchantment.AbstractCustomEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractOceanEnchantment extends AbstractCustomEnchantment implements IOceanEnchantment
{
	public AbstractOceanEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_)
	{
		super(p_44676_, p_44677_, p_44678_);
	}
}
