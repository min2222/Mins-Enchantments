package com.min01.minsenchantments.enchantment.bless;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentNbtTagKeys;
import com.min01.minsenchantments.util.MEUtil;
import com.min01.tickrateapi.api.event.EntityTickEvent;
import com.min01.tickrateapi.util.TickrateUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TimeBreakBlessment extends MEnchantment
{
	public TimeBreakBlessment()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.BLESS);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 18;
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
	public void onEntityTick(EntityTickEvent event) 
	{
		Entity entity = event.getEntity();
		if(entity instanceof LivingEntity living)
		{
			EnchantmentData data = MEUtil.getEnchantmentData(living, this);
			if(data != null)
			{
				CompoundTag tag = data.tag();
				int duration = tag.getInt(MEnchantmentNbtTagKeys.TIME_BREAK_DURATION);
				if(duration <= 0)
				{
					MEUtil.removeEnchantmentData(living, this);
				}
				else
				{
					TickrateUtil.setTickrate(entity, 0.0F);
					tag.putInt(MEnchantmentNbtTagKeys.TIME_BREAK_DURATION, duration - 1);
				}
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
				double chance = level * MEConfig.timeBreakChancePerLevel.get();
				if(Math.random() <= chance / 100.0F)
				{
					CompoundTag tag = new CompoundTag();
					tag.putInt(MEnchantmentNbtTagKeys.TIME_BREAK_DURATION, level * MEConfig.timeBreakDurationPerLevel.get());
					MEUtil.addEnchantmentData(living, tag, this);
				}
			}
		}
	}
}
