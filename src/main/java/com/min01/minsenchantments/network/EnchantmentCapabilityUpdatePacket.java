package com.min01.minsenchantments.network;

import java.util.function.Supplier;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
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
	
	public EnchantmentCapabilityUpdatePacket(Entity entity, Enchantment enchantment, EnchantmentData data) 
	{
		this.entityId = entity.getId();
		this.enchant = ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString();
		this.tag = data.save();
	}

	public EnchantmentCapabilityUpdatePacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.enchant = buf.readUtf();
		this.tag = buf.readNbt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeUtf(this.enchant);
		buf.writeNbt(this.tag);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(EnchantmentCapabilityUpdatePacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Entity entity = ClientEventHandlerForge.MC.level.getEntity(message.entityId);
				entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
				{
					t.setEnchantmentData(ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.parse(message.enchant)), EnchantmentData.read(message.tag));
				});
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
