package com.min01.minsenchantments.network;

import java.util.function.Supplier;

import com.min01.minsenchantments.api.IMinsEnchantment;
import com.min01.minsenchantments.api.IProjectileEnchantment;

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

	public static PlayerLeftClickPacket read(FriendlyByteBuf buf)
	{
		return new PlayerLeftClickPacket();
	}

	public void write(FriendlyByteBuf buf)
	{
		
	}
	
	public static boolean handle(PlayerLeftClickPacket message, Supplier<NetworkEvent.Context> ctx) 
	{
		ctx.get().enqueueWork(() ->
		{
			ForgeRegistries.ENCHANTMENTS.getValues().forEach(t -> 
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
