package com.min01.minsenchantments.capabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.min01.minsenchantments.network.EnchantmentCapabilityUpdatePacket;
import com.min01.minsenchantments.network.EnchantmentNetwork;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentCapabilityHandler implements IEnchantmentCapability
{
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
		this.enchantMap.put(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(nbt.getString("Enchantment"))), EnchantmentData.read(nbt));
	}

	@Override
	public void setEntity(Entity entity) 
	{
		this.entity = entity;
	}

	@Override
	public void update()
	{
		
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
		this.sendUpdatePacket();
	}
	
	@Override
	public void removeEnchantment(Enchantment enchantment)
	{
		this.enchantMap.remove(enchantment);
		this.sendUpdatePacket();
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
		if(this.entity instanceof ServerPlayer)
		{
			for(Entry<Enchantment, EnchantmentData> entry : this.enchantMap.entrySet())
			{
				if(entry.getKey() != null)
				{
					EnchantmentNetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new EnchantmentCapabilityUpdatePacket(this.entity, entry.getKey(), entry.getValue()));
				}
			}
		}
	}
}
