package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.enchantment.AbstractCustomEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractBlessment extends AbstractCustomEnchantment implements IBlessment
{
	public AbstractBlessment(EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_)
	{
		super(Rarity.VERY_RARE, p_44677_, p_44678_);
	}
}
