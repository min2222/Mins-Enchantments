package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.enchantment.curse.AbstractCurseEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;

public class EnchantmentFloatingCurse extends AbstractCurseEnchantment
{
	public EnchantmentFloatingCurse()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.floatingCurseMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.floatingCurseMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.floatingCurseMaxCost.get();
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
		if(level > 0 && living.isEyeInFluidType(ForgeMod.WATER_TYPE.get()))
		{
			living.setDeltaMovement(living.getDeltaMovement().add(0, level * EnchantmentConfig.floatingCurseFloatingSpeedPerLevel.get(), 0));
		}
	}
}
