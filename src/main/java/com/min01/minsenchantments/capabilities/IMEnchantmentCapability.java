package com.min01.minsenchantments.capabilities;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.api.EnchantmentData;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

@AutoRegisterCapability
public interface IMEnchantmentCapability extends ICapabilitySerializable<CompoundTag>
{
	ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "menchantments");
	
	void addEnchantmentData(Entity entity, ItemStack stack, EnchantmentData data);
	
	void removeEnchantmentData(Entity entity, ItemStack stack, Enchantment enchantment);
	
	boolean hasEnchantmentData(Enchantment enchantment);
	
	EnchantmentData getEnchantmentData(Enchantment enchantment);
	
	void tick(Entity entity, ItemStack stack);
}
