package com.min01.minsenchantments.enchantment.bless;

import java.util.List;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;

public class BarrierBlessment extends MEnchantment
{
	public BarrierBlessment()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}, EnchantmentType.BLESS);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 17;
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
	public void onLivingTick(LivingTickEvent event) 
	{
		LivingEntity living = event.getEntity();
		if(!living.level.isClientSide)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, living);
			if(level > 0)
			{
				List<Projectile> list = living.level.getEntitiesOfClass(Projectile.class, living.getBoundingBox().inflate(level * MEConfig.barrierRadiusPerLevel.get()), t -> this.canPush(t, living));
				list.forEach(t -> 
				{
					Vec3 motion = MEUtil.getVelocityTowards(living.position(), t.position(), level * MEConfig.barrierPowerPerLevel.get().floatValue());
					t.setDeltaMovement(motion);
				});
			}
		}
	}
	
	public boolean canPush(Projectile projectile, LivingEntity living)
	{
		Entity owner = projectile.getOwner();
		if(owner != null)
		{
			return owner != living && !living.isAlliedTo(owner);
		}
		return true;
	}
}
