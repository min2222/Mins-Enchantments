package com.min01.minsenchantments.enchantment.ocean;

import java.util.List;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.enchantment.curse.AbstractCurseEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentMermaidCurse extends AbstractCurseEnchantment
{
	public EnchantmentMermaidCurse()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.mermaidsCurseMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.mermaidsCurseMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.mermaidsCurseMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		int level = EnchantmentHelper.getEnchantmentLevel(this, living);
		if(level > 0)
		{
			List<Mob> list = living.level().getEntitiesOfClass(Mob.class, living.getBoundingBox().inflate(level * EnchantmentConfig.mermaidsCurseRadiusPerLevel.get()));
			list.forEach((mob) ->
			{
				if(mob.getTarget() != living || mob.getTarget() == null)
				{
					mob.setTarget(living);
				}
			});
		}
	}
}
