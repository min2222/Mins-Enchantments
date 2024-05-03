package com.min01.minsenchantments.enchantment.nether;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class EnchantmentDry extends AbstractNetherEnchantment
{
	public EnchantmentDry()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.dryMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.dryMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.dryMaxCost.get();
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
		if(level > 0)
		{
			int radius = level * EnchantmentConfig.dryRadiusPerLevel.get();
			
			if(EnchantmentUtil.removeWaterBreadthFirstSearch(living.level(), living.blockPosition(), radius))
			{
				living.level().levelEvent(2001, living.blockPosition(), Block.getId(Blocks.WATER.defaultBlockState()));
			}
		}
	}
}
