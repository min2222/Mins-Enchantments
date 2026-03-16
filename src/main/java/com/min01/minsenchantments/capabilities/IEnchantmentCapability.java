package com.min01.minsenchantments.capabilities;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

@AutoRegisterCapability
public interface IEnchantmentCapability extends ICapabilitySerializable<CompoundTag>
{
	ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "mins_enchantment");

	void setEntity(Entity entity);

	void tick();
	
	boolean hasEnchantment(Enchantment enchantment);
	
	EnchantmentData getEnchantmentData(Enchantment enchantment);
	
	void setEnchantmentData(Enchantment enchantment, EnchantmentData data);
	
	void removeEnchantment(Enchantment enchantment);
}
