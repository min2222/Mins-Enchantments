package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.config.EnchantmentConfig;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentArmorCrack extends AbstractMinsEnchantment
{
	public EnchantmentArmorCrack()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.armorCrackMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.armorCrackMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.armorCrackMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity entity, DamageSource source, float amount) 
	{
		Entity attacker = source.getEntity();
		
		if(attacker instanceof LivingEntity sourceEntity)
		{
			ItemStack stack = sourceEntity.getMainHandItem();
			int level = stack.getEnchantmentLevel(this);
			if(level > 0)
			{
				double armorPoint = entity.getAttributeBaseValue(Attributes.ARMOR);
				float damage = (float) ((armorPoint * (level * EnchantmentConfig.armorCrackDamagePerLevel.get())) / 100);
				return Pair.of(false, amount + damage);
			}
		}
		return super.onLivingDamage(entity, source, amount);
	}
}
