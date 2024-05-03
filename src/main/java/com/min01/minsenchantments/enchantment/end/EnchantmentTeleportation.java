package com.min01.minsenchantments.enchantment.end;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.HitResult;

public class EnchantmentTeleportation extends AbstractEndEnchantment implements IProjectileEnchantment
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
	public void onImpact(Projectile projectile, Entity owner, HitResult ray, EnchantmentData data, IEnchantmentCapability cap)
	{
		owner.setPos(ray.getLocation());
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
