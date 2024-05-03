package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

public class EnchantmentSailing extends AbstractOceanEnchantment
{
	public EnchantmentSailing()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.sailingMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.sailingMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.sailingMaxCost.get();
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
		if(living.isPassenger())
		{
			Entity entity = living.getVehicle();
			if(living.zza > 0)
			{
				Vec3 lookPos = entity.position().add(EnchantmentUtil.getLookPos(entity.getXRot(), entity.getYRot(), 0, 0.01F));
				Vec3 motion = EnchantmentUtil.fromToVector(entity.position(), lookPos, level * EnchantmentConfig.sailingSpeedPerLevel.get());
				entity.setDeltaMovement(motion.x, entity.getDeltaMovement().y, motion.z);
			}
		}
	}
}
