package com.min01.minsenchantments.capabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.minsenchantments.network.EnchantmentCapabilityUpdatePacket;
import com.min01.minsenchantments.network.EnchantmentNetwork;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentCapabilityImpl implements IEnchantmentCapability
{
	public static final Capability<IEnchantmentCapability> ENCHANTMENT = CapabilityManager.get(new CapabilityToken<>() {});
	
	private Entity entity;
	private final Map<Enchantment, EnchantmentData> enchantMap = new HashMap<>();
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag tag = new CompoundTag();
		for(Map.Entry<Enchantment, EnchantmentData> entry : this.enchantMap.entrySet())
		{
			if(entry.getKey() != null)
			{
				tag.putString("Enchantment", ForgeRegistries.ENCHANTMENTS.getKey(entry.getKey()).toString());
				tag.put("EnchantData", entry.getValue().save());
			}
		}
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.enchantMap.put(ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.parse(nbt.getString("Enchantment"))), EnchantmentData.read(nbt));
	}

	@Override
	public void setEntity(Entity entity) 
	{
		this.entity = entity;
	}
	
	@Override
	public void tick() 
	{
		this.sendUpdatePacket();
	}
	
	@Override
	public boolean hasEnchantment(Enchantment enchantment)
	{
		return this.enchantMap.containsKey(enchantment);
	}
	
	@Override
	public EnchantmentData getEnchantmentData(Enchantment enchantment) 
	{
		return this.enchantMap.get(enchantment);
	}
	
	@Override
	public void setEnchantmentData(Enchantment enchantment, EnchantmentData data)
	{
		this.enchantMap.put(enchantment, data);
	}
	
	@Override
	public void removeEnchantment(Enchantment enchantment)
	{
		this.enchantMap.remove(enchantment);
	}
	
	public static class EnchantmentData
	{
		private int enchantLevel;
		private CompoundTag data;
		
		public EnchantmentData(int enchantLevel, CompoundTag data)
		{
			this.enchantLevel = enchantLevel;
			this.data = data;
		}
		
		public int getEnchantLevel()
		{
			return this.enchantLevel;
		}
		
		public CompoundTag getData()
		{
			return this.data;
		}
		
		public CompoundTag save()
		{
			CompoundTag tag = new CompoundTag();
			tag.putInt("Level", this.enchantLevel);
			tag.put("Data", this.data);
			return tag;
		}
		
		public static EnchantmentData read(CompoundTag tag)
		{
			return new EnchantmentData(tag.getInt("Level"), tag.getCompound("Data"));
		}
	}
	
	private void sendUpdatePacket() 
	{
		if(!this.entity.level.isClientSide)
		{
			for(Entry<Enchantment, EnchantmentData> entry : this.enchantMap.entrySet())
			{
				if(entry.getKey() != null)
				{
					EnchantmentNetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new EnchantmentCapabilityUpdatePacket(this.entity.getId(), ForgeRegistries.ENCHANTMENTS.getKey(entry.getKey()).toString(), entry.getValue().save()));
				}
			}
		}
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) 
	{
		return ENCHANTMENT.orEmpty(cap, LazyOptional.of(() -> this));
	}
}
