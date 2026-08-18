package com.min01.minsenchantments.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class MEnchantmentCapabilityUpdatePacket 
{
	private final UUID entityUUID;
	private final EnchantmentData data;
	private final int slot;
	
	public MEnchantmentCapabilityUpdatePacket(UUID entityUUID, EnchantmentData data, int slot) 
	{
		this.entityUUID = entityUUID;
		this.data = data;
		this.slot = slot;
	}

	public static MEnchantmentCapabilityUpdatePacket read(FriendlyByteBuf buf)
	{
		return new MEnchantmentCapabilityUpdatePacket(buf.readUUID(), EnchantmentData.read(buf), buf.readInt());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		this.data.write(buf);
		buf.writeInt(this.slot);
	}
	
	public static boolean handle(MEnchantmentCapabilityUpdatePacket message, Supplier<NetworkEvent.Context> ctx) 
	{
		ctx.get().enqueueWork(() ->
		{
			MEUtil.getClientLevel(t -> 
			{
				Entity entity = MEUtil.getEntityByUUID(t, message.entityUUID);
				ItemStack stack = ItemStack.EMPTY;
				if(entity instanceof Player player)
				{
					stack = player.getInventory().getItem(message.slot);
				}
				MEUtil.addEnchantmentData(entity, stack, message.data);
			});
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}
