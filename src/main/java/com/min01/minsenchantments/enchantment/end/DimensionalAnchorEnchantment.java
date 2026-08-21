package com.min01.minsenchantments.enchantment.end;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.event.EntityTeleportToEvent;
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

public class DimensionalAnchorEnchantment extends MEnchantment
{
	public DimensionalAnchorEnchantment() 
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.END);
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
		return 3;
	}
	
	@Override
	public void onEntityTeleportTo(EntityTeleportToEvent event) 
	{
		Entity entity = event.getEntity();
		if(MEUtil.hasEnchantmentData(entity, this))
		{
			event.setCanceled(true);
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
			int duration = tag.getInt(MEnchantmentNbtTagKeys.DIMENSIONAL_ANCHOR_DURATION);
			if(duration <= 0)
			{
				MEUtil.removeEnchantmentData(living, this);
			}
			else
			{
				tag.putInt(MEnchantmentNbtTagKeys.DIMENSIONAL_ANCHOR_DURATION, duration - 1);
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
				double chance = level * MEConfig.dimensionalAnchorChancePerLevel.get();
				if(Math.random() <= chance / 100.0F)
				{
					CompoundTag tag = new CompoundTag();
					tag.putInt(MEnchantmentNbtTagKeys.DIMENSIONAL_ANCHOR_DURATION, level * MEConfig.dimensionalAnchorDurationPerLevel.get());
					MEUtil.addEnchantmentData(living, level, tag, this);
				}
			}
		}
	}
}
