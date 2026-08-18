package com.min01.minsenchantments.enchantment.bless;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.event.ProjectileHitEvent;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentCategory;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;

public class MirrorBlessment extends MEnchantment
{
	public MirrorBlessment()
	{
		super(Rarity.UNCOMMON, MEnchantmentCategory.SHIELD, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.BLESS);
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
	public void onProjectileHit(ProjectileHitEvent event) 
	{
		HitResult result = event.getRayTraceResult();
		Projectile projectile = event.getProjectile();
		if(result instanceof EntityHitResult entityHit)
		{
			Entity entity = entityHit.getEntity();
			if(entity instanceof LivingEntity living && living.isBlocking())
			{
				int level = EnchantmentHelper.getEnchantmentLevel(this, living);
				if(level > 0)
				{
					double chance = level * MEConfig.mirrorChancePerLevel.get();
					if(Math.random() <= chance / 100.0F)
					{
						projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
						projectile.setOwner(living);
						event.cancel();
					}
				}
			}
		}
	}
	
	@Override
	public void onShieldBlock(ShieldBlockEvent event) 
	{
		LivingEntity entity = event.getEntity();
		DamageSource source = event.getDamageSource();
		Entity sourceEntity = source.getEntity();
		float damage = event.getOriginalBlockedDamage();
		int level = EnchantmentHelper.getEnchantmentLevel(this, entity);
		if(level > 0)
		{
			double chance = level * MEConfig.mirrorChancePerLevel.get();
			if(Math.random() <= chance / 100.0F)
			{
				DamageSource copy = new DamageSource(source.typeHolder(), entity, entity, entity.position());
				sourceEntity.hurt(copy, damage);
			}
		}
	}
}
