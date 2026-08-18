package com.min01.minsenchantments.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MEConfig 
{
	public static final ForgeConfigSpec CONFIG_SPEC = build();
	
	//ocean
	public static ForgeConfigSpec.DoubleValue bubbleGuardPercentPerLevel;
	public static ForgeConfigSpec.DoubleValue depthAnglerLuckPerLevel;
	public static ForgeConfigSpec.IntValue depthAnglerMaxDepthPerLevel;
	public static ForgeConfigSpec.DoubleValue sinkingStrikePowerPerLevel;
	
	//nether
	public static ForgeConfigSpec.DoubleValue hellbaneAmountPerLevel;
	public static ForgeConfigSpec.IntValue soulFireAspectDurationPerLevel;
	public static ForgeConfigSpec.DoubleValue witherProtectionPercentPerLevel;
	public static ForgeConfigSpec.DoubleValue hellfireAmountPerLevel;
	public static ForgeConfigSpec.DoubleValue hellfireMaxAmountPerLevel;
	public static ForgeConfigSpec.IntValue hellfireIntervalPerLevel;
	public static ForgeConfigSpec.DoubleValue obsidianAegisAmountPerLevel;
	public static ForgeConfigSpec.DoubleValue obsidianAegisMaxAmountPerLevel;
	public static ForgeConfigSpec.IntValue obsidianAegisIntervalPerLevel;
	public static ForgeConfigSpec.DoubleValue infernalBarrageChancePerLevel;
	public static ForgeConfigSpec.IntValue infernalBarrageCountPerLevel;
	
	//end
	public static ForgeConfigSpec.DoubleValue gravityStabilityPercentPerLevel;
	public static ForgeConfigSpec.DoubleValue upwardStrikePowerPerLevel;
	public static ForgeConfigSpec.DoubleValue dimensionalAnchorChancePerLevel;
	public static ForgeConfigSpec.IntValue dimensionalAnchorDurationPerLevel;
	public static ForgeConfigSpec.DoubleValue obsidianShatterPercentPerLevel;
	public static ForgeConfigSpec.DoubleValue shulkerStancePercentPerLevel;
	public static ForgeConfigSpec.DoubleValue chorusEvasionChancePerLevel;
	public static ForgeConfigSpec.DoubleValue voideSnipeMaxDistancePerLevel;
	public static ForgeConfigSpec.DoubleValue enderGazeRadiusPerLevel;
	
	//sculk
	public static ForgeConfigSpec.DoubleValue sculkAdaptationPercentPerLevel;
	public static ForgeConfigSpec.DoubleValue sonicCleavePercentPerLevel;
	public static ForgeConfigSpec.DoubleValue catalystAbsorptionPercentPerLevel;
	public static ForgeConfigSpec.DoubleValue lifeErosionChancePerLevel;
	public static ForgeConfigSpec.IntValue lifeErosionDurationPerLevel;
	public static ForgeConfigSpec.DoubleValue lifeErosionPercentPerLevel;
	
	//bless
	public static ForgeConfigSpec.IntValue masterTouchCooldownPerLevel;
	public static ForgeConfigSpec.DoubleValue resoluteWardChancePerLevel;
	public static ForgeConfigSpec.DoubleValue barrierRadiusPerLevel;
	public static ForgeConfigSpec.DoubleValue barrierPowerPerLevel;
	public static ForgeConfigSpec.DoubleValue criticalStrikeChancePerLevel;
	public static ForgeConfigSpec.DoubleValue malicePercentPerLevel;
	public static ForgeConfigSpec.DoubleValue maliceReductionPerLevel;
	public static ForgeConfigSpec.DoubleValue mirrorChancePerLevel;
	public static ForgeConfigSpec.DoubleValue timeBreakChancePerLevel;
	public static ForgeConfigSpec.IntValue timeBreakDurationPerLevel;
	public static ForgeConfigSpec.IntValue terrarianSoulAmountPerLevel;
	
	//extra features
	public static ForgeConfigSpec.BooleanValue forceEnchanting;
	
    public static ForgeConfigSpec build() 
    {
    	ForgeConfigSpec.Builder config = new ForgeConfigSpec.Builder();
    	
    	config.push("Ocean Enchantment Settings");
    	bubbleGuardPercentPerLevel = config.comment("Air supply recover percentage per level of Bubble Guard Enchantment.").defineInRange("bubbleGuardPercentPerLevel", 10.0D, 0.0D, Double.MAX_VALUE);
    	depthAnglerLuckPerLevel = config.comment("Additional luck per level of Depth Angler Enchantment.").defineInRange("depthAnglerLuckPerLevel", 0.2D, 0.0D, Double.MAX_VALUE);
    	depthAnglerMaxDepthPerLevel = config.comment("Max depth per level of Depth Angler Enchantment.").defineInRange("depthAnglerMaxDepthPerLevel", 5, 0, Integer.MAX_VALUE);
    	sinkingStrikePowerPerLevel = config.comment("Sinking power per level of Sinking Strike Enchantment.").defineInRange("sinkingStrikePowerPerLevel", 0.35D, 0.0D, Double.MAX_VALUE);
        config.pop();
        
    	config.push("Nether Enchantment Settings");
    	hellbaneAmountPerLevel = config.comment("Additional damage per level of Hellbane Enchantment.").defineInRange("hellbaneAmountPerLevel", 2.5D, 0.0D, Double.MAX_VALUE);
    	soulFireAspectDurationPerLevel = config.comment("Soul fire duration (in ticks) per level of Soul Fire Aspect Enchantment.").defineInRange("soulFireAspectDurationPerLevel", 80, 0, Integer.MAX_VALUE);
    	witherProtectionPercentPerLevel = config.comment("Wither effect duration and damage interval reduction per level of Wither Protection Enchantment.").defineInRange("witherProtectionPercentPerLevel", 20.0, 0.0D, Double.MAX_VALUE);
    	hellfireAmountPerLevel = config.comment("Additional attack damage amount per level of Hellfire Enchantment.").defineInRange("hellfireAmountPerLevel", 0.5D, 0.0D, Double.MAX_VALUE);
    	hellfireMaxAmountPerLevel = config.comment("Max attack damage amount per level of Hellfire Enchantment.").defineInRange("hellfireMaxAmountPerLevel", 2.5D, 0.0D, Double.MAX_VALUE);
    	hellfireIntervalPerLevel = config.comment("Attack damage decrease interval (in ticks) per level of Hellfire Enchantment.").defineInRange("hellfireIntervalPerLevel", 10, 0, Integer.MAX_VALUE);
    	obsidianAegisAmountPerLevel = config.comment("Additional armor point amount per level of Obsidian Aegis Enchantment.").defineInRange("obsidianAegisAmountPerLevel", 0.5D, 0.0D, Double.MAX_VALUE);
    	obsidianAegisMaxAmountPerLevel = config.comment("Max armor point amount per level of Obsidian Aegis Enchantment.").defineInRange("obsidianAegisMaxAmountPerLevel", 2.5D, 0.0D, Double.MAX_VALUE);
    	obsidianAegisIntervalPerLevel = config.comment("Armor point decrease interval (in ticks) per level of Obsidian Aegis Enchantment.").defineInRange("obsidianAegisIntervalPerLevel", 10, 0, Integer.MAX_VALUE);
    	infernalBarrageChancePerLevel = config.comment("Barrage chance per level of Infernal Barrage Enchantment.").defineInRange("infernalBarrageChancePerLevel", 15.0D, 0.0D, Double.MAX_VALUE);
    	infernalBarrageCountPerLevel = config.comment("Additional attack count per level of Infernal Barrage Enchantment.").defineInRange("infernalBarrageCountPerLevel", 1, 0, Integer.MAX_VALUE);
        config.pop();
        
    	config.push("End Enchantment Settings");
    	gravityStabilityPercentPerLevel = config.comment("Speed reduction per level of Gravity Stability Enchantment.").defineInRange("gravityStabilityPercentPerLevel", 30.0D, 0.0D, Double.MAX_VALUE);
    	upwardStrikePowerPerLevel = config.comment("Knockback power per level of Upward Strike Enchantment.").defineInRange("upwardStrikePowerPerLevel", 0.5D, 0.0D, Double.MAX_VALUE);
    	dimensionalAnchorChancePerLevel = config.comment("Anchoring chance per level of Dimensional Anchor Enchantment.").defineInRange("dimensionalAnchorChancePerLevel", 20.0D, 0.0D, Double.MAX_VALUE);
    	dimensionalAnchorDurationPerLevel = config.comment("Anchoring duration (in ticks) per level of Dimensional Anchor Enchantment.").defineInRange("dimensionalAnchorDurationPerLevel", 20, 0, Integer.MAX_VALUE);
    	obsidianShatterPercentPerLevel = config.comment("Additional mining speed percentage per level of Obsidian Shatter Enchantment.").defineInRange("obsidianShatterPercentPerLevel", 100.0D, 0.0D, Double.MAX_VALUE);
    	shulkerStancePercentPerLevel = config.comment("Damage and knockback reduction percentage per level of Shulker Stance Enchantment.").defineInRange("shulkerStancePercentPerLevel", 20.0D, 0.0D, Double.MAX_VALUE);
    	chorusEvasionChancePerLevel = config.comment("Evasion chance per level of Chorus Evasion Enchantment.").defineInRange("chorusEvasionChancePerLevel", 20.0D, 0.0D, Double.MAX_VALUE);
    	voideSnipeMaxDistancePerLevel = config.comment("Max sniping distance per level of Void Snipe Enchantment.").defineInRange("voideSnipeMaxDistancePerLevel", 30.0D, 0.0D, Double.MAX_VALUE);
    	enderGazeRadiusPerLevel = config.comment("Gazing radius per level of Ender Gaze Enchantment.").defineInRange("enderGazeRadiusPerLevel", 1.5D, 0.0D, Double.MAX_VALUE);
        config.pop();
        
    	config.push("Sculk Enchantment Settings");
    	sculkAdaptationPercentPerLevel = config.comment("Darkness reduction percentage per level of Sculk Adaptation Enchantment.").defineInRange("sculkAdaptationPercentPerLevel", 40.0D, 0.0D, Double.MAX_VALUE);
    	sonicCleavePercentPerLevel = config.comment("Armor point percentage per level of Sonic Cleave Enchantment.").defineInRange("sonicCleavePercentPerLevel", 16.0D, 0.0D, Double.MAX_VALUE);
    	catalystAbsorptionPercentPerLevel = config.comment("Additional exp point percentage per level of Catalyst Absorption Enchantment.").defineInRange("catalystAbsorptionPercentPerLevel", 25.0D, 0.0D, Double.MAX_VALUE);
    	lifeErosionChancePerLevel = config.comment("Erosion chance per level of Life Erosion Enchantment.").defineInRange("lifeErosionChancePerLevel", 15.0D, 0.0D, Double.MAX_VALUE);
    	lifeErosionDurationPerLevel = config.comment("Erosion duration (in ticks) per level of Life Erosion Enchantment.").defineInRange("lifeErosionDurationPerLevel", 60, 0, Integer.MAX_VALUE);
    	lifeErosionPercentPerLevel = config.comment("Healing reduction percentage per level of Life Erosion Enchantment.").defineInRange("lifeErosionPercentPerLevel", 20.0D, 0.0D, Double.MAX_VALUE);
    	config.pop();
        
    	config.push("Blessment Settings");
    	masterTouchCooldownPerLevel = config.comment("Cooldown reduction (in ticks) per level of Master Touch Blessment.").defineInRange("masterTouchCooldownPerLevel", 20, 0, Integer.MAX_VALUE);
    	resoluteWardChancePerLevel = config.comment("Blocking chance per level of Resolute Ward Blessment.").defineInRange("resoluteWardChancePerLevel", 20.0D, 0.0D, Double.MAX_VALUE);
    	barrierRadiusPerLevel = config.comment("Push radius per level of Barrier Blessment.").defineInRange("barrierRadiusPerLevel", 1.5D, 0.0D, Double.MAX_VALUE);
    	barrierPowerPerLevel = config.comment("Push power per level of Barrier Blessment.").defineInRange("barrierPowerPerLevel", 0.15D, 0.0D, Double.MAX_VALUE);
    	criticalStrikeChancePerLevel = config.comment("Critical chance per level of Critical Strike Blessment.").defineInRange("criticalStrikeChancePerLevel", 15.0D, 0.0D, Double.MAX_VALUE);
    	malicePercentPerLevel = config.comment("Health percentage per level of Malice Blessment.").defineInRange("malicePercentPerLevel", 5.0D, 0.0D, Double.MAX_VALUE);
    	maliceReductionPerLevel = config.comment("Attack damage reduction per level of Malice Blessment.").defineInRange("maliceReductionPerLevel", 15.0D, 0.0D, Double.MAX_VALUE);
    	mirrorChancePerLevel = config.comment("Mirroring chance per level of Mirror Blessment.").defineInRange("mirrorChancePerLevel", 15.0D, 0.0D, Double.MAX_VALUE);
    	timeBreakChancePerLevel = config.comment("Break chance per level of Time Break Blessment.").defineInRange("timeBreakChancePerLevel", 5.0D, 0.0D, Double.MAX_VALUE);
    	timeBreakDurationPerLevel = config.comment("Break duration (in ticks) per level of Time Break Blessment.").defineInRange("timeBreakDurationPerLevel", 20, 0, Integer.MAX_VALUE);
    	terrarianSoulAmountPerLevel = config.comment("Additional projectile amount per level of Terrarian Soul Blessment.").defineInRange("terrarianSoulAmountPerLevel", 5, 0, Integer.MAX_VALUE);
        config.pop();
        
    	config.push("Extra Settings");
    	forceEnchanting = config.comment("Allow to enchant any non-stackable item in anvil.").define("forceEnchanting", true);
        config.pop();
        
        return config.build();
    }
}
