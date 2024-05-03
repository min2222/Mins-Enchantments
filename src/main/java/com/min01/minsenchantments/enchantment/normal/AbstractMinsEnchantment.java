package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.api.IMinsEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractMinsEnchantment extends Enchantment implements IMinsEnchantment
{
	public AbstractMinsEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) 
	{
		super(p_44676_, p_44677_, p_44678_);
	}
}
