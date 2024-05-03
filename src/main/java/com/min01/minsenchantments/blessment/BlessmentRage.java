package com.min01.minsenchantments.blessment;

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

public class BlessmentRage extends AbstractBlessment
{
	public BlessmentRage()
	{
		super(EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.rageMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.rageMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.rageMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player)
	{
		int level = player.getMainHandItem().getEnchantmentLevel(this);
		if(level > 0)
		{
			if(player.getMainHandItem().getOrCreateTag().contains(EnchantmentTags.RAGE))
			{
				float damage = player.getMainHandItem().getOrCreateTag().getFloat(EnchantmentTags.RAGE);
				if(damage + player.getMainHandItem().getOrCreateTag().getFloat(EnchantmentTags.RAGE_ORIGINAL) > player.getMainHandItem().getOrCreateTag().getFloat(EnchantmentTags.RAGE_ORIGINAL))
				{
					player.getMainHandItem().getOrCreateTag().putFloat(EnchantmentTags.RAGE, damage - 0.002F);
				}
			}
			else if(!player.getMainHandItem().getOrCreateTag().contains(EnchantmentTags.RAGE_ORIGINAL))
			{
				player.getMainHandItem().getOrCreateTag().putFloat(EnchantmentTags.RAGE, 0);
				Multimap<Attribute, AttributeModifier> map = player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
				for(Entry<Attribute, AttributeModifier> entry : map.entries())
				{
					if(entry.getKey() == Attributes.ATTACK_DAMAGE)
					{
						player.getMainHandItem().getOrCreateTag().putFloat(EnchantmentTags.RAGE_ORIGINAL, (float) (entry.getValue().getAmount()));
					}
				}
			}
		}
	}
	
	@Override
	public void onLivingAttack(LivingEntity entity, DamageSource damageSource, float amount)
	{
		Entity source = damageSource.getEntity();
		
		if(source != null && source instanceof LivingEntity attacker)
		{
			ItemStack stack = attacker.getMainHandItem();
			int level = stack.getEnchantmentLevel(this);
			if(level > 0)
			{
				if(stack.getTag().contains(EnchantmentTags.RAGE))
				{
					float damage = stack.getTag().getFloat(EnchantmentTags.RAGE);
					float original = stack.getTag().getFloat(EnchantmentTags.RAGE_ORIGINAL);
					if(original + damage < original + (level * EnchantmentConfig.rageMaxDamagePerLevel.get()))
					{
						stack.getTag().putFloat(EnchantmentTags.RAGE, damage + (level * EnchantmentConfig.rageDamagePerLevel.get()));
					}
				}
			}
		}
	}
}
