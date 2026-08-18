package com.min01.minsenchantments.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.network.MENetwork;
import com.min01.minsenchantments.network.MEnchantmentCapabilityRemovePacket;
import com.min01.minsenchantments.network.MEnchantmentCapabilityUpdatePacket;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class MEnchantmentCapabilityImpl implements IMEnchantmentCapability
{
	public static final Capability<IMEnchantmentCapability> MENCHANTMENTS = CapabilityManager.get(new CapabilityToken<>() {});
	
	public final ObjectArrayList<EnchantmentData> data = new ObjectArrayList<>();
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag nbt = new CompoundTag();
		ListTag list = new ListTag();
		for(EnchantmentData data : this.data)
		{
			CompoundTag tag = new CompoundTag();
			data.write(tag);
			list.add(tag);
		}
		nbt.put("EnchantmentData", list);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		ListTag list = nbt.getList("EnchantmentData", Tag.TAG_COMPOUND);
		for(int i = 0; i < list.size(); i++)
		{
			CompoundTag tag = list.getCompound(i);
			this.data.add(EnchantmentData.read(tag));
		}
	}

	@Override
	public void addEnchantmentData(Entity entity, ItemStack stack, EnchantmentData data)
	{
		this.removeEnchantmentData(entity, stack, data.instance().enchantment);
		this.data.add(data);
		this.sendUpdatePacket(entity, stack, data);
	}

	@Override
	public void removeEnchantmentData(Entity entity, ItemStack stack, Enchantment enchantment)
	{
		this.data.removeIf(t -> t.instance().enchantment.equals(enchantment));
		this.sendRemovePacket(entity, stack, enchantment);
	}
	
	@Override
	public EnchantmentData getEnchantmentData(Enchantment enchantment)
	{
		for(EnchantmentData data : this.data)
		{
			if(data.instance().enchantment.equals(enchantment))
			{
				return data;
			}
		}
		return null;
	}
	
	@Override
	public boolean hasEnchantmentData(Enchantment enchantment)
	{
		return this.getEnchantmentData(enchantment) != null;
	}
	
	@Override
	public void tick(Entity entity, ItemStack stack) 
	{
		if(entity.tickCount <= 4)
		{
			for(EnchantmentData data : this.data)
			{
				this.sendUpdatePacket(entity, stack, data);
			}
		}
	}
	
	public void sendRemovePacket(Entity entity, ItemStack stack, Enchantment enchantment)
	{
		if(!entity.level.isClientSide)
		{
			int slot = -1;
			if(entity instanceof Player player)
			{
				slot = player.getInventory().findSlotMatchingItem(stack);
			}
			MENetwork.sendToAll(new MEnchantmentCapabilityRemovePacket(entity.getUUID(), enchantment, slot));
		}
	}
	
	public void sendUpdatePacket(Entity entity, ItemStack stack, EnchantmentData data)
	{
		if(!entity.level.isClientSide)
		{
			int slot = -1;
			if(entity instanceof Player player)
			{
				slot = player.getInventory().findSlotMatchingItem(stack);
			}
			MENetwork.sendToAll(new MEnchantmentCapabilityUpdatePacket(entity.getUUID(), data, slot));
		}
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		return MENCHANTMENTS.orEmpty(cap, LazyOptional.of(() -> this));
	}
}
