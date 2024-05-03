package com.min01.minsenchantments.enchantment.ocean;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.min01.minsenchantments.mixin.AbstractArrowInvoker;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentWaterbolt extends AbstractOceanEnchantment implements IProjectileEnchantment
{
	public EnchantmentWaterbolt()
	{
		super(Rarity.UNCOMMON, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.waterboltMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.waterboltMinCost.get();
	}
	
	@Override
	public void onEntityTick(Entity entity)
	{
		if(entity instanceof AbstractArrow arrow)
		{
			arrow.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
			{
				if(t.hasEnchantment(this) && arrow.isInWater())
				{
					float waterInertia = ((AbstractArrowInvoker) arrow).Invoke_getWaterInertia();
					arrow.setDeltaMovement(arrow.getDeltaMovement().x / waterInertia, arrow.getDeltaMovement().y / waterInertia, arrow.getDeltaMovement().z / waterInertia);
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
		CompoundTag tag = new CompoundTag();
		tag.putBoolean(EnchantmentTags.WATER_BOLT, true);
		return tag;
	}
}
