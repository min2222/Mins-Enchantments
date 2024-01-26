package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.entity.EquipmentSlot;

public class BlessmentSoulOfTerrarian extends AbstractBlessment
{
	public BlessmentSoulOfTerrarian()
	{
		super(CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.soulOfTerrarianMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.soulOfTerrarianMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.soulOfTerrarianMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
}
