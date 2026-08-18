package com.min01.minsenchantments.enchantment.end;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ChorusEvasionEnchantment extends MEnchantment
{
	public ChorusEvasionEnchantment() 
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}, EnchantmentType.END);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return 5 + pLevel * 15;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public void onLivingHurt(LivingHurtEvent event) 
	{
		LivingEntity entity = event.getEntity();
		int level = EnchantmentHelper.getEnchantmentLevel(this, entity);
		if(level > 0)
		{
			float chance = level * MEConfig.chorusEvasionChancePerLevel.get().floatValue();
			if(Math.random() <= chance / 100.0F)
			{
				RandomSource random = entity.getRandom();
				for(int i = 0; i < 16; ++i) 
				{
		            double x = entity.getX() + (random.nextDouble() - 0.5D) * 16.0D;
		            double y = Mth.clamp(entity.getY() + (random.nextInt(16) - 8), entity.level.getMinBuildHeight(), (entity.level.getMinBuildHeight() + entity.level.dimensionType().logicalHeight() - 1));
		            double z = entity.getZ() + (random.nextDouble() - 0.5D) * 16.0D;
		            if(entity.isPassenger())
		            {
		            	entity.stopRiding();
		            }
		            if(entity.randomTeleport(x, y, z, true)) 
		            {
						entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
						entity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
						event.setCanceled(true);
						break;
		            }
				}
			}
		}
	}
}
