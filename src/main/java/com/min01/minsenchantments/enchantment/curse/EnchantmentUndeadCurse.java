package com.min01.minsenchantments.enchantment.curse;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentUndeadCurse extends AbstractCurseEnchantment
{
	public EnchantmentUndeadCurse()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[] {EquipmentSlot.HEAD});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.undeadCurseMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.undeadCurseMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.undeadCurseMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		int level = EnchantmentHelper.getEnchantmentLevel(this, living);
		if (level > 0 && living.level().isDay() && !living.level().isClientSide)
		{
			float f = living.getLightLevelDependentMagicValue();
			BlockPos blockpos = BlockPos.containing(living.getX(), living.getEyeY(), living.getZ());
			boolean flag = living.isInWaterRainOrBubble() || living.isInPowderSnow || living.wasInPowderSnow;
			if (f > 0.5F && living.level().random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && living.level().canSeeSky(blockpos)) 
			{
				living.setSecondsOnFire(level * (EnchantmentConfig.undeadCurseFireDurationPerLevel.get() * 20));
			}
		}
	}
}
