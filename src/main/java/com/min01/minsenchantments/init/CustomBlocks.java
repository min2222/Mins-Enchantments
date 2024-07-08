package com.min01.minsenchantments.init;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.block.AbstractCustomEnchantmentTableBlock;
import com.min01.minsenchantments.block.BlessmentTableBlock;
import com.min01.minsenchantments.block.EndEnchantmentTableBlock;
import com.min01.minsenchantments.block.NetherEnchantmentTableBlock;
import com.min01.minsenchantments.block.OceanEnchantmentTableBlock;
import com.min01.minsenchantments.block.SculkEnchantmentTableBlock;
import com.min01.minsenchantments.blockentity.BlessmentTableBlockEntity;
import com.min01.minsenchantments.blockentity.EndEnchantmentTableBlockEntity;
import com.min01.minsenchantments.blockentity.NetherEnchantmentTableBlockEntity;
import com.min01.minsenchantments.blockentity.OceanEnchantmentTableBlockEntity;
import com.min01.minsenchantments.blockentity.SculkEnchantmentTableBlockEntity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomBlocks
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MinsEnchantments.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MinsEnchantments.MODID);
    
    public static final BlockBehaviour.Properties TABLE_PROPERITES = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().lightLevel((p_152692_) ->
    {
    	return 7;
    }).strength(5.0F, 1200.0F);
    
    public static final RegistryObject<AbstractCustomEnchantmentTableBlock> OCEAN_ENCHANTMENT_TABLE = BLOCKS.register("ocean_enchanting_table", () -> new OceanEnchantmentTableBlock(TABLE_PROPERITES));
    public static final RegistryObject<BlockEntityType<OceanEnchantmentTableBlockEntity>> OCEAN_ENCHANTMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("ocean_enchanting_table", () -> BlockEntityType.Builder.of(OceanEnchantmentTableBlockEntity::new, OCEAN_ENCHANTMENT_TABLE.get()).build(null));

    public static final RegistryObject<AbstractCustomEnchantmentTableBlock> NETHER_ENCHANTMENT_TABLE = BLOCKS.register("nether_enchanting_table", () -> new NetherEnchantmentTableBlock(TABLE_PROPERITES));
    public static final RegistryObject<BlockEntityType<NetherEnchantmentTableBlockEntity>> NETHER_ENCHANTMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("nether_enchanting_table", () -> BlockEntityType.Builder.of(NetherEnchantmentTableBlockEntity::new, NETHER_ENCHANTMENT_TABLE.get()).build(null));
    
    public static final RegistryObject<AbstractCustomEnchantmentTableBlock> END_ENCHANTMENT_TABLE = BLOCKS.register("end_enchanting_table", () -> new EndEnchantmentTableBlock(TABLE_PROPERITES));
    public static final RegistryObject<BlockEntityType<EndEnchantmentTableBlockEntity>> END_ENCHANTMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("end_enchanting_table", () -> BlockEntityType.Builder.of(EndEnchantmentTableBlockEntity::new, END_ENCHANTMENT_TABLE.get()).build(null));
    
    public static final RegistryObject<AbstractCustomEnchantmentTableBlock> SCULK_ENCHANTMENT_TABLE = BLOCKS.register("sculk_enchanting_table", () -> new SculkEnchantmentTableBlock(TABLE_PROPERITES));
    public static final RegistryObject<BlockEntityType<SculkEnchantmentTableBlockEntity>> SCULK_ENCHANTMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("sculk_enchanting_table", () -> BlockEntityType.Builder.of(SculkEnchantmentTableBlockEntity::new, SCULK_ENCHANTMENT_TABLE.get()).build(null));
    
    public static final RegistryObject<AbstractCustomEnchantmentTableBlock> BLESSMENT_TABLE = BLOCKS.register("blessing_table", () -> new BlessmentTableBlock(TABLE_PROPERITES));
    public static final RegistryObject<BlockEntityType<BlessmentTableBlockEntity>> BLESSMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("blessing_table", () -> BlockEntityType.Builder.of(BlessmentTableBlockEntity::new, BLESSMENT_TABLE.get()).build(null));
}