package com.min01.minsenchantments.api;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public interface IProjectileEnchantment
{
	default void onRightClick(Player player, ItemStack stack, InteractionHand hand, BlockPos clickPos, @Nullable Direction clickDir)
	{
		player.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
		{
			int level = stack.getEnchantmentLevel(this.self());
			if(level > 0)
			{
				t.setEnchantmentData(this.self(), new EnchantmentData(level, this.getData(player, stack, 0)));
			}
		});
	}
	
	default void onStopUse(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
		{
			int level = item.getEnchantmentLevel(this.self());
			if(level > 0)
			{
				t.setEnchantmentData(this.self(), new EnchantmentData(level, this.getData(entity, item, duration)));
			}
		});
	}
	
	default void onJoinLevel(Entity entity, Level level)
	{
		if(entity instanceof Projectile projectile)
		{
			Entity owner = projectile.getOwner();
			if(owner != null)
			{
				owner.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
				{
					if(t.hasEnchantment(this.self()))
					{
						projectile.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t2 -> 
						{
							EnchantmentData data = t.getEnchantmentData(this.self());
							t2.setEnchantmentData(this.self(), t.getEnchantmentData(this.self()));
							t.removeEnchantment(this.self());
							this.onJoin(projectile, owner, data, t);
						});
					}
				});
			}
		}
	}
	
	default void onProjImpact(Projectile projectile, HitResult ray)
	{
		Entity owner = projectile.getOwner();
		if(owner != null)
		{
			projectile.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
			{
				if(t.hasEnchantment(this.self()))
				{
					EnchantmentData data = t.getEnchantmentData(this.self());
					this.onImpact(projectile, owner, ray, data, t);
				}
			});
		}
	}
	
	default void onJoin(Projectile projectile, Entity owner, EnchantmentData data, IEnchantmentCapability cap)
	{
		
	}
	
	default void onImpact(Projectile projectile, Entity owner, HitResult ray, EnchantmentData data, IEnchantmentCapability cap)
	{
		
	}
	
	public Enchantment self();
	
	
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration);
}
