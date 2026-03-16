package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BlessmentMirror extends AbstractBlessment
{
	public BlessmentMirror()
	{
		super(CustomEnchantments.SHIELD, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.mirrorMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.mirrorMinCost.get() + (pLevel - 1) * EnchantmentConfig.mirrorMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onProjectileImpact(Projectile projectile, HitResult ray)
	{
		if(ray instanceof EntityHitResult entityHit)
		{
			Entity entity = entityHit.getEntity();
			if(entity instanceof LivingEntity living && living.isBlocking())
			{
				int level = living.getUseItem().getEnchantmentLevel(this);
				if(level > 0)
				{
					if(Math.random() <= (level * EnchantmentConfig.mirrorReflectChancePerLevel.get()) / 100)
					{
						projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
						projectile.setOwner(living);
						projectile.hurtMarked= true;
					}
				}
			}
		}
	}
	
	@Override
	public void onShieldBlock(LivingEntity living, DamageSource damageSource, float amount)
	{
		Entity source = damageSource.getEntity();
		Entity directSource = damageSource.getDirectEntity();
		if(source instanceof LivingEntity attacker)
		{
			ItemStack stack = living.getOffhandItem();
			if(stack.isEmpty())
			{
				stack = living.getMainHandItem();
			}
			int level = stack.getEnchantmentLevel(this);
			if(level > 0)
			{
				if(!(directSource instanceof Projectile))
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
