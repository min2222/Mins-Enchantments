package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentCategory;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;

public class BubbleGuardEnchantment extends MEnchantment
{
	public BubbleGuardEnchantment() 
	{
		super(Rarity.UNCOMMON, MEnchantmentCategory.SHIELD, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.OCEAN);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 10;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public void onShieldBlock(ShieldBlockEvent event)
	{
		LivingEntity entity = event.getEntity();
		int level = EnchantmentHelper.getEnchantmentLevel(this, entity);
		if(level > 0)
		{
			int maxAirSupply = entity.getMaxAirSupply();
			int percent = (int) MEUtil.percent(maxAirSupply, level * MEConfig.bubbleGuardPercentPerLevel.get().floatValue());
			entity.setAirSupply(Math.min(entity.getAirSupply() + percent, maxAirSupply));
		}
	}
}
