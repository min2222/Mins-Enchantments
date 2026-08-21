package com.min01.minsenchantments.enchantment.nether;

import java.util.UUID;

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

public class InfernalBarrageEnchantment extends MEnchantment
{
	public InfernalBarrageEnchantment() 
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.NETHER);
	}

	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 15;
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
	public void onLivingTick(LivingTickEvent event) 
	{
		LivingEntity entity = event.getEntity();
		EnchantmentData data = MEUtil.getEnchantmentData(entity, this);
		if(data != null)
		{
			CompoundTag tag = data.tag();
			int interval = tag.getInt(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_INTERVAL);
			int count = tag.getInt(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_COUNT);
			float damage = tag.getFloat(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_DAMAGE);
			UUID uuid = tag.getUUID(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_UUID);
			Entity attacker = MEUtil.getEntityByUUID(entity.level, uuid);
			if(attacker == null || !attacker.isAlive())
			{
				MEUtil.removeEnchantmentData(entity, this);
				return;
			}
			if(attacker instanceof LivingEntity living)
			{
				if(count > 0)
				{
					if(interval > 0)
					{
						tag.putInt(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_INTERVAL, interval - 1);
					}
					else
					{
						entity.hurt(entity.damageSources().mobAttack(living), damage);
						entity.invulnerableTime = 0;
						tag.putInt(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_COUNT, count - 1);
						tag.putInt(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_INTERVAL, 4);
					}
				}
				else
				{
					MEUtil.removeEnchantmentData(entity, this);
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
		float amount = event.getAmount();
		if(sourceEntity instanceof LivingEntity attacker)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
			if(level > 0)
			{
				double chance = level * MEConfig.infernalBarrageChancePerLevel.get();
				if(Math.random() <= chance / 100.0F)
				{
					CompoundTag tag = new CompoundTag();
					tag.putInt(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_INTERVAL, 4);
					tag.putInt(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_COUNT, level * MEConfig.infernalBarrageCountPerLevel.get());
					tag.putFloat(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_DAMAGE, amount);
					tag.putUUID(MEnchantmentNbtTagKeys.INFERNAL_BARRAGE_UUID, attacker.getUUID());
					MEUtil.addEnchantmentData(living, level, tag, this);
				}
			}
		}
	}
}
