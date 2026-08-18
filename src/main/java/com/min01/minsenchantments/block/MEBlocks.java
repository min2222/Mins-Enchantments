package com.min01.minsenchantments.block;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.blockentity.MEnchantmentTableBlockEntity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MEBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MinsEnchantments.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MinsEnchantments.MODID);
    
    public static final RegistryObject<MEnchantmentTableBlock> OCEAN_ENCHANTMENT_TABLE = BLOCKS.register("ocean_enchanting_table", () -> createTable(ParticleTypes.SPLASH, EnchantmentType.OCEAN));
    public static final RegistryObject<MEnchantmentTableBlock> NETHER_ENCHANTMENT_TABLE = BLOCKS.register("nether_enchanting_table", () -> createTable(ParticleTypes.FLAME, EnchantmentType.NETHER));
    public static final RegistryObject<MEnchantmentTableBlock> END_ENCHANTMENT_TABLE = BLOCKS.register("end_enchanting_table", () -> createTable(ParticleTypes.PORTAL, EnchantmentType.END));
    public static final RegistryObject<MEnchantmentTableBlock> SCULK_ENCHANTMENT_TABLE = BLOCKS.register("sculk_enchanting_table", () -> createTable(ParticleTypes.SCULK_SOUL, EnchantmentType.SCULK));
    public static final RegistryObject<MEnchantmentTableBlock> BLESSMENT_TABLE = BLOCKS.register("blessing_table", () -> createTable(ParticleTypes.END_ROD, EnchantmentType.BLESS));
    
    public static MEnchantmentTableBlock createTable(ParticleOptions particle, EnchantmentType type)
    {
    	return new MEnchantmentTableBlock(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE), particle, type);
    }
    
    public static final RegistryObject<BlockEntityType<MEnchantmentTableBlockEntity>> ME_ENCHANTMENT_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("me_enchanting_table", () -> BlockEntityType.Builder.of(MEnchantmentTableBlockEntity::new,
    		OCEAN_ENCHANTMENT_TABLE.get(),
    		NETHER_ENCHANTMENT_TABLE.get(),
    		END_ENCHANTMENT_TABLE.get(),
    		SCULK_ENCHANTMENT_TABLE.get(),
    		BLESSMENT_TABLE.get()).build(null));
}
