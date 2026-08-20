package com.min01.minsenchantments.api;

import com.min01.minsenchantments.api.event.EntityTeleportToEvent;
import com.min01.minsenchantments.api.event.ItemAttributeModifyEvent;
import com.min01.minsenchantments.api.event.ProjectileHitEvent;
import com.min01.tickrateapi.api.event.EntityTickEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.EnderManAngerEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public interface IMEnchantment
{
	default boolean requiredSummonContext()
	{
		return false;
	}
	
	default void onShieldBlock(ShieldBlockEvent event)
	{
		
	}
	
	default void onEntityTick(EntityTickEvent event)
	{
		
	}
	
	default void onLivingTick(LivingTickEvent event)
	{
		
	}
	
	//this event is only fired on server side;
	default void onLivingHurt(LivingHurtEvent event)
	{
		
	}
	
	default void onProjectileHit(ProjectileHitEvent event)
	{
		
	}
	
	default void onLivingHeal(LivingHealEvent event)
	{
		
	}
	
	default void onCriticalHit(CriticalHitEvent event)
	{
		
	}
	
	default void onItemAttributeModify(ItemAttributeModifyEvent event)
	{
		
	}
	
	default void onItemInventoryTick(LivingEntity entity, ItemStack stack)
	{
		
	}
	
	default void onEnderManAnger(EnderManAngerEvent event)
	{
		
	}
	
	default void onEntityTeleportTo(EntityTeleportToEvent event)
	{
		
	}
	
	default void onLivingExperienceDrop(LivingExperienceDropEvent event)
	{
		
	}
	
	default void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		
	}
	
	default void onLivingKnockBack(LivingKnockBackEvent event)
	{
		
	}
	
	default void onEntityJoinLevel(EntityJoinLevelEvent event)
	{
		
	}
}