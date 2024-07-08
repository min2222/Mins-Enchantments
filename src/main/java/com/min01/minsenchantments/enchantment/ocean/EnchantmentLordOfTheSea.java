package com.min01.minsenchantments.enchantment.ocean;

import java.util.List;
import java.util.UUID;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.min01.minsenchantments.util.EnchantmentUtil;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentLordOfTheSea extends AbstractOceanEnchantment
{
	public EnchantmentLordOfTheSea()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.lordoftheseaMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.lordoftheseaMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.lordoftheseaMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		if(living.level instanceof ServerLevel level)
		{
			if(living.getPersistentData().contains(EnchantmentTags.LORD_OF_THE_SEA) && living instanceof WaterAnimal waterAnimal)
			{
				UUID ownerUUID = waterAnimal.getPersistentData().getUUID(EnchantmentTags.LORD_OF_THE_SEA);
				float damage = waterAnimal.getPersistentData().getFloat(EnchantmentTags.LORD_OF_THE_SEA_DMG);
				Entity owner = level.getEntity(ownerUUID);
				if(owner != null && waterAnimal.getTarget() != null)
				{
					Entity target = waterAnimal.getTarget();
					waterAnimal.getLookControl().setLookAt(target, 30.0F, 30.0F);
					double distance = waterAnimal.distanceTo((LivingEntity) target);
					if(distance <= EnchantmentUtil.getAttackReachSqr(waterAnimal, target))
					{
						target.hurt(DamageSource.mobAttack((LivingEntity) owner), damage);
					}
					else
					{
						waterAnimal.getNavigation().moveTo(target, waterAnimal.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
					}
				}
				
				if(owner == null || waterAnimal.getTarget() == null)
				{
					waterAnimal.getPersistentData().remove(EnchantmentTags.LORD_OF_THE_SEA);
					waterAnimal.getPersistentData().remove(EnchantmentTags.LORD_OF_THE_SEA_DMG);
				}
			}
		}
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity living, DamageSource damageSource, float amount) 
	{
		int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.LORD_OF_THE_SEA.get(), living);
		if(level > 0 && damageSource.getEntity() instanceof LivingEntity source && living.isInWater())
		{
			List<WaterAnimal> list = living.level.getEntitiesOfClass(WaterAnimal.class, living.getBoundingBox().inflate(level * EnchantmentConfig.lordoftheseaRadiusPerLevel.get()));
			if(list.size() <= level * EnchantmentConfig.lordoftheseaMaxAnimalsAmountPerLevel.get())
			{
				list.forEach((waterAnimal) ->
				{
					waterAnimal.getPersistentData().putUUID(EnchantmentTags.LORD_OF_THE_SEA, living.getUUID());
					waterAnimal.getPersistentData().putFloat(EnchantmentTags.LORD_OF_THE_SEA_DMG, level * EnchantmentConfig.lordoftheseaAnimalsDamagePerLevel.get());
					waterAnimal.setTarget(source);
				});
			}
		}
		return super.onLivingDamage(living, damageSource, amount);
	}
}
