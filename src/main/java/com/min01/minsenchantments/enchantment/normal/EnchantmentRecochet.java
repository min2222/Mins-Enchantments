package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentRecochet extends Enchantment
{
	public EnchantmentRecochet()
	{
		super(Rarity.UNCOMMON, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.recochetMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.recochetMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.recochetMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
}
