package com.min01.minsenchantments.enchantment.ocean;

import java.util.List;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.mixin.AbstractArrowInvoker;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

public class EnchantmentAquaticAura extends AbstractOceanEnchantment
{
	public EnchantmentAquaticAura()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.aquaticAuraMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.aquaticAuraMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.aquaticAuraMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public boolean onLivingBreath(LivingEntity entity, boolean canBreathe, int consumeAirAmount, int refillAirAmount, boolean canRefillAir) 
	{
		if(entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).isPresent())
		{
			IEnchantmentCapability cap = entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).orElse(new EnchantmentCapabilityHandler());
			if(cap.hasEnchantment(this))
			{
				EnchantmentData data = cap.getEnchantmentData(this);
				int level = data.getEnchantLevel();
				CompoundTag tag = data.getData();
				if(tag.contains("AuraOwnerUUID"))
				{
					Entity owner = EnchantmentUtil.getEntityByUUID(entity.level, tag.getUUID("AuraOwnerUUID"));
					if(owner == null || entity.distanceTo(owner) >= level * EnchantmentConfig.aquaticAuraRadiusPerLevel.get())
					{
						cap.removeEnchantment(this);
					}
					else
					{
						return false;
					}
				}
			}
		}
		return super.onLivingBreath(entity, canBreathe, consumeAirAmount, refillAirAmount, canRefillAir);
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		int level = EnchantmentHelper.getEnchantmentLevel(this, living);
		if(level > 0)
		{
			List<Projectile> projList = living.level.getEntitiesOfClass(Projectile.class, living.getBoundingBox().inflate(level * EnchantmentConfig.aquaticAuraRadiusPerLevel.get()));
			projList.removeIf((proj) -> (proj.getOwner() != null && proj.getOwner() == living) || proj instanceof ThrownTrident);
			projList.forEach((proj) -> 
			{
				if(proj instanceof AbstractArrow arrow)
				{
					float waterInertia = ((AbstractArrowInvoker)arrow).Invoke_getWaterInertia();
					arrow.setDeltaMovement(arrow.getDeltaMovement().scale(waterInertia));
				}
				else
				{
					proj.setDeltaMovement(proj.getDeltaMovement().scale(0.6F));
				}
				
				if(proj.getDeltaMovement() != Vec3.ZERO && !proj.isOnGround())
				{
					Vec3 vec3 = proj.getDeltaMovement();
					double d5 = vec3.x;
					double d6 = vec3.y;
					double d1 = vec3.z;
			         
					double d7 = proj.getX() + d5;
					double d2 = proj.getY() + d6;
					double d3 = proj.getZ() + d1;
			         
		            for(int j = 0; j < 4; ++j) 
		            {
		                proj.level.addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25D, d2 - d6 * 0.25D, d3 - d1 * 0.25D, d5, d6, d1);
		            }
				}
			});
			List<LivingEntity> list = living.level.getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(level * EnchantmentConfig.aquaticAuraRadiusPerLevel.get()));
			list.removeIf(t -> t == living || t.isAlliedTo(living));
			list.forEach(t -> 
			{
				t.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(cap -> 
				{
					CompoundTag tag = new CompoundTag();
					tag.putUUID("AuraOwnerUUID", living.getUUID());
					cap.setEnchantmentData(this, new EnchantmentData(level, tag));
				});
			});
		}
	}
}
