package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentAutoShielding extends Enchantment
{
	public EnchantmentAutoShielding()
	{
		super(Rarity.VERY_RARE, CustomEnchantments.SHIELD, new EquipmentSlot[] {EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.autoShieldingMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.autoShieldingMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.autoShieldingMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
}
