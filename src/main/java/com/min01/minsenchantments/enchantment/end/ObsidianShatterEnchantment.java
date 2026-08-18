package com.min01.minsenchantments.enchantment.end;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class ObsidianShatterEnchantment extends MEnchantment
{
	public ObsidianShatterEnchantment() 
	{
		super(Rarity.RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.END);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return 5 + pLevel * 10;
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
	public void onPlayerBreakSpeed(BreakSpeed event) 
	{
		Player player = event.getEntity();
		BlockState state = event.getState();
		Block block = state.getBlock();
		float speed = event.getOriginalSpeed();
		if(block.defaultDestroyTime() >= Blocks.OBSIDIAN.defaultDestroyTime())
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, player);
			if(level > 0)
			{
				float percent = MEUtil.percent(speed, level * MEConfig.obsidianShatterPercentPerLevel.get().floatValue());
				event.setNewSpeed(speed + percent);
			}
		}
	}
}
