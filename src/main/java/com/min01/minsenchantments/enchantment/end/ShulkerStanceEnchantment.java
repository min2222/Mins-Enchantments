package com.min01.minsenchantments.enchantment.end;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class ShulkerStanceEnchantment extends MEnchantment
{
	public ShulkerStanceEnchantment() 
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}, EnchantmentType.END);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 20;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 4;
	}
	
	@Override
	public void onLivingKnockBack(LivingKnockBackEvent event) 
	{
		LivingEntity entity = event.getEntity();
		if(entity.isShiftKeyDown())
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, entity);
			if(level > 0)
			{
				float strength = event.getOriginalStrength();
				float percent = MEUtil.percent(strength, level * MEConfig.shulkerStancePercentPerLevel.get().floatValue());
				event.setStrength(Math.max(strength - percent, 0.0F));
			}
		}
	}
	
	@Override
	public void onLivingHurt(LivingHurtEvent event) 
	{
		LivingEntity entity = event.getEntity();
		if(entity.isShiftKeyDown())
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, entity);
			if(level > 0)
			{
				float amount = event.getAmount();
				float percent = MEUtil.percent(amount, level * MEConfig.shulkerStancePercentPerLevel.get().floatValue());
				event.setAmount(Math.max(amount - percent, 0.0F));
			}
		}
	}
}
