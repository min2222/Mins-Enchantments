package com.min01.minsenchantments.api;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent.Phase;

public interface IMinsEnchantment
{
	default void onPlayerRightClickItem(Player player, ItemStack stack, InteractionHand hand, BlockPos clickPos, @Nullable Direction clickDir)
	{
		
	}
	
	default void onPlayerRightClickBlock(Player player, ItemStack stack, InteractionHand hand, BlockPos clickPos, BlockHitResult hitVec)
	{
		
	}
	
	default boolean onPlaySoundAtEntity(Entity entity, Holder<SoundEvent> sound, SoundSource source, float volume, float pitch)
	{
		return false;
	}
	
	default int onBlockBreak(BlockPos pos, BlockState state, Player player, int exp)
	{
		return exp;
	}
	
	default float onBreakSpeed(Player player, BlockState state, float originalSpeed, @Nullable BlockPos pos)
	{
		return originalSpeed;
	}
	
	default void onPlayerClone(Player _new, Player oldPlayer, boolean wasDeath)
	{
		
	}
	
	default void onLivingDeath(LivingEntity entity, DamageSource source)
	{
		
	}
	
	default int onLivingEntityStartUseItem(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		return duration;
	}
	
	default int onLivingEntityStopUseItem(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		return duration;
	}
	
	default int onLivingEntityUseItemTick(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		return duration;
	}
	
	default void onLivingAttack(LivingEntity entity, DamageSource source, float amount)
	{
		
	}
	
	default Pair<Float, Float> onLivingFall(LivingEntity entity, float distance, float damageMultiplier)
	{
		return Pair.of(distance, damageMultiplier);
	}
	
	default boolean onLivingBreath(LivingEntity entity, boolean canBreathe, int consumeAirAmount, int refillAirAmount, boolean canRefillAir)
	{
		return canBreathe;
	}
	
	default void onPlayerTick(Phase phase, Player player)
	{
		
	}
	
	default void onLivingTick(LivingEntity living)
	{
		
	}
	
	default void onEntityJoin(Entity entity, Level level)
	{
		
	}
	
	default Pair<Boolean, Float> onLivingDamage(LivingEntity entity, DamageSource source, float amount)
	{
		return Pair.of(false, amount);
	}
	
	default void onProjectileImpact(Projectile projectile, HitResult ray)
	{
		
	}
	
	default void onEntityTick(Entity entity)
	{
		
	}
}
