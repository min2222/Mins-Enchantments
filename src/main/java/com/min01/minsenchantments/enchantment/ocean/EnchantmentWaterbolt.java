package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.entity.EquipmentSlot;

public class EnchantmentWaterbolt extends AbstractOceanEnchantment
{
	public EnchantmentWaterbolt()
	{
		super(Rarity.UNCOMMON, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.waterboltMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.waterboltMinCost.get();
	}
}
