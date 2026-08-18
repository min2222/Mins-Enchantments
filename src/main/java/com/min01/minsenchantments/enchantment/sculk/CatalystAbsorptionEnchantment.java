package com.min01.minsenchantments.enchantment.sculk;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;

public class CatalystAbsorptionEnchantment extends MEnchantment
{
	public CatalystAbsorptionEnchantment()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.SCULK);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 15;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public void onLivingExperienceDrop(LivingExperienceDropEvent event)
	{
		Player player = event.getAttackingPlayer();
		if(player != null)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, player);
			if(level > 0)
			{
	            RandomSource random = player.getRandom();
				int original = event.getOriginalExperience();
				int percent = (int) MEUtil.percent(original, level * MEConfig.catalystAbsorptionPercentPerLevel.get().floatValue());
	            int i = MEUtil.repairPlayerItems(player, original + percent, original + percent);
	            if(i > 0)
	            {
		            player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.35F + 0.9F);
	            	player.giveExperiencePoints(i);
	            }
				event.setCanceled(true);
			}
		}
	}
}
