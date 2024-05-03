package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentLeech extends AbstractMinsEnchantment
{
	public EnchantmentLeech()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.leechMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.leechMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.leechMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingAttack(LivingEntity entity, DamageSource damageSource, float amount) 
	{
		Entity source = damageSource.getEntity();
		
		if(source != null && source instanceof LivingEntity attacker)
		{
			int level = attacker.getMainHandItem().getEnchantmentLevel(this);
			if(level > 0)
			{
				if(Math.random() <= (level * EnchantmentConfig.leechChancePerLevel.get()) / 100)
				{
					attacker.heal(level * EnchantmentConfig.leechHealAmountPerLevel.get());
				}
			}
		}
	}
}
