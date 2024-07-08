package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BlessmentMirror extends AbstractBlessment
{
	public BlessmentMirror()
	{
		super(CustomEnchantments.SHIELD, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.mirrorMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.mirrorMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.mirrorMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public boolean onProjectileImpact(Projectile projectile, HitResult ray)
	{
		if(ray instanceof EntityHitResult entityHit)
		{
			Entity entity = entityHit.getEntity();
			if(entity instanceof LivingEntity living)
			{
				int level = living.getOffhandItem().getEnchantmentLevel(this);
				if(level > 0 && living.isBlocking())
				{
					if(Math.random() <= (level * EnchantmentConfig.mirrorReflectChancePerLevel.get()) / 100)
					{
						projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
						projectile.setOwner(living);
						projectile.hasImpulse = true;
						return true;
					}
				}
			}
		}
		return super.onProjectileImpact(projectile, ray);
	}
	
	@Override
	public void onLivingAttack(LivingEntity living, DamageSource damageSource, float amount)
	{
		Entity source = damageSource.getEntity();
		Entity directSource = damageSource.getDirectEntity();
		
		if(source != null && source instanceof LivingEntity attacker)
		{
			int level = living.getOffhandItem().getEnchantmentLevel(this);
			if(level > 0 && living.isBlocking())
			{
				if(!(directSource instanceof Projectile proj))
				{
					if(Math.random() <= (level * EnchantmentConfig.mirrorReflectChancePerLevel.get()) / 100)
					{
						attacker.hurt(damageSource, amount);
					}
				}
			}
		}
	}
}
