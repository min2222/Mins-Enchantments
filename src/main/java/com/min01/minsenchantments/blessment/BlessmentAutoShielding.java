package com.min01.minsenchantments.blessment;

import java.lang.reflect.Method;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityImpl.EnchantmentData;
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
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class BlessmentAutoShielding extends AbstractBlessment
{
	public BlessmentAutoShielding()
	{
		super(CustomEnchantments.SHIELD, new EquipmentSlot[] {EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.autoShieldingMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.autoShieldingMinCost.get() + (pLevel - 1) * EnchantmentConfig.autoShieldingMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living)
	{
		living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).ifPresent(t -> 
		{
			ItemStack stack = living.getOffhandItem();
			InteractionHand hand = InteractionHand.OFF_HAND;
			if(stack.isEmpty())
			{
				stack = living.getMainHandItem();
				hand = InteractionHand.MAIN_HAND;
			}
			int level = stack.getEnchantmentLevel(this);
			if(level > 0 && stack.canPerformAction(ToolActions.SHIELD_BLOCK))
			{
				if(t.hasEnchantment(this))
				{
					EnchantmentData data = t.getEnchantmentData(this);
					CompoundTag tag = data.getData();
					if(tag.getBoolean(EnchantmentTags.IS_AUTO_SHIELDING))
					{
						int shielding = tag.getInt(EnchantmentTags.AUTO_SHIELDING);
						if(shielding > 0)
						{
							tag.putInt(EnchantmentTags.AUTO_SHIELDING, shielding - 1);
							living.startUsingItem(hand);
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
		if(living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).isPresent())
		{
			IEnchantmentCapability t = living.getCapability(EnchantmentCapabilityImpl.ENCHANTMENT).orElse(new EnchantmentCapabilityImpl(living));
			if(t.hasEnchantment(this))
			{
				ItemStack offHandStack = living.getOffhandItem();
				if(offHandStack.isEmpty())
				{
					offHandStack = living.getMainHandItem();
				}
				int level = offHandStack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get());
				if(offHandStack.canPerformAction(ToolActions.SHIELD_BLOCK) && level > 0)
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
											Method m = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "m_7909_", float.class);
											m.setAccessible(true);
											try 
											{
												m.invoke(living, amount);
											}
											catch (Exception e) 
											{
												e.printStackTrace();
											}
										}
										am -= ev.getBlockedDamage();
										if(!source.is(DamageTypeTags.IS_PROJECTILE))
										{
											Entity entity = source.getDirectEntity();
											if(entity instanceof LivingEntity livingEntity) 
											{
												Method m = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "m_6728_", LivingEntity.class);
												try 
												{
													m.invoke(living, livingEntity);
												}
												catch(Exception e) 
												{
													e.printStackTrace();
												}
											}
										}
										tag.putInt(EnchantmentTags.AUTO_SHIELDING, 10);
										tag.putBoolean(EnchantmentTags.IS_AUTO_SHIELDING, true);
										t.setEnchantmentData(this, new EnchantmentData(level, tag));
										if(am <= 0)
										{
											living.level.broadcastEntityEvent(living, (byte)29);
											return Pair.of(true, am);
										}
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
	
	@Override
	public boolean onLivingAttack(LivingEntity living, DamageSource source, float amount)
	{
		return this.onLivingDamage(living, source, amount).first();
	}
}
