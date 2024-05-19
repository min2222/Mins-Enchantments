package com.min01.minsenchantments.blessment;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public class BlessmentGodHand extends AbstractBlessment
{
	public static final Map<Item, ItemStack> ITEM_MAP = new HashMap<>();
	
	public BlessmentGodHand()
	{
		super(CustomEnchantments.WEAPONS, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.godHandMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.godHandMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.godHandMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public int onLivingEntityStopUseItem(LivingEntity entity, @NotNull ItemStack stack, int duration)
	{
		int level = stack.getEnchantmentLevel(CustomEnchantments.GOD_HAND.get());
		if(level > 0)
		{
			ITEM_MAP.put(stack.getItem(), stack);
		}
		else if(ITEM_MAP.containsValue(stack))
		{
			ITEM_MAP.remove(stack.getItem(), stack);
		}
		return super.onLivingEntityStopUseItem(entity, stack, duration);
	}
	
	@Override
	public void onPlayerLeftClickEmpty(Player player, ItemStack stack, InteractionHand hand, BlockPos clickPos) 
	{
		int level = stack.getEnchantmentLevel(CustomEnchantments.GOD_HAND.get());
		if(level > 0)
		{
			ITEM_MAP.put(stack.getItem(), stack);
		}
		else if(ITEM_MAP.containsValue(stack))
		{
			ITEM_MAP.remove(stack.getItem(), stack);
		}
	}
	
	@Override
	public void onPlayerRightClickItem(Player player, ItemStack stack, InteractionHand hand, BlockPos clickPos, Direction clickDir) 
	{
		int level = stack.getEnchantmentLevel(CustomEnchantments.GOD_HAND.get());
		if(level > 0)
		{
			ITEM_MAP.put(stack.getItem(), stack);
		}
		else if(ITEM_MAP.containsValue(stack))
		{
			ITEM_MAP.remove(stack.getItem(), stack);
		}
	}
	
	@Override
	public void onPlayerRightClickBlock(Player player, ItemStack stack, InteractionHand hand, BlockPos clickPos, BlockHitResult hitVec)
	{
		int level = stack.getEnchantmentLevel(CustomEnchantments.GOD_HAND.get());
		if(level > 0)
		{
			ITEM_MAP.put(stack.getItem(), stack);
		}
		else if(ITEM_MAP.containsValue(stack))
		{
			ITEM_MAP.remove(stack.getItem(), stack);
		}
	}
}
