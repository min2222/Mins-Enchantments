package com.min01.minsenchantments.blessment;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;

public class BlessmentHoming extends AbstractBlessment implements IProjectileEnchantment
{
	public BlessmentHoming()
	{
		super(CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.homingMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.homingMinCost.get() + (pLevel - 1) * EnchantmentConfig.homingMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onEntityTick(Entity entity) 
	{
		if(entity instanceof Projectile projectile)
		{
			projectile.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(cap ->
			{
				if(cap.hasEnchantment(this))
				{
					if(!projectile.onGround() && projectile.getOwner() != null)
					{
						Entity owner = projectile.getOwner();
						EnchantmentData data = cap.getEnchantmentData(this);
						CompoundTag tag = data.getData();
						int level = data.getEnchantLevel();
						List<LivingEntity> list = projectile.level.getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(level), t -> t != owner || (t instanceof Player && EnchantmentConfig.disableHomingPlayers.get()));
						list.forEach((living) ->
						{
							int time = tag.getInt(EnchantmentTags.HOMING_TIME);
							if(time < level * (EnchantmentConfig.homingMaxHomingTimePerLevel.get() * 20))
							{
								Vec3 vec = EnchantmentUtil.getVelocityTowards(projectile.position(), living.position().add(0, 0.5F, 0), 0.5F * level);
								projectile.setDeltaMovement(vec);
								projectile.hurtMarked = true;
								tag.putInt(EnchantmentTags.HOMING_TIME, time + 1);
							}
						});
					}
				}
			});
		}
	}

	@Override
	public Enchantment self() 
	{
		return this;
	}

	@Override
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		return new CompoundTag();
	}
}
