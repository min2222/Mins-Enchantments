package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.Vec3;

public class BlessmentReturning extends AbstractBlessment
{
	public BlessmentReturning()
	{
		super(EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.returningMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.returningMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.returningMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingDeath(LivingEntity living, DamageSource source)
	{
		Iterable<ItemStack> armors = living.getArmorSlots();
		armors.forEach((stack) ->
		{
			if(stack.getEnchantmentLevel(CustomEnchantments.RETURNING.get()) > 0)
			{
				stack.getOrCreateTag().put(EnchantmentTags.RETURNING, NbtUtils.writeBlockPos(living.blockPosition()));
			}
		});
	}
	
	@Override
	public void onPlayerClone(Player player, Player original, boolean wasDeath) 
	{
		if(wasDeath)
		{
			Iterable<ItemStack> armors = player.getArmorSlots();
			armors.forEach((stack) ->
			{
				if(stack.getEnchantmentLevel(CustomEnchantments.RETURNING.get()) > 0)
				{
					int level = stack.getEnchantmentLevel(CustomEnchantments.RETURNING.get());
					if(stack.getOrCreateTag().contains(EnchantmentTags.RETURNING))
					{
						if(stack.getMaxDamage() - stack.getDamageValue() >=  EnchantmentConfig.returningDurabilityPerLevel.get() / level)
						{
							stack.hurtAndBreak(EnchantmentConfig.returningDurabilityPerLevel.get() / level, player, (living) -> 
							{
								
							});
							player.setPos(Vec3.atCenterOf(NbtUtils.readBlockPos(stack.getOrCreateTag().getCompound(EnchantmentTags.RETURNING))));
							player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE);
						}
					}
				}
			});
		}
	}
}
