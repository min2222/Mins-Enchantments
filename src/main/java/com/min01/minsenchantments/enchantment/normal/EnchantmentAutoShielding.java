package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.min01.minsenchantments.util.EnchantmentUtil;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class EnchantmentAutoShielding extends AbstractMinsEnchantment
{
	public EnchantmentAutoShielding()
	{
		super(Rarity.VERY_RARE, CustomEnchantments.SHIELD, new EquipmentSlot[] {EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.autoShieldingMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.autoShieldingMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.autoShieldingMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living)
	{
		living.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
		{
			ItemStack stack = living.getItemBySlot(EquipmentSlot.OFFHAND);
			int level = stack.getEnchantmentLevel(this);
			if(level > 0)
			{
				if(t.hasEnchantment(this))
				{
					EnchantmentData data = t.getEnchantmentData(this);
					CompoundTag tag = data.getData();
					if(stack.getItem() instanceof ShieldItem && tag.getBoolean(EnchantmentTags.IS_AUTO_SHIELDING))
					{
						int shielding = tag.getInt(EnchantmentTags.AUTO_SHIELDING);
						if(shielding > 0)
						{
							tag.putInt(EnchantmentTags.AUTO_SHIELDING, shielding - 1);
							living.startUsingItem(InteractionHand.OFF_HAND);
						}
						else
						{
							living.stopUsingItem();
							tag.putBoolean(EnchantmentTags.IS_AUTO_SHIELDING, false);
						}
					}
					t.setEnchantmentData(this, new EnchantmentData(data.getEnchantLevel(), tag));
				}
				else
				{
					t.setEnchantmentData(this, new EnchantmentData(level, new CompoundTag()));
				}
			}
		});
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity living, DamageSource source, float amount)
	{
		if(living.getCapability(EnchantmentCapabilities.ENCHANTMENT).isPresent())
		{
			IEnchantmentCapability t = living.getCapability(EnchantmentCapabilities.ENCHANTMENT).orElse(null);
			if(t.hasEnchantment(this))
			{
				ItemStack offHandStack = living.getOffhandItem();
				int level = offHandStack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get());
				if(offHandStack.getItem() instanceof ShieldItem && level > 0)
				{
					if(EnchantmentUtil.isDamageSourceBlocked(living, source))
					{
						if(Math.random() <= (level * EnchantmentConfig.autoShieldingChancePerLevel.get()) / 100)
						{
							EnchantmentData data = t.getEnchantmentData(this);
							CompoundTag tag = data.getData();
							int shielding = tag.getInt(EnchantmentTags.AUTO_SHIELDING);
							if(shielding <= 0 && !tag.getBoolean(EnchantmentTags.IS_AUTO_SHIELDING))
							{
								float am = amount;
								if(am > 0.0F)
								{
									net.minecraftforge.event.entity.living.ShieldBlockEvent ev = net.minecraftforge.common.ForgeHooks.onShieldBlock(living, source, amount);
									if(!ev.isCanceled())
									{
										if(ev.shieldTakesDamage())
										{
											living.hurtCurrentlyUsedShield(amount);
										}
										am -= ev.getBlockedDamage();
										if (!source.is(DamageTypeTags.IS_PROJECTILE))
										{
											Entity entity = source.getDirectEntity();
											if (entity instanceof LivingEntity) 
											{
												LivingEntity livingentity = (LivingEntity)entity;
												living.blockUsingShield(livingentity);
											}
										}
										
										if(amount <= 0)
										{
											living.level().broadcastEntityEvent(living, (byte)29);
										}
										tag.putInt(EnchantmentTags.AUTO_SHIELDING, 10);
										tag.putBoolean(EnchantmentTags.IS_AUTO_SHIELDING, true);
										t.setEnchantmentData(this, new EnchantmentData(level, tag));
										return Pair.of(true, am);
									}
								}
							}
						}
					}
				}
			}
		}
		return super.onLivingDamage(living, source, amount);
	}
}
