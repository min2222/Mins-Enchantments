package com.min01.minsenchantments.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class MEnchantmentCapabilityRemovePacket 
{
	private final UUID entityUUID;
	private final Enchantment enchantment;
	private final int slot;
	
	public MEnchantmentCapabilityRemovePacket(UUID entityUUID, Enchantment enchantment, int slot) 
	{
		this.entityUUID = entityUUID;
		this.enchantment = enchantment;
		this.slot = slot;
	}

	public static MEnchantmentCapabilityRemovePacket read(FriendlyByteBuf buf)
	{
		return new MEnchantmentCapabilityRemovePacket(buf.readUUID(), ForgeRegistries.ENCHANTMENTS.getValue(buf.readResourceLocation()), buf.readInt());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		buf.writeResourceLocation(ForgeRegistries.ENCHANTMENTS.getKey(this.enchantment));
		buf.writeInt(this.slot);
	}
	
	public static boolean handle(MEnchantmentCapabilityRemovePacket message, Supplier<NetworkEvent.Context> ctx) 
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
				MEUtil.removeEnchantmentData(entity, stack, message.enchantment);
			});
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}
