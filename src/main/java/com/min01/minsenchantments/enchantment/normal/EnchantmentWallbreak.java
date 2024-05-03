package com.min01.minsenchantments.enchantment.normal;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentWallbreak extends AbstractMinsEnchantment implements IProjectileEnchantment
{
	public EnchantmentWallbreak()
	{
		super(Rarity.UNCOMMON, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.wallbreakMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.wallbreakMinCost.get();
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
