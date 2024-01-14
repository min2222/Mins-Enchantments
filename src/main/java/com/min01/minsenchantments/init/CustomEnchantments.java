package com.min01.minsenchantments.init;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.enchantment.curse.EnchantmentOvergravity;
import com.min01.minsenchantments.enchantment.nether.EnchantmentAutoSmelt;
import com.min01.minsenchantments.enchantment.nether.EnchantmentAutoSmelt.EnchantmentAutoSmeltModifier;
import com.min01.minsenchantments.enchantment.nether.EnchantmentLavaWalker;
import com.min01.minsenchantments.enchantment.normal.EnchantmentAutoShielding;
import com.min01.minsenchantments.enchantment.normal.EnchantmentBarrier;
import com.min01.minsenchantments.enchantment.normal.EnchantmentClimb;
import com.min01.minsenchantments.enchantment.normal.EnchantmentCriticalStrike;
import com.min01.minsenchantments.enchantment.normal.EnchantmentLeech;
import com.min01.minsenchantments.enchantment.normal.EnchantmentMagnet;
import com.min01.minsenchantments.enchantment.normal.EnchantmentRecochet;
import com.min01.minsenchantments.enchantment.normal.EnchantmentSnipe;
import com.min01.minsenchantments.enchantment.normal.EnchantmentWallbreak;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentAbyssCurse;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentFloatingCurse;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentMermaidCurse;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentMermaidsBlessing;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentPoseidonsGrace;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentRusty;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentSharpWaves;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentTide;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentWaterbolt;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentWavesProtection;
import com.mojang.serialization.Codec;

import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomEnchantments 
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MinsEnchantments.MODID);
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MinsEnchantments.MODID);
	
	public static final EnchantmentCategory PROJECTILE_WEAPON = EnchantmentCategory.create("PROJECTILE_WEAPON", (item) -> item instanceof ProjectileWeaponItem);
	public static final EnchantmentCategory SHIELD = EnchantmentCategory.create("SHIELD", (item) -> item instanceof ShieldItem);
	
	public static final RegistryObject<Codec<EnchantmentAutoSmeltModifier>> AUTO_SMELT_LOOT_MODIFIER = LOOT_MODIFIERS.register("auto_smelt", EnchantmentAutoSmeltModifier.CODEC);
	
	//ocean
	public static final RegistryObject<Enchantment> WATERBOLT = ENCHANTMENTS.register("waterbolt", () -> new EnchantmentWaterbolt());
	public static final RegistryObject<Enchantment> MERMAIDS_BLESSING = ENCHANTMENTS.register("mermaids_blessing", () -> new EnchantmentMermaidsBlessing());
	public static final RegistryObject<Enchantment> SHARP_WAVES = ENCHANTMENTS.register("sharp_waves", () -> new EnchantmentSharpWaves());
	public static final RegistryObject<Enchantment> TIDE = ENCHANTMENTS.register("tide", () -> new EnchantmentTide());
	public static final RegistryObject<Enchantment> POSEIDONS_GRACE = ENCHANTMENTS.register("poseidons_grace", () -> new EnchantmentPoseidonsGrace());
	public static final RegistryObject<Enchantment> WAVES_PROTECTION = ENCHANTMENTS.register("waves_protection", () -> new EnchantmentWavesProtection());
	
	//nether
	public static final RegistryObject<Enchantment> LAVA_WALKER = ENCHANTMENTS.register("lava_walker", () -> new EnchantmentLavaWalker());
	public static final RegistryObject<Enchantment> AUTO_SMELT = ENCHANTMENTS.register("auto_smelt", () -> new EnchantmentAutoSmelt());
	
	//end
	
	//sculk
	
	//normal
	public static final RegistryObject<Enchantment> RECOCHET = ENCHANTMENTS.register("recochet", () -> new EnchantmentRecochet());
	public static final RegistryObject<Enchantment> CLIMB = ENCHANTMENTS.register("climb", () -> new EnchantmentClimb());
	public static final RegistryObject<Enchantment> CRITICAL_STRIKE = ENCHANTMENTS.register("critical_strike", () -> new EnchantmentCriticalStrike());
	public static final RegistryObject<Enchantment> LEECH = ENCHANTMENTS.register("leech", () -> new EnchantmentLeech());
	public static final RegistryObject<Enchantment> WALLBREAK = ENCHANTMENTS.register("wallbreak", () -> new EnchantmentWallbreak());
	public static final RegistryObject<Enchantment> SNIPE = ENCHANTMENTS.register("snipe", () -> new EnchantmentSnipe());
	public static final RegistryObject<Enchantment> MAGNET = ENCHANTMENTS.register("magnet", () -> new EnchantmentMagnet());
	public static final RegistryObject<Enchantment> AUTO_SHIELDING = ENCHANTMENTS.register("auto_shielding", () -> new EnchantmentAutoShielding());
	public static final RegistryObject<Enchantment> BARRIER = ENCHANTMENTS.register("barrier", () -> new EnchantmentBarrier());
	
	//curse
	public static final RegistryObject<Enchantment> CURSE_OF_MERMAID = ENCHANTMENTS.register("curse_of_mermaid", () -> new EnchantmentMermaidCurse());
	public static final RegistryObject<Enchantment> CURSE_OF_FLOATING = ENCHANTMENTS.register("curse_of_floating", () -> new EnchantmentFloatingCurse());
	public static final RegistryObject<Enchantment> CURSE_OF_ABYSS = ENCHANTMENTS.register("curse_of_abyss", () -> new EnchantmentAbyssCurse());
	public static final RegistryObject<Enchantment> RUSTY = ENCHANTMENTS.register("rusty", () -> new EnchantmentRusty());
	public static final RegistryObject<Enchantment> OVERGRAVITY = ENCHANTMENTS.register("overgravity", () -> new EnchantmentOvergravity());
}
