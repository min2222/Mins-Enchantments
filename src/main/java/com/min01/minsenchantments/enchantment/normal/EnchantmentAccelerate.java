package com.min01.minsenchantments.enchantment.normal;

import java.util.Map.Entry;

import com.google.common.collect.Multimap;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.TickEvent.Phase;

public class EnchantmentAccelerate extends AbstractMinsEnchantment
{
	public EnchantmentAccelerate()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.accelerateMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.accelerateMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.accelerateMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingAttack(LivingEntity entity, DamageSource damageSource, float amount) 
	{
		Entity source = damageSource.getEntity();
		if(source != null && source instanceof LivingEntity attacker)
		{
			int level = attacker.getMainHandItem().getEnchantmentLevel(this);
			if(level > 0)
			{
				ItemStack stack = attacker.getMainHandItem();
				if(stack.getTag().contains(EnchantmentTags.ACCELERATE))
				{
					float speed = stack.getTag().getFloat(EnchantmentTags.ACCELERATE);
					float original = stack.getTag().getFloat(EnchantmentTags.ACCELERATE_ORIGINAL);
					if(original + speed < original + (level * EnchantmentConfig.accelerateMaxSpeedPerLevel.get()))
					{
						stack.getTag().putFloat(EnchantmentTags.ACCELERATE, speed + (level * EnchantmentConfig.accelerateSpeedPerLevel.get()));
					}
				}
			}
		}
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player)
	{
		int level = player.getMainHandItem().getEnchantmentLevel(this);
		if(level > 0)
		{
			if(player.getMainHandItem().getOrCreateTag().contains(EnchantmentTags.ACCELERATE))
			{
				float speed = player.getMainHandItem().getOrCreateTag().getFloat(EnchantmentTags.ACCELERATE);
				if(speed + player.getMainHandItem().getOrCreateTag().getFloat(EnchantmentTags.ACCELERATE_ORIGINAL) > player.getMainHandItem().getOrCreateTag().getFloat(EnchantmentTags.ACCELERATE_ORIGINAL))
				{
					player.getMainHandItem().getOrCreateTag().putFloat(EnchantmentTags.ACCELERATE, speed - 0.002F);
				}
			}
			else if(!player.getMainHandItem().getOrCreateTag().contains(EnchantmentTags.ACCELERATE_ORIGINAL))
			{
				player.getMainHandItem().getOrCreateTag().putFloat(EnchantmentTags.ACCELERATE, 0);
				Multimap<Attribute, AttributeModifier> map = player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
				for(Entry<Attribute, AttributeModifier> entry : map.entries())
				{
					if(entry.getKey() == Attributes.ATTACK_SPEED)
					{
						player.getMainHandItem().getOrCreateTag().putFloat(EnchantmentTags.ACCELERATE_ORIGINAL, (float) (entry.getValue().getAmount()));
					}
				}
			}
		}
	}
}
