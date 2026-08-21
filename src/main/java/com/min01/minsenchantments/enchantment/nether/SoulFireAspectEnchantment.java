package com.min01.minsenchantments.enchantment.nether;

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
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SoulFireAspectEnchantment extends MEnchantment
{
	public SoulFireAspectEnchantment() 
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.NETHER);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return 5 + pLevel * 7;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 2;
	}
	
	@Override
	public void onLivingTick(LivingTickEvent event)
	{
		LivingEntity living = event.getEntity();
		EnchantmentData data = MEUtil.getEnchantmentData(living, this);
		if(data != null)
		{
			CompoundTag tag = data.tag();
			int duration = tag.getInt(MEnchantmentNbtTagKeys.SOUL_FIRE_ASPECT_DURATION);
			if(duration > 0)
			{
				tag.putInt(MEnchantmentNbtTagKeys.SOUL_FIRE_ASPECT_DURATION, duration - 1);
			}
			else
			{
				MEUtil.removeEnchantmentData(living, this);
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
				int duration = level * MEConfig.soulFireAspectDurationPerLevel.get();
				CompoundTag tag = new CompoundTag();
				tag.putInt(MEnchantmentNbtTagKeys.SOUL_FIRE_ASPECT_DURATION, duration);
				MEUtil.addEnchantmentData(living, level, tag, this);
				living.setSecondsOnFire(duration / 20);
			}
		}
	}
}
