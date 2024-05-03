package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.enchantment.curse.AbstractCurseEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentSinkingCurse extends AbstractCurseEnchantment
{
	public EnchantmentSinkingCurse()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.sinkingCurseMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.sinkingCurseMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.sinkingCurseMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living)
	{
		int level = EnchantmentHelper.getEnchantmentLevel(this, living);
		if(level > 0 && living.isInWater())
		{
			living.setDeltaMovement(living.getDeltaMovement().subtract(0, level * EnchantmentConfig.sinkingCurseSpeedPerLevel.get(), 0));
		}
	}
}
