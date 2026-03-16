package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.Phase;

public class EnchantmentWavesProtection extends AbstractOceanEnchantment
{
	public EnchantmentWavesProtection()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.wavesProtectionMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.wavesProtectionMinCost.get() + (pLevel - 1) * EnchantmentConfig.wavesProtectionMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player) 
	{
		player.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t ->
		{
			if(t.hasEnchantment(this))
			{
				EnchantmentData data = t.getEnchantmentData(this);
				CompoundTag tag = data.getData();
				float wave = tag.getFloat(EnchantmentTags.WAVES_PROTECTION);
				if(wave > 0)
				{
					tag.putFloat(EnchantmentTags.WAVES_PROTECTION, wave - 1);
				}
				else
				{
					player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(tag.getDouble(EnchantmentTags.WAVES_PROTECTION_OLD_SPEED));
					t.removeEnchantment(this);
				}
			}
		});
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity entity, DamageSource source, float amount) 
	{
		entity.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t ->
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, entity);
			if(level > 0)
			{
				if(!t.hasEnchantment(this) && entity.isInWater())
				{
					CompoundTag tag = new CompoundTag();
					double oldSpeed = entity.getAttributeBaseValue(ForgeMod.SWIM_SPEED.get());
					tag.putFloat(EnchantmentTags.WAVES_PROTECTION, level * (EnchantmentConfig.wavesProtectionDurationPerLevel.get() * 20));
					tag.putDouble(EnchantmentTags.WAVES_PROTECTION_OLD_SPEED, oldSpeed);
					entity.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(oldSpeed + (level * EnchantmentConfig.wavesProtectionSpeedPerLevel.get()));
					t.setEnchantmentData(this, new EnchantmentData(level, tag));
				}
			}
		});
		return super.onLivingDamage(entity, source, amount);
	}
}
