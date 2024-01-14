package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.TridentLoyaltyEnchantment;

public class EnchantmentPoseidonsGrace extends AbstractOceanEnchantment
{
	public EnchantmentPoseidonsGrace()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.TRIDENT, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.poseidonsGraceMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.poseidonsGraceMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.poseidonsGraceMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public boolean checkCompatibility(Enchantment p_44590_)
	{
		return p_44590_ instanceof TridentLoyaltyEnchantment || p_44590_ instanceof EnchantmentSharpWaves ? false : super.checkCompatibility(p_44590_);
	}
}
