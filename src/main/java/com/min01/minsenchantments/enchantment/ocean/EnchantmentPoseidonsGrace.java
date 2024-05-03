package com.min01.minsenchantments.enchantment.ocean;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.min01.minsenchantments.util.EnchantmentUtil;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.TridentLoyaltyEnchantment;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EnchantmentPoseidonsGrace extends AbstractOceanEnchantment implements IProjectileEnchantment
{
	public EnchantmentPoseidonsGrace()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.TRIDENT, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.poseidonsGraceMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.poseidonsGraceMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.poseidonsGraceMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public boolean checkCompatibility(Enchantment p_44590_)
	{
		return p_44590_ instanceof TridentLoyaltyEnchantment || p_44590_ instanceof EnchantmentSharpWaves ? false : super.checkCompatibility(p_44590_);
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
					projectile.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
					{
						if(t.hasEnchantment(this))
						{
							EnchantmentData data = t.getEnchantmentData(this);
							CompoundTag tag = data.getData();
							if(tag.contains(EnchantmentTags.POSEIDONS_GRACE_SUMMONED))
							{
								living.invulnerableTime = 0;
							}
							
							if(!tag.contains(EnchantmentTags.POSEIDONS_GRACE_SUMMONED))
							{
								int level = data.getEnchantLevel();
								for(int i = 0; i < level * 2; i++)
								{
									double spawnRange = level * 5;
				                    double x = (double)owner.getX() + (projectile.level().getRandom().nextDouble() - projectile.level().getRandom().nextDouble()) * (double)spawnRange + 0.5D;
				                    double z = (double)owner.getZ() + (projectile.level().getRandom().nextDouble() - projectile.level().getRandom().nextDouble()) * (double)spawnRange + 0.5D;
				                    
				                    if(projectile.level().getFluidState(BlockPos.containing(x, living.getY() - 1, z)).is(Fluids.WATER) || projectile.level().getFluidState(BlockPos.containing(x, living.getY() - 1, z)).is(Fluids.FLOWING_WATER))
				                    {
				                    	Projectile summonedTrident = (Projectile) projectile.getType().create(projectile.level());
										summonedTrident.setOwner(projectile.getOwner());
										summonedTrident.setPos(x, living.getY() - 1, z);
										summonedTrident.setDeltaMovement(0, 0.35, 0);
										summonedTrident.setNoGravity(true);
										if(summonedTrident instanceof AbstractArrow arrow)
										{
											arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
										}
										if(summonedTrident instanceof ThrownTrident trident)
										{
											ObfuscationReflectionHelper.setPrivateValue(ThrownTrident.class, trident, ItemStack.of(tag.getCompound(EnchantmentTags.POSEIDONS_GRACE_ITEM)), "f_37555_");
										}
										projectile.level().addFreshEntity(summonedTrident);
										summonedTrident.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t2 -> 
										{
											CompoundTag tag2 = new CompoundTag();
											tag2.putBoolean(EnchantmentTags.POSEIDONS_GRACE_SUMMONED, true);
											tag2.putUUID(EnchantmentTags.POSEIDONS_GRACE_TARGET, living.getUUID());
											t2.setEnchantmentData(this, new EnchantmentData(level, tag2));
										});
				                    }
								}
							}
							else
							{
								projectile.discard();
							}
						}
					});
				}
			}
		}
		return super.onLivingDamage(living, damageSource, amount);
	}
	
	@Override
	public void onEntityTick(Entity entity) 
	{
		if(entity instanceof Projectile trident)
		{
			trident.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
			{
				if(t.hasEnchantment(this))
				{
					EnchantmentData data = t.getEnchantmentData(this);
					CompoundTag tag = data.getData();
					if(tag.contains(EnchantmentTags.POSEIDONS_GRACE_SUMMONED))
					{
						if(trident.tickCount >= 20)
						{
							if(trident.level() instanceof ServerLevel level)
							{
								Entity target = level.getEntity(tag.getUUID(EnchantmentTags.POSEIDONS_GRACE_TARGET));
								if(target != null)
								{
									trident.setDeltaMovement(EnchantmentUtil.fromToVector(trident.position(), target.position().add(0, target.getEyeHeight(), 0), EnchantmentConfig.poseidonsGraceTridentSpeed.get()));
								}
								else
								{
									trident.discard();
								}
							}
						}
					}
				}
			});
		}
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
		tag.put(EnchantmentTags.POSEIDONS_GRACE_ITEM, item.save(new CompoundTag()));
		return tag;
	}
}
