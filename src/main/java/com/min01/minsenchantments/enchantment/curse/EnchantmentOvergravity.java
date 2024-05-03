package com.min01.minsenchantments.enchantment.curse;

import com.min01.minsenchantments.config.EnchantmentConfig;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentOvergravity extends AbstractCurseEnchantment
{
	public EnchantmentOvergravity()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.overgravityMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.overgravityMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.overgravityMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		int level = living.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(this);
		if(level > 0)
		{
			living.setDeltaMovement(living.getDeltaMovement().subtract(0, level * EnchantmentConfig.overgravityFallSpeedPerLevel.get(), 0));
		}
	}
	
	@Override
	public Pair<Float, Float> onLivingFall(LivingEntity entity, float distance, float damageMultiplier) 
	{
		int level = entity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(this);
		if(level > 0)
		{
			float multiplier = damageMultiplier + (level *  EnchantmentConfig.overgravityFallDamagePerLevel.get());
			return Pair.of(distance, multiplier);
		}
		return super.onLivingFall(entity, distance, damageMultiplier);
	}
}
