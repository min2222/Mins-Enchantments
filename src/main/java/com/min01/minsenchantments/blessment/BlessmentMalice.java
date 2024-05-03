package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.config.EnchantmentConfig;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BlessmentMalice extends AbstractBlessment
{
	public BlessmentMalice()
	{
		super(EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.maliceMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.maliceMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.maliceMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity living, DamageSource damageSource, float amount)
	{
		if(damageSource.getEntity() != null)
		{
			Entity entity = damageSource.getEntity();
			
			if(entity instanceof LivingEntity sourceEntity)
			{
				ItemStack stack = sourceEntity.getMainHandItem();
				int level = stack.getEnchantmentLevel(this);
				if(level > 0)
				{
					float percent = level * EnchantmentConfig.malicePercentagePerLevel.get();
					float damage = (living.getHealth() * percent) / 100; 
					return Pair.of(false, amount + damage);
				}
			}
		}
		return super.onLivingDamage(living, damageSource, amount);
	}
}
