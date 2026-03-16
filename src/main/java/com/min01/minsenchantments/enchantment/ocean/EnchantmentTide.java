package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.Phase;

public class EnchantmentTide extends AbstractOceanEnchantment
{
	public EnchantmentTide()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.tideMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.tideMinCost.get() + (pLevel - 1) * EnchantmentConfig.tideMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player) 
	{
		int level = EnchantmentHelper.getEnchantmentLevel(this, player);
		if(level > 0)
		{
			player.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t -> 
			{
				if(t.hasEnchantment(this))
				{
					EnchantmentData data = t.getEnchantmentData(this);
					CompoundTag tag = data.getData();
					float tide = tag.getFloat(EnchantmentTags.TIDE);
					double swimSpeed = player.getAttributeBaseValue(ForgeMod.SWIM_SPEED.get());
					if(!tag.contains(EnchantmentTags.TIDE_OLD_SPEED))
					{
						 tag.putDouble(EnchantmentTags.TIDE_OLD_SPEED, swimSpeed);
					}
					float maxSpeed = (float) (swimSpeed + (level * EnchantmentConfig.tideMaxSpeedPerLevel.get()));
					float speed = (level * EnchantmentConfig.tideSpeedPerLevel.get()) / 20;
					if(player.isSwimming())
					{
						if(swimSpeed + tide < maxSpeed)
						{
							tag.putFloat(EnchantmentTags.TIDE, tide + speed);
						}
						player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(tag.getDouble(EnchantmentTags.TIDE_OLD_SPEED) + tide);
					}
					else
					{
						player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(tag.getDouble(EnchantmentTags.TIDE_OLD_SPEED));
						t.removeEnchantment(this);
					}
				}
				else
				{
					t.setEnchantmentData(this, new EnchantmentData(level, new CompoundTag()));
				}
			});
		}
	}
}
