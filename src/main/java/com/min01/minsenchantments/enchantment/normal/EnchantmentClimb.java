package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentClimb extends AbstractMinsEnchantment
{
	public EnchantmentClimb()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.climbMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.climbMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.climbMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		int level = EnchantmentHelper.getEnchantmentLevel(this, living);
		if(level > 0)
		{
			if(living.horizontalCollision)
			{
				double climbSpeed = level * EnchantmentConfig.climbSpeedPerLevel.get();
			    if(living.isShiftKeyDown())
			    {
			    	living.setDeltaMovement(living.getDeltaMovement().x, 0.0, living.getDeltaMovement().z);
			    }
			    else
			    {
					living.setDeltaMovement(living.getDeltaMovement().x, climbSpeed, living.getDeltaMovement().z);
			    }
				living.fallDistance = 0.0F;
			}
		}
	}
}
