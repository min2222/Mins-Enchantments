package com.min01.minsenchantments.event;

import com.google.common.collect.Iterables;
import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.api.event.EntityTeleportToEvent;
import com.min01.minsenchantments.api.event.ItemAttributeModifyEvent;
import com.min01.minsenchantments.api.event.ProjectileHitEvent;
import com.min01.minsenchantments.capabilities.IMEnchantmentCapability;
import com.min01.minsenchantments.capabilities.MEnchantmentCapabilityImpl;
import com.min01.minsenchantments.util.MEUtil;
import com.min01.tickrateapi.api.event.EntityTickEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandlerForge 
{
	@SubscribeEvent
	public static void onShieldBlock(ShieldBlockEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onShieldBlock(event));
	}

	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent event)
	{
		Entity entity = event.getEntity();
		MEUtil.getMEnchantment(t -> t.onEntityTick(event));
		Iterable<ItemStack> iterable = entity.getAllSlots();
		if(entity instanceof Player player)
		{
			iterable = Iterables.concat(iterable, player.getInventory().items);
		}
		for(ItemStack stack : iterable)
		{
			if(entity instanceof LivingEntity living)
			{
				MEUtil.getMEnchantment(t -> t.onItemInventoryTick(living, stack));
			}
			IMEnchantmentCapability cap = stack.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
			cap.tick(entity, stack);
		}
	}

	@SubscribeEvent
	public static void onLivingTick(LivingTickEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onLivingTick(event));
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onLivingHurt(event));
	}

	@SubscribeEvent
	public static void onProjectileHit(ProjectileHitEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onProjectileHit(event));
	}
	
	@SubscribeEvent
	public static void onLivingHeal(LivingHealEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onLivingHeal(event));
	}
	
	@SubscribeEvent
	public static void onCriticalHit(CriticalHitEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onCriticalHit(event));
	}
	
	@SubscribeEvent
	public static void onItemAttributeModify(ItemAttributeModifyEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onItemAttributeModify(event));
	}
	
	@SubscribeEvent
	public static void onEnderManAnger(EnderManAngerEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onEnderManAnger(event));
	}
	
	@SubscribeEvent
	public static void onEntityTeleportTo(EntityTeleportToEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onEntityTeleportTo(event));
	}
	
	@SubscribeEvent
	public static void onLivingExperienceDrop(LivingExperienceDropEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onLivingExperienceDrop(event));
	}
	
	@SubscribeEvent
	public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		MEUtil.getMEnchantment(t -> t.onPlayerBreakSpeed(event));
	}
	
	@SubscribeEvent
	public static void onLivingKnockBack(LivingKnockBackEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onLivingKnockBack(event));
	}
	
	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event)
	{
		MEUtil.getMEnchantment(t -> t.onEntityJoinLevel(event));
	}
}