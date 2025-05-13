package com.min01.minsenchantments.network;

import java.util.function.Supplier;

import com.min01.minsenchantments.api.IMinsEnchantment;
import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

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
				CustomEnchantments.LIST.forEach(t -> 
				{
					ServerPlayer player = ctx.get().getSender();
					if(player != null)
					{
						if(t instanceof IMinsEnchantment enchantment)
						{
							enchantment.onPlayerLeftClickEmpty(player, player.getMainHandItem(), InteractionHand.MAIN_HAND, player.blockPosition());
						}
						
						if(t instanceof IProjectileEnchantment enchantment)
						{
							enchantment.onLeftClickEmpty(player, player.getMainHandItem(), InteractionHand.MAIN_HAND, player.blockPosition());
						}
					}
				});
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
