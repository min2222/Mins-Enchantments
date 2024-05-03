package com.min01.minsenchantments.enchantment.normal;

import org.jetbrains.annotations.NotNull;

import com.min01.entitytimer.TimerUtil;
import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentSnipe extends AbstractMinsEnchantment implements IProjectileEnchantment
{
	public EnchantmentSnipe()
	{
		super(Rarity.RARE, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.snipeMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.snipeMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.snipeMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public int onLivingEntityStartUseItem(LivingEntity living, @NotNull ItemStack stack, int duration) 
	{
		if(stack.getEnchantmentLevel(this) > 0)
		{
			living.getPersistentData().putInt(EnchantmentTags.SNIPE_TICK, stack.getUseDuration());
		}
		return super.onLivingEntityStartUseItem(living, stack, duration);
	}
	
	@Override
	public void onJoin(Projectile projectile, Entity owner, EnchantmentData data, IEnchantmentCapability cap)
	{
		TimerUtil.setTickrate(projectile, 20 + (data.getEnchantLevel() * EnchantmentConfig.snipeProjectileSpeedPerLevel.get()));
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity living, DamageSource source, float amount) 
	{
		Entity entity = source.getDirectEntity();
		
		if(entity instanceof Projectile projectile)
		{
			if(projectile.getCapability(EnchantmentCapabilities.ENCHANTMENT).isPresent())
			{
				IEnchantmentCapability t = projectile.getCapability(EnchantmentCapabilities.ENCHANTMENT).orElseGet(null);
				if(t.hasEnchantment(this))
				{
					int level = t.getEnchantmentData(this).getEnchantLevel();
					return Pair.of(false, amount + (level * EnchantmentConfig.snipeAdditionalDamagePerLevel.get()));
				}
			}
		}
		return super.onLivingDamage(living, source, amount);
	}
	
	@Override
	public int onLivingEntityUseItemTick(LivingEntity living, @NotNull ItemStack stack, int duration) 
	{
		if(stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get());
			int speed = (int) (level * EnchantmentConfig.snipeChargeSpeedPerLevel.get());
			int tick = living.getPersistentData().getInt(EnchantmentTags.SNIPE_TICK);
			if(living.tickCount % speed * 20 == 0)
			{
				living.getPersistentData().putInt(EnchantmentTags.SNIPE_TICK, tick - 1);
			}
			
			return tick;
		}
		return super.onLivingEntityUseItemTick(living, stack, duration);
	}

	@Override
	public Enchantment self() 
	{
		return this;
	}

	@Override
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration) 
	{
		return new CompoundTag();
	}
}
