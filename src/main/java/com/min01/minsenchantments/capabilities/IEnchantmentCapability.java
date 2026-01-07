package com.min01.minsenchantments.capabilities;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEnchantmentCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "mins_enchantment");

	void setEntity(Entity entity);

	void update();
	
	boolean hasEnchantment(Enchantment enchantment);
	
	EnchantmentData getEnchantmentData(Enchantment enchantment);
	
	void setEnchantmentData(Enchantment enchantment, EnchantmentData data);
	
	void removeEnchantment(Enchantment enchantment);
}
