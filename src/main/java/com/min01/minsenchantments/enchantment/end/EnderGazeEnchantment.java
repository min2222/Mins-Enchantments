package com.min01.minsenchantments.enchantment.end;

import java.util.List;
import java.util.UUID;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.event.ProjectileHitEvent;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentCategory;
import com.min01.minsenchantments.misc.MEnchantmentNbtTagKeys;
import com.min01.minsenchantments.util.MEUtil;
import com.min01.tickrateapi.api.event.EntityTickEvent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.phys.Vec3;

public class EnderGazeEnchantment extends MEnchantment
{
	public EnderGazeEnchantment()
	{
		super(Rarity.VERY_RARE, MEnchantmentCategory.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.END);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 10;
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
	public boolean requiredSummonContext()
	{
		return true;
	}
	
	@Override
	public void onProjectileHit(ProjectileHitEvent event) 
	{
		Projectile projectile = event.getProjectile();
		if(MEUtil.hasEnchantmentData(projectile, this))
		{
			MEUtil.removeEnchantmentData(projectile, this);
		}
	}
	
	@Override
	public void onEntityTick(EntityTickEvent event) 
	{
		Entity entity = event.getEntity();
		if(entity instanceof Projectile projectile)
		{
			EnchantmentData data = MEUtil.getEnchantmentData(projectile, this);
			if(data != null)
			{
				CompoundTag tag = data.tag();
				if(tag.hasUUID(MEnchantmentNbtTagKeys.ENDER_GAZE_UUID))
				{
					UUID uuid = tag.getUUID(MEnchantmentNbtTagKeys.ENDER_GAZE_UUID);
					Entity target = MEUtil.getEntityByUUID(projectile.level, uuid);
					if(target != null && target.isAlive())
					{
						Vec3 motion = MEUtil.getVelocityTowards(projectile.position(), target.getEyePosition(), 1.0F);
						projectile.setDeltaMovement(motion);
					}
					else
					{
						tag.remove(MEnchantmentNbtTagKeys.ENDER_GAZE_UUID);
					}
					return;
				}
				EnchantmentInstance instance = data.instance();
				int level = instance.level;
				List<LivingEntity> list = projectile.level.getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(level * MEConfig.enderGazeRadiusPerLevel.get()), t -> MEUtil.canHitEntity(t, projectile));
				double min = -1.0D;
				LivingEntity nearest = null;
				for(LivingEntity living : list)
				{
					double dist = living.distanceToSqr(projectile.getX(), projectile.getY(), projectile.getZ());
					if(min == -1.0D || dist < min) 
					{
						min = dist;
						nearest = living;
					}
				}
				if(nearest != null)
				{
					tag.putUUID(MEnchantmentNbtTagKeys.ENDER_GAZE_UUID, nearest.getUUID());
				}
			}
		}
	}
}
