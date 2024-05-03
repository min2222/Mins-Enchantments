package com.min01.minsenchantments.enchantment.sculk;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentConcealment extends AbstractSculkEnchantment
{
	public EnchantmentConcealment()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.concealmentMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.concealmentMinCost.get();
	}
	
	@Override
	public boolean onPlaySoundAtEntity(Entity entity, Holder<SoundEvent> sound, SoundSource source, float volume, float pitch) 
	{
		if(entity instanceof LivingEntity living)
		{
			if(EnchantmentHelper.getEnchantmentLevel(this, living) > 0)
			{
				return true;
			}
		}
		
		return super.onPlaySoundAtEntity(entity, sound, source, volume, pitch);
	}
}
