package com.min01.minsenchantments.blessment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class AbstractBlessment extends Enchantment implements IBlessment
{
	public AbstractBlessment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_)
	{
		super(p_44676_, p_44677_, p_44678_);
	}
}
