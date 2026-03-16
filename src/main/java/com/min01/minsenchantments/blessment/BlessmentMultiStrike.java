package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BlessmentMultiStrike extends AbstractBlessment
{
	public BlessmentMultiStrike() 
	{
		super(EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}

	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.multiStrikeMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.multiStrikeMinCost.get() + (pLevel - 1) * EnchantmentConfig.multiStrikeMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public boolean onLivingAttack(LivingEntity entity, DamageSource damageSource, float amount)
	{
		Entity source = damageSource.getEntity();
		if(source instanceof LivingEntity attacker)
		{
			int level = attacker.getMainHandItem().getEnchantmentLevel(this);
			if(level > 0)
			{
				if(Math.random() <= (level * EnchantmentConfig.multiStrikeChancePerLevel.get()) / 100)
				{
					for(int i = 0; i < level * EnchantmentConfig.multiStrikeAmountPerLevel.get(); i++)
					{
						if(entity.hurt(damageSource, amount))
						{
							entity.invulnerableTime = 0;
						}
					}
				}
			}
		}
		return super.onLivingAttack(entity, damageSource, amount);
	}
}
