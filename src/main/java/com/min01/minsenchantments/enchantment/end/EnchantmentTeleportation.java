package com.min01.minsenchantments.enchantment.end;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

public class EnchantmentTeleportation extends AbstractEndEnchantment
{
	public EnchantmentTeleportation()
	{
		super(Rarity.RARE, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.teleportationMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.teleportationMinCost.get();
	}
	
	@Override
	public int onLivingEntityStopUseItem(LivingEntity entity, @NotNull ItemStack item, int duration) 
	{
		int level = item.getEnchantmentLevel(this);
		if(level > 0)
		{
			entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
			{
				t.setEnchantmentData(this, new EnchantmentData(level, new CompoundTag()));
			});
		}
		return super.onLivingEntityStopUseItem(entity, item, duration);
	}
	
	@Override
	public void onProjectileImpact(Projectile projectile, HitResult ray)
	{
		if(projectile.getOwner() != null)
		{
			Entity owner = projectile.getOwner();
			owner.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
			{
				if(t.hasEnchantment(this))
				{
					owner.setPos(ray.getLocation());
					t.removeEnchantment(this);
				}
			});
		}
	}
}
