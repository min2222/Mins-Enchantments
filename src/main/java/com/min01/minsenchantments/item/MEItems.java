package com.min01.minsenchantments.item;

import java.util.function.Supplier;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.block.MEBlocks;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MEItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MinsEnchantments.MODID);
	
	public static final RegistryObject<Item> OCEAN_ENCHANTMENT_TABLE = ITEMS.register("ocean_enchanting_table", () -> new BlockItem(MEBlocks.OCEAN_ENCHANTMENT_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> NETHER_ENCHANTMENT_TABLE = ITEMS.register("nether_enchanting_table", () -> new BlockItem(MEBlocks.NETHER_ENCHANTMENT_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> END_ENCHANTMENT_TABLE = ITEMS.register("end_enchanting_table", () -> new BlockItem(MEBlocks.END_ENCHANTMENT_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> SCULK_ENCHANTMENT_TABLE = ITEMS.register("sculk_enchanting_table", () -> new BlockItem(MEBlocks.SCULK_ENCHANTMENT_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> BLESSMENT_TABLE = ITEMS.register("blessing_table", () -> new BlockItem(MEBlocks.BLESSMENT_TABLE.get(), new Item.Properties()));
	
	public static RegistryObject<Item> registerBlockItem(String name, Supplier<Block> block, Item.Properties properties)
	{
		return ITEMS.register(name, () -> new BlockItem(block.get(), properties));
	}
	
	public static <T extends Mob> RegistryObject<Item> registerSpawnEgg(String name, Supplier<EntityType<T>> entity, int color1, int color2) 
	{
		return ITEMS.register(name, () -> new ForgeSpawnEggItem(entity, color1, color2, new Item.Properties()));
	}
}
