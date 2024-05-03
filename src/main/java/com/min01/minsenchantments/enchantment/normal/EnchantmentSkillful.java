package com.min01.minsenchantments.enchantment.normal;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantmentSkillful extends AbstractMinsEnchantment implements IProjectileEnchantment
{
	public EnchantmentSkillful()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.skillfulMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.skillfulMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.skillfulMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public void onLivingTick(LivingEntity living) 
	{
		ItemStack stack = living.getMainHandItem();
		int level = stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get());
		if(level > 0)
		{
			int damage = stack.getDamageValue();
			int count = stack.getOrCreateTag().getInt(EnchantmentTags.SKILLFUL);
			int miningCount = stack.getOrCreateTag().getInt(EnchantmentTags.SKILLFUL_MINING_COUNT);
			int projCount = stack.getOrCreateTag().getInt(EnchantmentTags.SKILLFUL_PROJECTILE_COUNT);
			
			stack.getOrCreateTag().putInt(EnchantmentTags.SKILLFUL, damage / (EnchantmentConfig.skillfulDurabilityPerLevel.get() / level));
			
			if(projCount < count)
			{
				float dmg = stack.getOrCreateTag().getFloat(EnchantmentTags.SKILLFUL_PROJECTILE);
				
				if(dmg < level * EnchantmentConfig.skillfulMaxDamagePerLevel.get())
				{
					stack.getOrCreateTag().putFloat(EnchantmentTags.SKILLFUL_PROJECTILE, dmg + (level * EnchantmentConfig.skillfulDamagePerLevel.get()));
				}
				
				if(dmg > level * EnchantmentConfig.skillfulMaxDamagePerLevel.get())
				{
					stack.getOrCreateTag().putFloat(EnchantmentTags.SKILLFUL_PROJECTILE, level * EnchantmentConfig.skillfulMaxDamagePerLevel.get());
				}
				stack.getOrCreateTag().putInt(EnchantmentTags.SKILLFUL_PROJECTILE_COUNT, projCount + 1);
			}
			
			if(miningCount < count)
			{
				float speed = stack.getOrCreateTag().getFloat(EnchantmentTags.SKILLFUL_MINING);
				if(speed < level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get())
				{
					stack.getOrCreateTag().putFloat(EnchantmentTags.SKILLFUL_MINING, speed + (level * EnchantmentConfig.skillfulSpeedPerLevel.get()));
				}
				
				if(speed > level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get())
				{
					stack.getOrCreateTag().putFloat(EnchantmentTags.SKILLFUL_MINING, level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get());
				}
				stack.getOrCreateTag().putInt(EnchantmentTags.SKILLFUL_MINING_COUNT, miningCount + 1);
			}
		}
	}
	
	@Override
	public float onBreakSpeed(Player player, BlockState state, float originalSpeed, BlockPos pos) 
	{
		ItemStack stack = player.getMainHandItem();
		if(stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get()) > 0)
		{
			float speed = stack.getOrCreateTag().getFloat(EnchantmentTags.SKILLFUL_MINING);
			return originalSpeed + speed;
		}
		
		return super.onBreakSpeed(player, state, originalSpeed, pos);
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
					float damage = t.getEnchantmentData(this).getData().getFloat(EnchantmentTags.SKILLFUL_PROJECTILE);
					return Pair.of(false, amount + damage);
				}
			}
		}
		return super.onLivingDamage(living, source, amount);
	}

	@Override
	public Enchantment self() 
	{
		return this;
	}
	
	@Override
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(EnchantmentTags.SKILLFUL_PROJECTILE, item.getOrCreateTag().getInt(EnchantmentTags.SKILLFUL_PROJECTILE));
		return tag;
	}
}
