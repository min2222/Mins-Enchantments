package com.min01.minsenchantments.enchantment.end;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.event.ProjectileHitEvent;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentCategory;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public class ChorusPiercingEnchantment extends MEnchantment
{
	public ChorusPiercingEnchantment()
	{
		super(Rarity.COMMON, MEnchantmentCategory.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.END);
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
	public boolean requiredSummonContext() 
	{
		return true;
	}
	
	@Override
	public void onProjectileHit(ProjectileHitEvent event) 
	{
		Projectile projectile = event.getProjectile();
		HitResult result = event.getRayTraceResult();
		if(result.getType() == Type.BLOCK)
		{
			if(MEUtil.hasEnchantmentData(projectile, this))
			{
				event.cancel();
			}
		}
	}
}
