package com.min01.minsenchantments.init;

import com.min01.minsenchantments.MinsEnchantments;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MinsEnchantments.MODID);
	
	public static final RegistryObject<Item> OCEAN_ENCHANTMENT_TABLE = ITEMS.register("ocean_enchanting_table", () -> new BlockItem(CustomBlocks.OCEAN_ENCHANTMENT_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> NETHER_ENCHANTMENT_TABLE = ITEMS.register("nether_enchanting_table", () -> new BlockItem(CustomBlocks.NETHER_ENCHANTMENT_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> END_ENCHANTMENT_TABLE = ITEMS.register("end_enchanting_table", () -> new BlockItem(CustomBlocks.END_ENCHANTMENT_TABLE.get(), new Item.Properties()));
}
