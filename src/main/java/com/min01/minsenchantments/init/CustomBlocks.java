package com.min01.minsenchantments.init;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.block.AbstractCustomEnchantmentTableBlock;
import com.min01.minsenchantments.block.NetherEnchantmentTableBlock;
import com.min01.minsenchantments.block.OceanEnchantmentTableBlock;
import com.min01.minsenchantments.blockentity.NetherEnchantmentTableBlockEntity;
import com.min01.minsenchantments.blockentity.OceanEnchantmentTableBlockEntity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomBlocks
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MinsEnchantments.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MinsEnchantments.MODID);
    
    public static final BlockBehaviour.Properties ENCHANTMENT_PROPERITES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().lightLevel((p_152692_) ->
    {
    	return 7;
    }).strength(5.0F, 1200.0F);
    
    public static final RegistryObject<AbstractCustomEnchantmentTableBlock> OCEAN_ENCHANTMENT_TABLE = BLOCKS.register("ocean_enchanting_table", () -> new OceanEnchantmentTableBlock(ENCHANTMENT_PROPERITES));
    public static final RegistryObject<BlockEntityType<OceanEnchantmentTableBlockEntity>> OCEAN_ENCHANTMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("ocean_enchanting_table", () -> BlockEntityType.Builder.of(OceanEnchantmentTableBlockEntity::new, OCEAN_ENCHANTMENT_TABLE.get()).build(null));

    public static final RegistryObject<AbstractCustomEnchantmentTableBlock> NETHER_ENCHANTMENT_TABLE = BLOCKS.register("nether_enchanting_table", () -> new NetherEnchantmentTableBlock(ENCHANTMENT_PROPERITES));
    public static final RegistryObject<BlockEntityType<NetherEnchantmentTableBlockEntity>> NETHER_ENCHANTMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("nether_enchanting_table", () -> BlockEntityType.Builder.of(NetherEnchantmentTableBlockEntity::new, NETHER_ENCHANTMENT_TABLE.get()).build(null));
}
