package com.min01.minsenchantments.network;

import java.util.function.Supplier;

import com.min01.minsenchantments.api.IMinsEnchantment;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class PlayerLeftClickPacket 
{
	public PlayerLeftClickPacket() 
	{
		
	}

	public PlayerLeftClickPacket(FriendlyByteBuf buf)
	{
		
	}

	public void encode(FriendlyByteBuf buf)
	{
		
	}
	
	public static class Handler 
	{
		public static boolean onMessage(PlayerLeftClickPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				ForgeRegistries.ENCHANTMENTS.forEach(t -> 
				{
					if(t instanceof IMinsEnchantment enchantment)
					{
						ServerPlayer player = ctx.get().getSender();
						if(player != null)
						{
							enchantment.onPlayerLeftClickEmpty(player, player.getMainHandItem(), InteractionHand.MAIN_HAND, player.blockPosition());
						}
					}
				});
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
