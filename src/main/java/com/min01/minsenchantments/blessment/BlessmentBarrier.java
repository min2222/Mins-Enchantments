package com.min01.minsenchantments.blessment;

import java.util.List;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

public class BlessmentBarrier extends AbstractBlessment
{
	public BlessmentBarrier()
	{
		super(EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.barrierMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.barrierMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.barrierMaxCost.get();
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
			float radius = level * EnchantmentConfig.barrierRadiusPerLevel.get();
			
			int i = Mth.ceil((float)Math.PI * radius * radius);

			for(int j = 0; j < i; ++j)
			{
				float f2 = living.getRandom().nextFloat() * ((float)Math.PI * 2F);
				float f3 = Mth.sqrt(living.getRandom().nextFloat()) * radius;
				
	            List<LivingEntity> list = living.level.getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(Mth.cos(f2) * f3, radius, Mth.sin(f2) * f3));
	            List<Projectile> projList = living.level.getEntitiesOfClass(Projectile.class, living.getBoundingBox().inflate(Mth.cos(f2) * f3, radius, Mth.sin(f2) * f3));
	            projList.removeIf((proj) -> proj.getOwner() != null && proj.getOwner() == living);
	            projList.forEach((entity) ->
	            {
	            	Vec3 vec = EnchantmentUtil.fromToVector(living.position(), entity.position(), level * EnchantmentConfig.barrierPushPowerPerLevel.get());
	            	entity.setDeltaMovement(vec.x, entity.getDeltaMovement().y, vec.z);
	            });
	            list.removeIf((entity) -> entity == living);
	            list.removeIf((entity) -> entity instanceof Player && EnchantmentConfig.disableBarrierPushingPlayer.get());
	            list.forEach((entity) ->
	            {
	            	Vec3 vec = EnchantmentUtil.fromToVector(living.position(), entity.position(), level * EnchantmentConfig.barrierPushPowerPerLevel.get());
	            	entity.setDeltaMovement(vec.x, entity.getDeltaMovement().y, vec.z);
	            });
			}
		}
	}
}
