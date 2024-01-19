package com.min01.entitytimer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.network.EnchantmentNetwork;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Bus.FORGE)
public class TimerUtil 
{
	public static final Map<UUID, EntityTimer> TIMER_MAP = new HashMap<>();
	public static final Map<UUID, EntityTimer> CLIENT_TIMER_MAP = new HashMap<>();
	public static final EntityTimer ENTITY_TIMER = new EntityTimer(20.0F, 0L);

	public static final String REPLAYMOD = "replaymod";
	public static final Map<Class<? extends Entity>, Object> ENTITY_MAP = new HashMap<>();
	
	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent event)
	{
		ENTITY_MAP.put(event.getEntity().getClass(), event.getEntity());
	}
	
	public static boolean isNotReplay()
	{
		return true;
	}
	
    public static void setTickrate(Entity entity, float tickrate)
    {
    	Level level = entity.level();
    	if(!level.isClientSide)
    	{
        	for(ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) 
        	{
        		EnchantmentNetwork.CHANNEL.sendTo(new EntityTimerSyncPacket(entity.getUUID(), tickrate, false), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        	}
        	
    		if(TIMER_MAP.containsKey(entity.getUUID()))
    		{
    			TIMER_MAP.remove(entity.getUUID());
    		}
    		
			TIMER_MAP.put(entity.getUUID(), new EntityTimer(tickrate, 0));
    	}
    }
    
    public static void resetTickrate(Entity entity)
    {
    	Level level = entity.level();
    	if(!level.isClientSide)
    	{
        	for(ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) 
        	{
        		EnchantmentNetwork.CHANNEL.sendTo(new EntityTimerSyncPacket(entity.getUUID(), 0, true), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        	}
        	
    		if(TIMER_MAP.containsKey(entity.getUUID()))
    		{
    			TIMER_MAP.remove(entity.getUUID());
    		}
    	}
    }
	
	public static boolean isModLoaded(String modid)
	{
		return ModList.get().isLoaded(modid);
	}
}
