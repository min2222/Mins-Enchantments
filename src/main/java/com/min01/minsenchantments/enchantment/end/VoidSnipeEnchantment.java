package com.min01.minsenchantments.enchantment.end;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.event.EntityAddedToWorldEvent;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentCategory;
import com.min01.minsenchantments.mixin.ProjectileInvoker;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class VoidSnipeEnchantment extends MEnchantment
{
	public VoidSnipeEnchantment()
	{
		super(Rarity.VERY_RARE, MEnchantmentCategory.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.END);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 25;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
	
	@Override
	public boolean requiredSummonContext()
	{
		return true;
	}
	
	@Override
	public int getMaxLevel()
	{
		return 3;
	}
	
	@Override
	public void onEntityAddedToWorld(EntityAddedToWorldEvent event) 
	{
		Entity entity = event.getEntity();
		if(entity instanceof Projectile projectile)
		{
			EnchantmentData data = MEUtil.getEnchantmentData(projectile, this);
			if(data != null)
			{
				EnchantmentInstance instance = data.instance();
				Entity owner = MEUtil.getOwner(projectile);
				if(owner != null)
				{
					float dist = instance.level * MEConfig.voideSnipeMaxDistancePerLevel.get().floatValue();
					HitResult result = ProjectileUtil.getHitResultOnViewVector(owner, t -> MEUtil.canHitEntity(t, projectile), dist);
					Vec3 pos = result.getLocation();
					projectile.moveTo(pos);
					((ProjectileInvoker) projectile).minsenchantments$invoke_onHit(result);
					MEUtil.removeEnchantmentData(projectile, this);
				}
			}
		}
	}
}
