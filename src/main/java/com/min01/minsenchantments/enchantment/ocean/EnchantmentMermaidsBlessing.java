package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentMermaidsBlessing extends AbstractOceanEnchantment
{
	public EnchantmentMermaidsBlessing()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.mermaidsBlessingMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.mermaidsBlessingMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.mermaidsBlessingMaxCost.get();
	}
	
	@Override
	public int getMaxLevel()
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		if(living instanceof Mob mob)
		{
			if(mob.getTarget() != null)
			{
				Entity target = mob.getTarget();
				if(target instanceof LivingEntity livingTarget)
				{
					int level = EnchantmentHelper.getEnchantmentLevel(this, livingTarget);
					if(level > 0)
					{
						if(livingTarget.isInWater() && livingTarget instanceof Player)
						{
							if(mob.getHealth() <= level * EnchantmentConfig.mermaidsBlessingMaxMobHealthPerLevel.get() && mob.getAttribute(Attributes.ATTACK_DAMAGE) != null && mob.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) <= level * EnchantmentConfig.mermaidsBlessingMaxMobDamagePerLevel.get())
							{
								mob.setTarget(null);
							}
						}
					}
				}
			}
		}
	}
}
