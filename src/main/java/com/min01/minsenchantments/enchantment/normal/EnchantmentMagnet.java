package com.min01.minsenchantments.enchantment.normal;

import java.util.List;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentMagnet extends AbstractMinsEnchantment
{
	public EnchantmentMagnet()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.magnetMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.magnetMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.magnetMaxCost.get();
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
		if(level > 0 && living.isShiftKeyDown())
		{
			List<ItemEntity> itemList = living.level().getEntitiesOfClass(ItemEntity.class, living.getBoundingBox().inflate(level * EnchantmentConfig.magnetRadiusPerLevel.get()));
			itemList.forEach((item) -> 
			{
				item.setDeltaMovement(EnchantmentUtil.fromToVector(item.position(), living.position(), 0.05F));
			});
			
			List<ExperienceOrb> xpList = living.level().getEntitiesOfClass(ExperienceOrb.class, living.getBoundingBox().inflate(level * EnchantmentConfig.magnetRadiusPerLevel.get()));
			xpList.forEach((xp) -> 
			{
				xp.setDeltaMovement(EnchantmentUtil.fromToVector(xp.position(), living.position(), 0.1F));
			});
		}
	}
}
