package com.min01.minsenchantments.enchantment.sculk;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentNbtTagKeys;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LifeErosionEnchantment extends MEnchantment
{
	public LifeErosionEnchantment() 
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.SCULK);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 17;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public void onLivingHeal(LivingHealEvent event)
	{
		LivingEntity living = event.getEntity();
		EnchantmentData data = MEUtil.getEnchantmentData(living, this);
		if(data != null)
		{
			EnchantmentInstance instance = data.instance();
			int level = instance.level;
			float amount = event.getAmount();
			float reduced = MEUtil.percent(amount, level * MEConfig.lifeErosionPercentPerLevel.get().floatValue());
			event.setAmount(amount - reduced);
		}
	}
	
	@Override
	public void onLivingTick(LivingTickEvent event) 
	{
		LivingEntity living = event.getEntity();
		EnchantmentData data = MEUtil.getEnchantmentData(living, this);
		if(data != null)
		{
			CompoundTag tag = data.tag();
			int duration = tag.getInt(MEnchantmentNbtTagKeys.LIFE_EROSION_DURATION);
			if(duration <= 0)
			{
				MEUtil.removeEnchantmentData(living, this);
			}
			else
			{
				tag.putInt(MEnchantmentNbtTagKeys.LIFE_EROSION_DURATION, duration - 1);
			}
		}
	}
	
	@Override
	public void onLivingHurt(LivingHurtEvent event) 
	{
		LivingEntity living = event.getEntity();
		DamageSource source = event.getSource();
		Entity sourceEntity = source.getEntity();
		if(sourceEntity instanceof LivingEntity attacker)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
			if(level > 0)
			{
				double chance = level * MEConfig.lifeErosionChancePerLevel.get();
				if(Math.random() <= chance / 100.0F)
				{
					CompoundTag tag = new CompoundTag();
					tag.putInt(MEnchantmentNbtTagKeys.LIFE_EROSION_DURATION, level * MEConfig.lifeErosionDurationPerLevel.get());
					MEUtil.addEnchantmentData(living, tag, this);
				}
			}
		}
	}
}
