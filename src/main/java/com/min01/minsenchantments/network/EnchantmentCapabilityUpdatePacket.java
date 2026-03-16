package com.min01.minsenchantments.network;

import java.util.function.Supplier;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;
import com.min01.minsenchantments.event.ClientEventHandlerForge;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentCapabilityUpdatePacket 
{
	private final int entityId;
	private final String enchant;
	private final CompoundTag tag;
	
	public EnchantmentCapabilityUpdatePacket(int entityId, String name, CompoundTag tag) 
	{
		this.entityId = entityId;
		this.enchant = name;
		this.tag = tag;
	}

	public static EnchantmentCapabilityUpdatePacket read(FriendlyByteBuf buf)
	{
		return new EnchantmentCapabilityUpdatePacket(buf.readInt(), buf.readUtf(), buf.readNbt());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeUtf(this.enchant);
		buf.writeNbt(this.tag);
	}
	
	public static boolean handle(EnchantmentCapabilityUpdatePacket message, Supplier<NetworkEvent.Context> ctx) 
	{
		ctx.get().enqueueWork(() ->
		{
			Entity entity = ClientEventHandlerForge.MC.level.getEntity(message.entityId);
			EnchantmentData data = EnchantmentData.read(message.tag);
			Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.parse(message.enchant));
			entity.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t -> 
			{
				t.setEnchantmentData(enchantment, data);
			});
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}
