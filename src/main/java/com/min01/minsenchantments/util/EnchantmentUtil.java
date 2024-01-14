package com.min01.minsenchantments.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.min01.minsenchantments.misc.EntityTimer;
import com.min01.minsenchantments.network.EnchantmentNetwork;
import com.min01.minsenchantments.network.EntityTimerSyncPacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.server.ServerLifecycleHooks;

public class EnchantmentUtil
{
	public static final Map<UUID, EntityTimer> TIMER_MAP = new HashMap<>();
	public static final Map<UUID, EntityTimer> CLIENT_TIMER_MAP = new HashMap<>();
	public static final EntityTimer ENTITY_TIMER = new EntityTimer(20.0F, 0L);

	public static final String REPLAYMOD = "replaymod";
	public static final String MINS_UNIVERSE = "minsuniverse";
	
	public static boolean hasMU()
	{
		return EnchantmentUtil.isModLoaded(MINS_UNIVERSE);
	}
	
	public static boolean isNotReplay()
	{
		return true;
	}
	
    public static void setTickrate(Entity entity, float tickrate)
    {
    	if(!hasMU())
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
    }
    
    public static void resetTickrate(Entity entity)
    {
    	if(!hasMU())
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
    }
	
	public static boolean isModLoaded(String modid)
	{
		return ModList.get().isLoaded(modid);
	}
	
	public static boolean isDamageSourceBlocked(LivingEntity living, DamageSource p_21276_) 
	{
		Entity entity = p_21276_.getDirectEntity();
		boolean flag = false;
		if (entity instanceof AbstractArrow abstractarrow)
		{
			if (abstractarrow.getPierceLevel() > 0) 
			{
				flag = true;
			}
		}

		if (!p_21276_.is(DamageTypeTags.BYPASSES_SHIELD) && !flag)
		{
			Vec3 vec32 = p_21276_.getSourcePosition();
			if (vec32 != null) 
			{
				Vec3 vec3 = living.getViewVector(1.0F);
				Vec3 vec31 = vec32.vectorTo(living.position()).normalize();
				vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
				if (vec31.dot(vec3) < 0.0D)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static Vec3 fromToVector(Vec3 from, Vec3 to, float speed)
	{
		Vec3 motion = new Vec3(to.x - from.x, to.y - from.y, to.z - from.z).scale(speed).normalize();
		return motion;
	}
	
	public static void speedupEntity(LivingEntity entity, float factor)
	{
		if(entity.zza > 0)
		{
			entity.setDeltaMovement(entity.getDeltaMovement().multiply(factor, 1, factor));
		}
	}
}
