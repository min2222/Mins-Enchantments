package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SinkingStrikeEnchantment extends MEnchantment
{
	public SinkingStrikeEnchantment() 
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.OCEAN);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return 5 + pLevel * 15;
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
	public void onLivingHurt(LivingHurtEvent event) 
	{
		LivingEntity living = event.getEntity();
		DamageSource source = event.getSource();
		Entity sourceEntity = source.getEntity();
		if(living.isInWater())
		{
			if(sourceEntity instanceof LivingEntity attacker)
			{
				int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
				if(level > 0)
				{
					float strength = level * MEConfig.sinkingStrikePowerPerLevel.get().floatValue();
					living.push(0, -strength, 0);
				}
			}
		}
	}
}
