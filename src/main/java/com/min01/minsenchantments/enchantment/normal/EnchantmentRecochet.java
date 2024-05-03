package com.min01.minsenchantments.enchantment.normal;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EnchantmentRecochet extends AbstractMinsEnchantment implements IProjectileEnchantment
{
	public EnchantmentRecochet()
	{
		super(Rarity.UNCOMMON, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.recochetMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.recochetMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.recochetMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}

	@Override
	public Enchantment self() 
	{
		return this;
	}

	@Override
	public void onImpact(Projectile projectile, Entity owner, HitResult ray, EnchantmentData data, IEnchantmentCapability cap) 
	{
		if(ray.getType() == HitResult.Type.BLOCK)
		{
			CompoundTag tag = data.getData();
		    int bounce = tag.getInt(EnchantmentTags.RECOCHET_BOUNCE);
		    int level = data.getEnchantLevel();
		    double motionx = projectile.getDeltaMovement().x;
		    double motiony = projectile.getDeltaMovement().y;
		    double motionz = projectile.getDeltaMovement().z;

			Direction direction = ((BlockHitResult) ray).getDirection();
			
			if(direction == Direction.EAST) 
			{
				motionx = -motionx;
			}
			else if(direction == Direction.SOUTH) 
			{
				motionz = -motionz;
			}
			else if(direction == Direction.WEST) 
			{
				motionx = -motionx;
			}
			else if(direction == Direction.NORTH)
			{
				motionz = -motionz;
			}
			else if(direction == Direction.UP)
			{
				motiony = -motiony;
			}
			else if(direction == Direction.DOWN)
			{
				motiony = -motiony;
			}
			
			if(bounce < level * EnchantmentConfig.recochetBouncePerLevel.get()) 
			{
				projectile.setDeltaMovement(motionx, motiony, motionz);
				tag.putInt(EnchantmentTags.RECOCHET_BOUNCE, bounce + 1);
				cap.setEnchantmentData(this, new EnchantmentData(level, tag));
			}
		}
	}

	@Override
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(EnchantmentTags.RECOCHET_BOUNCE, 0);
		return tag;
	}
}
