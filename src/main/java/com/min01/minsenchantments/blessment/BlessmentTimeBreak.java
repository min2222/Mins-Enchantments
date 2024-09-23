package com.min01.minsenchantments.blessment;

import com.min01.entitytimer.TimerUtil;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BlessmentTimeBreak extends AbstractBlessment
{
	public BlessmentTimeBreak()
	{
		super(EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.timeBreakMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.timeBreakMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.timeBreakMaxCost.get();
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
				if(Math.random() <= (level * EnchantmentConfig.timeBreakChancePerLevel.get()) / 100)
				{
					entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
					{
						CompoundTag tag = new CompoundTag();
						tag.putFloat(EnchantmentTags.TIME_BREAK_DURATION, level * (EnchantmentConfig.timeBreakDurationPerLevel.get() * 20));
						t.setEnchantmentData(this, new EnchantmentData(level, tag));
						TimerUtil.setTickrate(entity, 0);
					});
					attacker.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
					{
						if(t.hasEnchantment(this))
						{
							EnchantmentData data = t.getEnchantmentData(this);
							CompoundTag tag = data.getData();
							CompoundTag newTag = new CompoundTag();
							ListTag list = tag.getList("TimeBreakers", 10);
							newTag.putUUID("TimeBreakUUID", entity.getUUID());
							list.add(newTag);
							tag.put("TimeBreakers", list);
							t.setEnchantmentData(this, new EnchantmentData(level, tag));
						}
						else
						{
							CompoundTag tag = new CompoundTag();
							CompoundTag newTag = new CompoundTag();
							ListTag list = new ListTag();
							newTag.putUUID("TimeBreakUUID", entity.getUUID());
							list.add(newTag);
							tag.put("TimeBreakers", list);
							t.setEnchantmentData(this, new EnchantmentData(level, tag));
						}
					});
				}
			}
		}
	}
	
	@Override
	public void onLivingTick(LivingEntity living)
	{
		living.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
		{
			if(t.hasEnchantment(this))
			{
				EnchantmentData data = t.getEnchantmentData(this);
				CompoundTag tag = data.getData();
				if(tag.contains("TimeBreakers", 9))
				{
					ListTag list = tag.getList("TimeBreakers", 10);
					for(int i = 0; i < list.size(); ++i)
					{
						CompoundTag compoundTag = list.getCompound(i);
						Entity entity = EnchantmentUtil.getEntityByUUID(living.level(), compoundTag.getUUID("TimeBreakUUID"));
						if(entity != null)
						{
							entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t2 -> 
							{
								if(t2.hasEnchantment(this))
								{
									entity.invulnerableTime = 0;
									EnchantmentData data2 = t2.getEnchantmentData(this);
									CompoundTag tag2 = data2.getData();
									tag2.putFloat(EnchantmentTags.TIME_BREAK_DURATION, tag2.getFloat(EnchantmentTags.TIME_BREAK_DURATION) - 1);
									if(tag2.getFloat(EnchantmentTags.TIME_BREAK_DURATION) <= 0)
									{
										TimerUtil.resetTickrate(entity);
										t2.removeEnchantment(this);
									}
								}
							});
						}
					}
				}
			}
		});
	}
}
