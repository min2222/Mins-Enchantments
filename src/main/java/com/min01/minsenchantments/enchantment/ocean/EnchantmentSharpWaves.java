package com.min01.minsenchantments.enchantment.ocean;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentSharpWaves extends AbstractOceanEnchantment implements IProjectileEnchantment
{
	public EnchantmentSharpWaves()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.TRIDENT, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.sharpWavesMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.sharpWavesMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.sharpWavesMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}

	@Override
	public Enchantment self() 
	{
		return this;
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity living, DamageSource damageSource, float amount)
	{
		if(damageSource.getDirectEntity() != null)
		{
			Entity entity = damageSource.getDirectEntity();
			
			if(entity instanceof Projectile projectile)
			{
				Entity owner = projectile.getOwner();
				if(owner != null)
				{
					if(projectile.getCapability(EnchantmentCapabilities.ENCHANTMENT).isPresent())
					{
						IEnchantmentCapability t = projectile.getCapability(EnchantmentCapabilities.ENCHANTMENT).orElseGet(null);
						if(t.hasEnchantment(this))
						{
							EnchantmentData data = t.getEnchantmentData(this);
							CompoundTag tag = data.getData();
							if(owner.isInWater())
							{
								//FIXME not working
								BlockPos pos = NbtUtils.readBlockPos(tag.getCompound(EnchantmentTags.SHARP_WAVES));
								double distance = projectile.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
								int level = data.getEnchantLevel();
								float damage = 0;
								if(distance % (EnchantmentConfig.sharpWavesDistancePerLevel.get() / level) == 0)
								{
									if(damage <= level * EnchantmentConfig.sharpWavesMaxDamagePerLevel.get())
									{
										damage += (level * EnchantmentConfig.sharpWavesDamagePerLevel.get());
									}
									if(damage > level * EnchantmentConfig.sharpWavesMaxDamagePerLevel.get())
									{
										damage = level * EnchantmentConfig.sharpWavesMaxDamagePerLevel.get();
									}
								}
								return Pair.of(false, amount + damage);
							}
						}
					}
				}
			}
		}
		return super.onLivingDamage(living, damageSource, amount);
	}

	@Override
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		CompoundTag tag = new CompoundTag();
		tag.put(EnchantmentTags.SHARP_WAVES, NbtUtils.writeBlockPos(entity.blockPosition()));
		return tag;
	}
}
