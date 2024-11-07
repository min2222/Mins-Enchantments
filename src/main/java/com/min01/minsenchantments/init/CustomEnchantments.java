package com.min01.minsenchantments.init;

import java.util.ArrayList;
import java.util.List;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.blessment.BlessmentAccelerate;
import com.min01.minsenchantments.blessment.BlessmentAirSwimming;
import com.min01.minsenchantments.blessment.BlessmentAutoShielding;
import com.min01.minsenchantments.blessment.BlessmentBarrier;
import com.min01.minsenchantments.blessment.BlessmentCriticalStrike;
import com.min01.minsenchantments.blessment.BlessmentGodHand;
import com.min01.minsenchantments.blessment.BlessmentGrinding;
import com.min01.minsenchantments.blessment.BlessmentHardening;
import com.min01.minsenchantments.blessment.BlessmentHealingCutter;
import com.min01.minsenchantments.blessment.BlessmentHeartSteal;
import com.min01.minsenchantments.blessment.BlessmentHoming;
import com.min01.minsenchantments.blessment.BlessmentMalice;
import com.min01.minsenchantments.blessment.BlessmentMirror;
import com.min01.minsenchantments.blessment.BlessmentMultiStrike;
import com.min01.minsenchantments.blessment.BlessmentRage;
import com.min01.minsenchantments.blessment.BlessmentReturning;
import com.min01.minsenchantments.blessment.BlessmentSolaria;
import com.min01.minsenchantments.blessment.BlessmentSoulOfTerrarian;
import com.min01.minsenchantments.blessment.BlessmentTimeBreak;
import com.min01.minsenchantments.enchantment.curse.EnchantmentOvergravity;
import com.min01.minsenchantments.enchantment.curse.EnchantmentUndeadCurse;
import com.min01.minsenchantments.enchantment.end.EnchantmentEndermanCurse;
import com.min01.minsenchantments.enchantment.end.EnchantmentTeleportation;
import com.min01.minsenchantments.enchantment.nether.EnchantmentAutoSmelt;
import com.min01.minsenchantments.enchantment.nether.EnchantmentAutoSmelt.EnchantmentAutoSmeltModifier;
import com.min01.minsenchantments.enchantment.nether.EnchantmentDry;
import com.min01.minsenchantments.enchantment.nether.EnchantmentFlameThorn;
import com.min01.minsenchantments.enchantment.nether.EnchantmentLavaWalker;
import com.min01.minsenchantments.enchantment.nether.EnchantmentSoulFire;
import com.min01.minsenchantments.enchantment.normal.EnchantmentArmorCrack;
import com.min01.minsenchantments.enchantment.normal.EnchantmentCellDivision;
import com.min01.minsenchantments.enchantment.normal.EnchantmentClimb;
import com.min01.minsenchantments.enchantment.normal.EnchantmentMagnet;
import com.min01.minsenchantments.enchantment.normal.EnchantmentMiner;
import com.min01.minsenchantments.enchantment.normal.EnchantmentRecochet;
import com.min01.minsenchantments.enchantment.normal.EnchantmentTakeoff;
import com.min01.minsenchantments.enchantment.normal.EnchantmentWallbreak;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentAbyssCurse;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentAquaticAura;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentFloatingCurse;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentLordOfTheSea;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentMermaidCurse;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentMermaidsBlessing;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentPoseidonsGrace;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentRusty;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentSailing;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentSharpWaves;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentSinkingCurse;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentTide;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentWaterJet;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentWaterbolt;
import com.min01.minsenchantments.enchantment.ocean.EnchantmentWavesProtection;
import com.min01.minsenchantments.enchantment.sculk.EnchantmentConcealment;
import com.min01.minsenchantments.enchantment.sculk.EnchantmentInfection;
import com.min01.minsenchantments.enchantment.sculk.EnchantmentSonicBoom;
import com.mojang.serialization.Codec;

import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
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
	
	public static final List<Enchantment> LIST = new ArrayList<>();
	
	public static final EnchantmentCategory PROJECTILE_WEAPON = EnchantmentCategory.create("PROJECTILE_WEAPON", (item) -> item instanceof ProjectileWeaponItem);
	public static final EnchantmentCategory SHIELD = EnchantmentCategory.create("SHIELD", (item) -> item instanceof ShieldItem);
	public static final EnchantmentCategory ELYTRA = EnchantmentCategory.create("ELYTRA", (item) -> item instanceof ElytraItem);
	public static final EnchantmentCategory WEAPONS = EnchantmentCategory.create("WEAPONS", (item) -> item instanceof ProjectileWeaponItem || item instanceof SwordItem || item instanceof ShieldItem);
	
	public static final RegistryObject<Codec<EnchantmentAutoSmeltModifier>> AUTO_SMELT_LOOT_MODIFIER = LOOT_MODIFIERS.register("auto_smelt", EnchantmentAutoSmeltModifier.CODEC);
	
	//blessment
	public static final RegistryObject<Enchantment> BARRIER = ENCHANTMENTS.register("barrier", () -> new BlessmentBarrier());
	public static final RegistryObject<Enchantment> HARDENING = ENCHANTMENTS.register("hardening", () -> new BlessmentHardening());
	public static final RegistryObject<Enchantment> RAGE = ENCHANTMENTS.register("rage", () -> new BlessmentRage());
	public static final RegistryObject<Enchantment> RETURNING = ENCHANTMENTS.register("returning", () -> new BlessmentReturning());
	public static final RegistryObject<Enchantment> HOMING = ENCHANTMENTS.register("homing", () -> new BlessmentHoming());
	public static final RegistryObject<Enchantment> MIRROR = ENCHANTMENTS.register("mirror", () -> new BlessmentMirror());
	public static final RegistryObject<Enchantment> SOUL_OF_TERRARIAN = ENCHANTMENTS.register("soul_of_terrarian", () -> new BlessmentSoulOfTerrarian());
	public static final RegistryObject<Enchantment> GOD_HAND = ENCHANTMENTS.register("god_hand", () -> new BlessmentGodHand());
	public static final RegistryObject<Enchantment> MALICE = ENCHANTMENTS.register("malice", () -> new BlessmentMalice());
	public static final RegistryObject<Enchantment> AUTO_SHIELDING = ENCHANTMENTS.register("auto_shielding", () -> new BlessmentAutoShielding());
	public static final RegistryObject<Enchantment> GRINDING = ENCHANTMENTS.register("grinding", () -> new BlessmentGrinding());
	public static final RegistryObject<Enchantment> ACCELERATE = ENCHANTMENTS.register("accelerate", () -> new BlessmentAccelerate());
	public static final RegistryObject<Enchantment> CRITICAL_STRIKE = ENCHANTMENTS.register("critical_strike", () -> new BlessmentCriticalStrike());
	public static final RegistryObject<Enchantment> HEART_STEAL = ENCHANTMENTS.register("heart_steal", () -> new BlessmentHeartSteal());
	public static final RegistryObject<Enchantment> HEALING_CUTTER = ENCHANTMENTS.register("healing_cutter", () -> new BlessmentHealingCutter());
	public static final RegistryObject<Enchantment> AIR_SWIMMING = ENCHANTMENTS.register("air_swimming", () -> new BlessmentAirSwimming());
	public static final RegistryObject<Enchantment> MULTI_STRIKE = ENCHANTMENTS.register("multi_strike", () -> new BlessmentMultiStrike());
	public static final RegistryObject<Enchantment> SOLARIA = ENCHANTMENTS.register("solaria", () -> new BlessmentSolaria());
	public static final RegistryObject<Enchantment> TIME_BREAK = ENCHANTMENTS.register("time_break", () -> new BlessmentTimeBreak());
	
	//ocean
	public static final RegistryObject<Enchantment> WATERBOLT = ENCHANTMENTS.register("waterbolt", () -> new EnchantmentWaterbolt());
	public static final RegistryObject<Enchantment> MERMAIDS_BLESSING = ENCHANTMENTS.register("mermaids_blessing", () -> new EnchantmentMermaidsBlessing());
	public static final RegistryObject<Enchantment> SHARP_WAVES = ENCHANTMENTS.register("sharp_waves", () -> new EnchantmentSharpWaves());
	public static final RegistryObject<Enchantment> TIDE = ENCHANTMENTS.register("tide", () -> new EnchantmentTide());
	public static final RegistryObject<Enchantment> POSEIDONS_GRACE = ENCHANTMENTS.register("poseidons_grace", () -> new EnchantmentPoseidonsGrace());
	public static final RegistryObject<Enchantment> WAVES_PROTECTION = ENCHANTMENTS.register("waves_protection", () -> new EnchantmentWavesProtection());
	public static final RegistryObject<Enchantment> LORD_OF_THE_SEA = ENCHANTMENTS.register("lord_of_the_sea", () -> new EnchantmentLordOfTheSea());
	public static final RegistryObject<Enchantment> SAILING = ENCHANTMENTS.register("sailing", () -> new EnchantmentSailing());
	public static final RegistryObject<Enchantment> WATER_JET = ENCHANTMENTS.register("water_jet", () -> new EnchantmentWaterJet());
	public static final RegistryObject<Enchantment> AQUATIC_AURA = ENCHANTMENTS.register("aquatic_aura", () -> new EnchantmentAquaticAura());
	
	//nether
	public static final RegistryObject<Enchantment> LAVA_WALKER = ENCHANTMENTS.register("lava_walker", () -> new EnchantmentLavaWalker());
	public static final RegistryObject<Enchantment> AUTO_SMELT = ENCHANTMENTS.register("auto_smelt", () -> new EnchantmentAutoSmelt());
	public static final RegistryObject<Enchantment> FLAME_THORN = ENCHANTMENTS.register("flame_thorn", () -> new EnchantmentFlameThorn());
	public static final RegistryObject<Enchantment> DRY = ENCHANTMENTS.register("dry", () -> new EnchantmentDry());
	public static final RegistryObject<Enchantment> SOUL_FIRE = ENCHANTMENTS.register("soul_fire", () -> new EnchantmentSoulFire());
	
	//end
	public static final RegistryObject<Enchantment> TELEPORTATION = ENCHANTMENTS.register("teleportation", () -> new EnchantmentTeleportation());
	
	//sculk
	public static final RegistryObject<Enchantment> SONIC_BOOM = ENCHANTMENTS.register("sonic_boom", () -> new EnchantmentSonicBoom());
	public static final RegistryObject<Enchantment> INFECTION = ENCHANTMENTS.register("infection", () -> new EnchantmentInfection());
	public static final RegistryObject<Enchantment> CONCEALMENT = ENCHANTMENTS.register("concealment", () -> new EnchantmentConcealment());
	
	//normal
	public static final RegistryObject<Enchantment> RECOCHET = ENCHANTMENTS.register("recochet", () -> new EnchantmentRecochet());
	public static final RegistryObject<Enchantment> CLIMB = ENCHANTMENTS.register("climb", () -> new EnchantmentClimb());
	public static final RegistryObject<Enchantment> WALLBREAK = ENCHANTMENTS.register("wallbreak", () -> new EnchantmentWallbreak());
	public static final RegistryObject<Enchantment> MAGNET = ENCHANTMENTS.register("magnet", () -> new EnchantmentMagnet());
	public static final RegistryObject<Enchantment> CELL_DIVISION = ENCHANTMENTS.register("cell_division", () -> new EnchantmentCellDivision());
	public static final RegistryObject<Enchantment> TAKEOFF = ENCHANTMENTS.register("takeoff", () -> new EnchantmentTakeoff());
	public static final RegistryObject<Enchantment> ARMOR_CRACK = ENCHANTMENTS.register("armor_crack", () -> new EnchantmentArmorCrack());
	public static final RegistryObject<Enchantment> MINER = ENCHANTMENTS.register("miner", () -> new EnchantmentMiner());
	
	//curse
	public static final RegistryObject<Enchantment> CURSE_OF_MERMAID = ENCHANTMENTS.register("curse_of_mermaid", () -> new EnchantmentMermaidCurse());
	public static final RegistryObject<Enchantment> CURSE_OF_FLOATING = ENCHANTMENTS.register("curse_of_floating", () -> new EnchantmentFloatingCurse());
	public static final RegistryObject<Enchantment> CURSE_OF_ABYSS = ENCHANTMENTS.register("curse_of_abyss", () -> new EnchantmentAbyssCurse());
	public static final RegistryObject<Enchantment> RUSTY = ENCHANTMENTS.register("rusty", () -> new EnchantmentRusty());
	public static final RegistryObject<Enchantment> OVERGRAVITY = ENCHANTMENTS.register("overgravity", () -> new EnchantmentOvergravity());
	public static final RegistryObject<Enchantment> CURSE_OF_UNDEAD = ENCHANTMENTS.register("curse_of_undead", () -> new EnchantmentUndeadCurse());
	public static final RegistryObject<Enchantment> CURSE_OF_ENDERMAN = ENCHANTMENTS.register("curse_of_enderman", () -> new EnchantmentEndermanCurse());
	public static final RegistryObject<Enchantment> CURSE_OF_SINKING = ENCHANTMENTS.register("curse_of_sinking", () -> new EnchantmentSinkingCurse());
}
