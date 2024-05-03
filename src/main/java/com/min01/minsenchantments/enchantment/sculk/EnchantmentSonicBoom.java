package com.min01.minsenchantments.enchantment.sculk;

import java.util.List;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EnchantmentSonicBoom extends AbstractSculkEnchantment
{
	public EnchantmentSonicBoom()
	{
		super(Rarity.VERY_RARE, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.sonicBoomMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.sonicBoomMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.sonicBoomMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public boolean onEntityJoin(Entity entity, Level world) 
	{
		if(entity instanceof Projectile projectile)
		{
			if(projectile.getOwner() != null)
			{
				Entity owner = projectile.getOwner();
				
				if(owner instanceof LivingEntity living)
				{
					ItemStack stack = living.getUseItem();
					
					if(stack.isEmpty())
					{
						if(living.getMainHandItem().isEmpty())
						{
							stack = living.getOffhandItem();
						}
						else
						{
							stack = living.getMainHandItem();
						}
					}
					
					if(projectile instanceof AbstractArrow arrow)
					{
						int level = stack.getEnchantmentLevel(this);
						if(level > 0)
						{
				            Vec3 vec3 = living.position().add(0, living.getEyeHeight(), 0);
				            Vec3 lookPos = vec3.add(EnchantmentUtil.getLookPos(living.getXRot(), living.getYRot(), 0, 1));
				            Vec3 vec31 = lookPos.subtract(vec3);
				            Vec3 vec32 = vec31.normalize();

				            for(int i = 1; i < Mth.floor(vec31.length()) + (level * EnchantmentConfig.sonicBoomDistancePerLevel.get()); ++i)
				            {
				            	Vec3 vec33 = vec3.add(vec32.scale((double)i));				            	
				            	if(living.level() instanceof ServerLevel serverLevel)
				            	{
				            		serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
				            	}
				            	AABB aabb = new AABB(vec3, vec33);
				            	List<LivingEntity> list = living.level().getEntitiesOfClass(LivingEntity.class, aabb);
				            	list.removeIf((entity1) -> entity1 == living);
				            	list.forEach((entity1) ->
				            	{
				            		entity1.hurt(arrow.damageSources().sonicBoom(living), level * EnchantmentConfig.sonicBoomDamagePerLevel.get());
						            double d1 = 0.5D * (1.0D - entity1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
						            double d0 = 2.5D * (1.0D - entity1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
						            entity1.push(vec32.x() * d0, vec32.y() * d1, vec32.z() * d0);
				            	});
				            }

				            living.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
				            return true;
						}
					}
				}
			}
		}
		return super.onEntityJoin(entity, world);
	}
}
