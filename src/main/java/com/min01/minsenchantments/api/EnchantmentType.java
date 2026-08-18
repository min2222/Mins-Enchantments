package com.min01.minsenchantments.api;

import java.util.function.Supplier;

import com.min01.minsenchantments.block.MEBlocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public enum EnchantmentType
{
	OCEAN(() -> MEBlocks.OCEAN_ENCHANTMENT_TABLE.get(), () -> Items.PRISMARINE_SHARD),
	NETHER(() -> MEBlocks.NETHER_ENCHANTMENT_TABLE.get(), () -> Items.BLAZE_POWDER),
	END(() -> MEBlocks.END_ENCHANTMENT_TABLE.get(), () -> Items.ENDER_PEARL),
	SCULK(() -> MEBlocks.SCULK_ENCHANTMENT_TABLE.get(), () -> Items.ECHO_SHARD),
	BLESS(() -> MEBlocks.BLESSMENT_TABLE.get(), () -> Items.DIAMOND);
	
	private final Supplier<Block> tableBlock;
	private final Supplier<Item> fuelItem;
	
	private EnchantmentType(Supplier<Block> tableBlock, Supplier<Item> fuelItem) 
	{
		this.tableBlock = tableBlock;
		this.fuelItem = fuelItem;
	}
	
	public Supplier<Block> getTableBlock()
	{
		return this.tableBlock;
	}
	
	public Supplier<Item> getFuelItem()
	{
		return this.fuelItem;
	}
}