package com.min01.minsenchantments.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

public record EnchantmentData(EnchantmentInstance instance, CompoundTag tag)
{
	public void write(CompoundTag tag)
	{
		tag.putString("Enchantment", ForgeRegistries.ENCHANTMENTS.getKey(this.instance.enchantment).toString());
		tag.putInt("Level", this.instance.level);
		tag.put("Tag", this.tag);
	}
	
	public static EnchantmentData read(CompoundTag tag)
	{
		return new EnchantmentData(new EnchantmentInstance(ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.parse(tag.getString("Enchantment"))), tag.getInt("Level")), tag.getCompound("Tag"));
	}
	
	public void write(FriendlyByteBuf buf)
	{
		buf.writeUtf(ForgeRegistries.ENCHANTMENTS.getKey(this.instance.enchantment).toString());
		buf.writeInt(this.instance.level);
		buf.writeNbt(this.tag);
	}
	
	public static EnchantmentData read(FriendlyByteBuf buf)
	{
		return new EnchantmentData(new EnchantmentInstance(ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.parse(buf.readUtf())), buf.readInt()), buf.readNbt());
	}
}
