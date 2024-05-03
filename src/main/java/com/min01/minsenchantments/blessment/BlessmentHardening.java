package com.min01.minsenchantments.blessment;

import java.util.Map.Entry;

import com.google.common.collect.Multimap;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent.Phase;

public class BlessmentHardening extends AbstractBlessment
{
	public BlessmentHardening()
	{
		super(EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.hardeningMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.hardeningMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.hardeningMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity living, DamageSource source, float amount) 
	{
		if(source.getEntity() != null)
		{
			Iterable<ItemStack> armors = living.getArmorSlots();
			armors.forEach((stack) ->
			{
				if(stack.getEnchantmentLevel(CustomEnchantments.HARDENING.get()) > 0)
				{
					int level = stack.getEnchantmentLevel(CustomEnchantments.HARDENING.get());
					if(stack.getTag().contains(EnchantmentTags.HARDENING))
					{
						float armor = stack.getTag().getFloat(EnchantmentTags.HARDENING);
						float original = stack.getTag().getFloat(EnchantmentTags.HARDENING_ORIGINAL);
						if(original + armor < original + (level * EnchantmentConfig.hardeningMaxArmorPerLevel.get()))
						{
							stack.getTag().putFloat(EnchantmentTags.HARDENING, armor + (level * EnchantmentConfig.hardeningArmorPerLevel.get()));
						}
					}
				}
			});
		}
		return super.onLivingDamage(living, source, amount);
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player) 
	{
		if(EnchantmentHelper.getEnchantmentLevel(this, player) > 0)
		{
			Iterable<ItemStack> armors = player.getArmorSlots();
			armors.forEach((stack) ->
			{
				if(stack.getOrCreateTag().contains(EnchantmentTags.HARDENING))
				{
					float armor = stack.getOrCreateTag().getFloat(EnchantmentTags.HARDENING);
					if(armor + stack.getOrCreateTag().getFloat(EnchantmentTags.HARDENING_ORIGINAL) > stack.getOrCreateTag().getFloat(EnchantmentTags.HARDENING_ORIGINAL))
					{
						stack.getOrCreateTag().putFloat(EnchantmentTags.HARDENING, armor - 0.002F);
					}
				}
				else if(!stack.getOrCreateTag().contains(EnchantmentTags.HARDENING_ORIGINAL))
				{
					stack.getOrCreateTag().putFloat(EnchantmentTags.HARDENING, 0);
					Multimap<Attribute, AttributeModifier> map = stack.getAttributeModifiers(stack.getEquipmentSlot());
					for(Entry<Attribute, AttributeModifier> entry : map.entries())
					{
						if(entry.getKey() == Attributes.ARMOR)
						{
							player.getMainHandItem().getOrCreateTag().putFloat(EnchantmentTags.HARDENING_ORIGINAL, (float) (entry.getValue().getAmount()));
						}
					}
				}
			});
		}
	}
}
