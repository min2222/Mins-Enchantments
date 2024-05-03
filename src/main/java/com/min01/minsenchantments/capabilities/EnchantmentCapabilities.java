package com.min01.minsenchantments.capabilities;

import javax.annotation.Nonnull;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class EnchantmentCapabilities
{
	public static final Capability<IEnchantmentCapability> ENCHANTMENT = CapabilityManager.get(new CapabilityToken<>() {});
	
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e)
	{
		e.addCapability(IEnchantmentCapability.ID, new ICapabilitySerializable<CompoundTag>() 
		{
			LazyOptional<IEnchantmentCapability> inst = LazyOptional.of(() -> 
			{
				EnchantmentCapabilityHandler i = new EnchantmentCapabilityHandler();
				i.setEntity(e.getObject());
				return i;
			});

			@Nonnull
			@Override
			public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) 
			{
				return ENCHANTMENT.orEmpty(capability, inst.cast());
			}

			@Override
			public CompoundTag serializeNBT() 
			{
				return inst.orElseThrow(NullPointerException::new).serializeNBT();
			}

			@Override
			public void deserializeNBT(CompoundTag nbt)
			{
				inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
			}
		});
	}
}
