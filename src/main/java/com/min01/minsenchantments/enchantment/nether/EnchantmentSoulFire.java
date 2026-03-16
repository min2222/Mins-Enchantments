package com.min01.minsenchantments.enchantment.nether;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentSoulFire extends AbstractNetherEnchantment
{
	public EnchantmentSoulFire()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.soulFireMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.soulFireMinCost.get() + (pLevel - 1) * EnchantmentConfig.soulFireMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t ->
		{
			if(t.hasEnchantment(this))
			{
				EnchantmentData data = t.getEnchantmentData(this);
				CompoundTag tag = data.getData();
				int remainingSoulFireTicks = tag.getInt(EnchantmentTags.SOUL_FIRE);
				if(remainingSoulFireTicks > 0) 
				{
					if(!living.fireImmune())
					{
			            if(remainingSoulFireTicks % 20 == 0 && !living.isInLava()) 
			            {
			            	living.hurt(living.damageSources().onFire(), 2.0F);
			            }
			            tag.putInt(EnchantmentTags.SOUL_FIRE, remainingSoulFireTicks - 1);
					}
					t.setEnchantmentData(this, new EnchantmentData(data.getEnchantLevel(), tag));
				}
				else
				{
					t.removeEnchantment(this);
				}
			}
		});
	}
	
	@Override
	public boolean onLivingAttack(LivingEntity living, DamageSource damageSource, float amount)
	{
		Entity source = damageSource.getEntity();
		if(source != null && source instanceof LivingEntity attacker)
		{
			int level = attacker.getMainHandItem().getEnchantmentLevel(this);
			if(!living.isOnFire() && level > 0)
			{
				living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t ->
				{
					CompoundTag tag = new CompoundTag();
					tag.putInt(EnchantmentTags.SOUL_FIRE, level * (EnchantmentConfig.soulFireDurationPerLevel.get() * 20));
					t.setEnchantmentData(this, new EnchantmentData(level, tag));
				});
			}
		}
		return super.onLivingAttack(living, damageSource, amount);
	}
}
