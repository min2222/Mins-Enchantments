package com.min01.minsenchantments.network;


import com.min01.minsenchantments.MinsEnchantments;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MENetwork 
{
	public static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, MinsEnchantments.MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	public static void registerMessages()
	{
		int id = 0;
		CHANNEL.registerMessage(id++, MEnchantmentCapabilityUpdatePacket.class, MEnchantmentCapabilityUpdatePacket::write, MEnchantmentCapabilityUpdatePacket::read, MEnchantmentCapabilityUpdatePacket::handle);
		CHANNEL.registerMessage(id++, MEnchantmentCapabilityRemovePacket.class, MEnchantmentCapabilityRemovePacket::write, MEnchantmentCapabilityRemovePacket::read, MEnchantmentCapabilityRemovePacket::handle);
	}
	
    public static <MSG> void sendToServer(MSG message) 
    {
    	CHANNEL.sendToServer(message);
    }
    
    public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) 
    {
        CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
    
    public static <MSG> void sendToAll(MSG message)
    {
    	for(ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) 
    	{
    		CHANNEL.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    	}
    }
}
