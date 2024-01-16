package com.min01.minsenchantments.config;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class EnchantmentConfig 
{
    private static ForgeConfigSpec.Builder BUILDER;
    public static ForgeConfigSpec CONFIG;

    //enchantment
    
    //ocean
	public static ConfigValue<Integer> waterboltMinCost;
	public static ConfigValue<Integer> waterboltMaxCost;
	
	public static ConfigValue<Integer> mermaidsBlessingMinCost;
	public static ConfigValue<Integer> mermaidsBlessingMaxCost;
	
	public static ConfigValue<Float> mermaidsBlessingMaxMobHealthPerLevel;
	public static ConfigValue<Double> mermaidsBlessingMaxMobDamagePerLevel;
	
	public static ConfigValue<Integer> sharpWavesMinCost;
	public static ConfigValue<Integer> sharpWavesMaxCost;

	public static ConfigValue<Float> sharpWavesMaxDamagePerLevel;
	public static ConfigValue<Float> sharpWavesDistancePerLevel;
	public static ConfigValue<Float> sharpWavesDamagePerLevel;
	
	public static ConfigValue<Integer> tideMinCost;
	public static ConfigValue<Integer> tideMaxCost;
	
	public static ConfigValue<Float> tideMaxSpeedPerLevel;
	public static ConfigValue<Float> tideSpeedPerLevel;
	
	public static ConfigValue<Integer> poseidonsGraceMinCost;
	public static ConfigValue<Integer> poseidonsGraceMaxCost;
	
	public static ConfigValue<Float> poseidonsGraceTridentSpeed;
	
	public static ConfigValue<Integer> wavesProtectionMinCost;
	public static ConfigValue<Integer> wavesProtectionMaxCost;
	
	public static ConfigValue<Float> wavesProtectionSpeedPerLevel;
	public static ConfigValue<Integer> wavesProtectionDurationPerLevel;
	
	public static ConfigValue<Integer> lordoftheseaMinCost;
	public static ConfigValue<Integer> lordoftheseaMaxCost;
	
	public static ConfigValue<Float> lordoftheseaRadiusPerLevel;
	public static ConfigValue<Float> lordoftheseaAnimalsDamagePerLevel;
	public static ConfigValue<Integer> lordoftheseaMaxAnimalsAmountPerLevel;
	
	public static ConfigValue<Integer> mermaidsCurseMinCost;
	public static ConfigValue<Integer> mermaidsCurseMaxCost;
	
	public static ConfigValue<Float> mermaidsCurseRadiusPerLevel;
	
	public static ConfigValue<Integer> floatingCurseMinCost;
	public static ConfigValue<Integer> floatingCurseMaxCost;

	public static ConfigValue<Float> floatingCurseFloatingSpeedPerLevel;
	
	public static ConfigValue<Integer> abyssCurseMinCost;
	public static ConfigValue<Integer> abyssCurseMaxCost;
	
	public static ConfigValue<Float> abyssCurseFogDensityPerLevel;	
	
	public static ConfigValue<Integer> rustyMinCost;
	public static ConfigValue<Integer> rustyMaxCost;
	
	public static ConfigValue<Float> rustyDecreaseIntervalPerLevel;
	
	public static ConfigValue<Integer> overgravityMinCost;
	public static ConfigValue<Integer> overgravityMaxCost;
	
	public static ConfigValue<Float> overgravityFallDamagePerLevel;
	public static ConfigValue<Float> overgravityFallSpeedPerLevel;
	
	//nether
	public static ConfigValue<Integer> lavaWalkerMinCost;
	public static ConfigValue<Integer> lavaWalkerMaxCost;
	
	public static ConfigValue<Integer> autoSmeltMinCost;
	public static ConfigValue<Integer> autoSmeltMaxCost;
	
	//end
	
	//sculk
	
	//normal
	public static ConfigValue<Integer> recochetMinCost;
	public static ConfigValue<Integer> recochetMaxCost;
	
	public static ConfigValue<Integer> recochetMaxBounce;
	
	public static ConfigValue<Integer> climbMinCost;
	public static ConfigValue<Integer> climbMaxCost;
	
	public static ConfigValue<Double> climbSpeedPerLevel;
	
	public static ConfigValue<Integer> criticalStrikeMinCost;
	public static ConfigValue<Integer> criticalStrikeMaxCost;
	
	public static ConfigValue<Float> criticalStrikeChancePerLevel;
	
	public static ConfigValue<Integer> leechMinCost;
	public static ConfigValue<Integer> leechMaxCost;
	
	public static ConfigValue<Float> leechHealAmountPerLevel;
	public static ConfigValue<Float> leechChancePerLevel;
	
	public static ConfigValue<Integer> wallbreakMinCost;
	public static ConfigValue<Integer> wallbreakMaxCost;
	
	public static ConfigValue<Integer> snipeMinCost;
	public static ConfigValue<Integer> snipeMaxCost;
	
	public static ConfigValue<Integer> snipeProjectileSpeedPerLevel;
	public static ConfigValue<Float> snipeAdditionalDamagePerLevel;
	
	public static ConfigValue<Integer> magnetMinCost;
	public static ConfigValue<Integer> magnetMaxCost;
	
	public static ConfigValue<Float> magnetRadiusPerLevel;
	
	public static ConfigValue<Integer> autoShieldingMinCost;
	public static ConfigValue<Integer> autoShieldingMaxCost;
	
	public static ConfigValue<Float> autoShieldingChancePerLevel;

	public static ConfigValue<Integer> barrierMinCost;
	public static ConfigValue<Integer> barrierMaxCost;
	
	public static ConfigValue<Float> barrierRadiusPerLevel;
	public static ConfigValue<Float> barrierPushPowerPerLevel;
	
	public static ForgeConfigSpec.BooleanValue disableBarrierPushingPlayer;
	
	public static ConfigValue<Integer> skillfulMinCost;
	public static ConfigValue<Integer> skillfulMaxCost;
	
	public static ConfigValue<Float> skillfulMaxDamagePerLevel;
	public static ConfigValue<Float> skillfulDurabilityPerLevel;
	public static ConfigValue<Float> skillfulDamagePerLevel;
	
	public static ConfigValue<Integer> cellDivisionMinCost;
	public static ConfigValue<Integer> cellDivisionMaxCost;

	public static ConfigValue<Float> cellDivisionScalePerSplit;
	public static ConfigValue<Integer> cellDivisionMaxSplitPerLevel;
	public static ConfigValue<Integer> cellDivisionSplitAmountPerLevel;
	
	//tables
	
	//ocean
	
	//nether
	
	//end
	
	//sculk
	
	//normal
	
    //extra
	public static ForgeConfigSpec.BooleanValue noEnchantCap;
	public static ForgeConfigSpec.BooleanValue disenchanting;
	public static ForgeConfigSpec.BooleanValue noIncreasingRepairCost;
    
    public static void loadConfig(ForgeConfigSpec config, String path) 
    {
        CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
    
    static 
    {
    	BUILDER = new ForgeConfigSpec.Builder();
    	EnchantmentConfig.init(EnchantmentConfig.BUILDER);
    	CONFIG = EnchantmentConfig.BUILDER.build();
    }
	
    public static void init(ForgeConfigSpec.Builder config) 
    {
    	config.push("Enchantment Settings");
    	EnchantmentConfig.waterboltMinCost = config.comment("minimum enchantability for waterbolt enchantment").define("waterboltMinCost", 25);
    	EnchantmentConfig.waterboltMaxCost = config.comment("maximum enchantability for waterbolt enchantment").define("waterboltMaxCost", 50);

    	EnchantmentConfig.mermaidsBlessingMinCost = config.comment("minimum enchantability for mermaid's blessing enchantment").define("mermaidsBlessingMinCost", 1);
    	EnchantmentConfig.mermaidsBlessingMaxCost = config.comment("maximum enchantability for mermaid's blessing enchantment").define("mermaidsBlessingMaxCost", 11);
    	
    	EnchantmentConfig.mermaidsBlessingMaxMobHealthPerLevel = config.comment("maximum value of mob health that affected by mermaid's blessing enchantment").define("mermaidsBlessingMaxMobHealthPerLevel", 10.0F);
    	EnchantmentConfig.mermaidsBlessingMaxMobDamagePerLevel = config.comment("maximum value of mob damage that affected by mermaid's blessing enchantment").define("mermaidsBlessingMaxMobDamagePerLevel", 2.0D);
    	
    	EnchantmentConfig.sharpWavesMinCost = config.comment("minimum enchantability for sharp waves enchantment").define("sharpWavesMinCost", 1);
    	EnchantmentConfig.sharpWavesMaxCost = config.comment("maximum enchantability for sharp waves enchantment").define("sharpWavesMaxCost", 11);
    	
    	EnchantmentConfig.sharpWavesMaxDamagePerLevel = config.comment("maximum additional damage for each level of sharp waves enchantment").define("sharpWavesMaxDamagePerLevel", 5.0F);
    	EnchantmentConfig.sharpWavesDistancePerLevel = config.comment("minimum distance required to increase damage for each level of sharp waves enchantment").define("sharpWavesDistancePerLevel", 200.0F);
    	EnchantmentConfig.sharpWavesDamagePerLevel = config.comment("additional damage amount when enough distance traveled for each level of sharp waves enchantment").define("sharpWavesDamagePerDistanceAndLevel", 2.0F);
    	
    	EnchantmentConfig.tideMinCost = config.comment("minimum enchantability for tide enchantment").define("tideMinCost", 1);
    	EnchantmentConfig.tideMaxCost = config.comment("maximum enchantability for tide enchantment").define("tideMaxCost", 11);
    	
    	EnchantmentConfig.tideMaxSpeedPerLevel = config.comment("maximum speed for each level of tide enchantment").define("tideMaxSpeedPerLevel", 0.07F);
    	EnchantmentConfig.tideSpeedPerLevel = config.comment("tide speed for each level of tide enchantment").define("tideSpeedPerLevel", 0.01F);
    	
    	EnchantmentConfig.poseidonsGraceMinCost = config.comment("minimum enchantability for poseidon's grace enchantment").define("poseidonsGraceMinCost", 1);
    	EnchantmentConfig.poseidonsGraceMaxCost = config.comment("maximum enchantability for poseidon's grace enchantment").define("poseidonsGraceMaxCost", 11);
    	
    	EnchantmentConfig.poseidonsGraceTridentSpeed = config.comment("speed multiplier of summoned trident for poseidon's grace enchantment").define("poseidonsGraceTridentSpeed", 1.3F);
    	
    	EnchantmentConfig.wavesProtectionMinCost = config.comment("minimum enchantability for wave's protection enchantment").define("wavesProtectionMinCost", 1);
    	EnchantmentConfig.wavesProtectionMaxCost = config.comment("maximum enchantability for wave's protection enchantment").define("wavesProtectionMaxCost", 11);
    	
    	EnchantmentConfig.wavesProtectionSpeedPerLevel = config.comment("effect speed for each level of wave's protection enchantment").define("wavesProtectionSpeedPerLevel", 0.1F);
    	EnchantmentConfig.wavesProtectionDurationPerLevel = config.comment("effect duration for each level of wave's protection enchantment").define("wavesProtectionDurationPerLevel", 1);
    	
    	EnchantmentConfig.lordoftheseaMinCost = config.comment("minimum enchantability for lord of the sea enchantment").define("lordoftheseaMinCost", 1);
    	EnchantmentConfig.lordoftheseaMaxCost = config.comment("maximum enchantability for lord of the sea enchantment").define("lordoftheseaMaxCost", 11);
    	
    	EnchantmentConfig.lordoftheseaRadiusPerLevel = config.comment("effect radius for each level of lord of the sea enchantment").define("lordoftheseaRadiusPerLevel", 2F);
    	EnchantmentConfig.lordoftheseaAnimalsDamagePerLevel = config.comment("attack damage of animals for each level of lord of the sea enchantment").define("lordoftheseaAnimalsDamagePerLevel", 1F);
    	EnchantmentConfig.lordoftheseaMaxAnimalsAmountPerLevel = config.comment("maximum value of animals that affected for each level of lord of the sea enchantment").define("lordoftheseaMaxAnimalsAmountPerLevel", 2);
    	
    	EnchantmentConfig.mermaidsCurseMinCost = config.comment("minimum enchantability for curse of mermaid enchantment").define("mermaidsCurseMinCost", 1);
    	EnchantmentConfig.mermaidsCurseMaxCost = config.comment("maximum enchantability for curse of mermaid enchantment").define("mermaidsCurseMaxCost", 11);
    	
    	EnchantmentConfig.mermaidsCurseRadiusPerLevel = config.comment("affect radius for each level of curse of mermaid enchantment").define("mermaidsCurseRadiusPerLevel", 7F);
    	
    	EnchantmentConfig.floatingCurseMinCost = config.comment("minimum enchantability for curse of floating enchantment").define("floatingCurseMinCost", 1);
    	EnchantmentConfig.floatingCurseMaxCost = config.comment("maximum enchantability for curse of floating enchantment").define("floatingCurseMaxCost", 11);
    	
    	EnchantmentConfig.floatingCurseFloatingSpeedPerLevel = config.comment("floating speed of per level for curse of floating enchantment").define("floatingCurseFloatingSpeedPerLevel", 0.05F);
    	
    	EnchantmentConfig.abyssCurseMinCost = config.comment("minimum enchantability for curse of abyss enchantment").define("abyssCurseMinCost", 1);
    	EnchantmentConfig.abyssCurseMaxCost = config.comment("maximum enchantability for curse of abyss enchantment").define("abyssCurseMaxCost", 11);
    	
    	EnchantmentConfig.abyssCurseFogDensityPerLevel = config.comment("fog density for each level of curse of abyss enchantment").define("abyssCurseFogDensityPerLevel", 5F);
    	
    	EnchantmentConfig.rustyMinCost = config.comment("minimum enchantability for rusty enchantment").define("rustyMinCost", 1);
    	EnchantmentConfig.rustyMaxCost = config.comment("maximum enchantability for rusty enchantment").define("rustyMaxCost", 11);
    	
    	EnchantmentConfig.rustyDecreaseIntervalPerLevel = config.comment("durability decrease interval for each level of rusty enchantment").define("rustyDecreaseIntervalPerLevel", 5.0F);
    	
    	EnchantmentConfig.overgravityMinCost = config.comment("minimum enchantability for overgravity enchantment").define("overgravityMinCost", 1);
    	EnchantmentConfig.overgravityMaxCost = config.comment("maximum enchantability for overgravity enchantment").define("overgravityMaxCost", 11);
    	
    	EnchantmentConfig.overgravityFallDamagePerLevel = config.comment("additional fall damage for each level of overgravity enchantment").define("overgravityFallDamagePerLevel", 1.0F);
    	EnchantmentConfig.overgravityFallSpeedPerLevel = config.comment("falling speed for each level of overgravity enchantment").define("overgravityFallSpeedPerLevel", 0.05F);

    	EnchantmentConfig.lavaWalkerMinCost = config.comment("minimum enchantability for lavawalker enchantment").define("lavaWalkerMinCost", 25);
    	EnchantmentConfig.lavaWalkerMaxCost = config.comment("maximum enchantability for lavawalker enchantment").define("lavaWalkerMaxCost", 50);
    	
    	EnchantmentConfig.autoSmeltMinCost = config.comment("minimum enchantability for auto smelt enchantment").define("autoSmeltMinCost", 25);
    	EnchantmentConfig.autoSmeltMaxCost = config.comment("maximum enchantability for auto smelt enchantment").define("autoSmeltMaxCost", 50);
    	
    	EnchantmentConfig.recochetMinCost = config.comment("minimum enchantability for recochet enchantment").define("recochetMinCost", 25);
    	EnchantmentConfig.recochetMaxCost = config.comment("maximum enchantability for recochet enchantment").define("recochetMaxCost", 50);
    	
    	EnchantmentConfig.recochetMaxBounce = config.comment("maximum bouncing count for recochet enchantment").define("recochetMaxBounce", 5);
    	
    	EnchantmentConfig.climbMinCost = config.comment("minimum enchantability for climb enchantment").define("climbMinCost", 1);
    	EnchantmentConfig.climbMaxCost = config.comment("maximum enchantability for climb enchantment").define("climbMaxCost", 11);
    	
    	EnchantmentConfig.climbSpeedPerLevel = config.comment("climb speed for each level of climb enchantment").define("climbSpeedPerLevel", 1.5D);
    	
    	EnchantmentConfig.criticalStrikeMinCost = config.comment("minimum enchantability for critical strike enchantment").define("criticalStrikeMinCost", 1);
    	EnchantmentConfig.criticalStrikeMaxCost = config.comment("maximum enchantability for critical strike enchantment").define("criticalStrikeMaxCost", 11);
    	
    	EnchantmentConfig.criticalStrikeChancePerLevel = config.comment("critical chance for each level of critical strike enchantment").define("criticalStrikeChancePerLevel", 10.0F);
    	
    	EnchantmentConfig.leechMinCost = config.comment("minimum enchantability for leech enchantment").define("leechMinCost", 1);
    	EnchantmentConfig.leechMaxCost = config.comment("maximum enchantability for leech enchantment").define("leechMaxCost", 11);
    	
    	EnchantmentConfig.leechHealAmountPerLevel = config.comment("heal amount for each level of leech enchantment").define("leechHealAmountPerLevel", 0.5F);
    	EnchantmentConfig.leechChancePerLevel = config.comment("heal change for each level of leech enchantment").define("leechChancePerLevel", 5.0F);
    	
    	EnchantmentConfig.wallbreakMinCost = config.comment("minimum enchantability for wallbreak enchantment").define("wallbreakMinCost", 25);
    	EnchantmentConfig.wallbreakMaxCost = config.comment("maximum enchantability for wallbreak enchantment").define("wallbreakMaxCost", 50);
    	
    	EnchantmentConfig.snipeMinCost = config.comment("minimum enchantability for snipe enchantment").define("snipeMinCost", 1);
    	EnchantmentConfig.snipeMaxCost = config.comment("maximum enchantability for snipe enchantment").define("snipeMaxCost", 11);
    	
    	EnchantmentConfig.snipeProjectileSpeedPerLevel = config.comment("speed of projectile for each level of snipe enchantment").define("snipeProjectileSpeedPerLevel", 10);
    	EnchantmentConfig.snipeAdditionalDamagePerLevel = config.comment("additional damage for each level of snipe enchantment").define("snipeAdditionalDamagePerLevel", 1F);
    	
    	EnchantmentConfig.magnetMinCost = config.comment("minimum enchantability for magnet enchantment").define("magnetMinCost", 1);
    	EnchantmentConfig.magnetMaxCost = config.comment("maximum enchantability for magnet enchantment").define("magnetMaxCost", 11);
    	
    	EnchantmentConfig.magnetRadiusPerLevel = config.comment("attract range for each level of magnet enchantment").define("magnetRadiusPerLevel", 3.0F);
    	
    	EnchantmentConfig.autoShieldingMinCost = config.comment("minimum enchantability for auto shielding enchantment").define("autoShieldingMinCost", 1);
    	EnchantmentConfig.autoShieldingMaxCost = config.comment("maximum enchantability for auto shielding enchantment").define("autoShieldingMaxCost", 11);
    	
    	EnchantmentConfig.autoShieldingChancePerLevel = config.comment("shielding change for each level of auto shielding enchantment").define("autoShieldingChancePerLevel", 5.0F);
    	
    	EnchantmentConfig.barrierMinCost = config.comment("minimum enchantability for barrier enchantment").define("barrierMinCost", 1);
    	EnchantmentConfig.barrierMaxCost = config.comment("maximum enchantability for barrier enchantment").define("barrierMaxCost", 11);
    	
    	EnchantmentConfig.barrierPushPowerPerLevel = config.comment("pushing power for each level of barrier enchantment").define("barrierPushPowerPerLevel", 0.01F);
    	EnchantmentConfig.barrierRadiusPerLevel = config.comment("pushing radius for each level of barrier enchantment").define("barrierRadiusPerLevel", 2.0F);
    	
    	EnchantmentConfig.disableBarrierPushingPlayer = config.comment("disable players pushed by barrier enchantment").define("disableBarrierPushingPlayer", true);
    	
    	EnchantmentConfig.skillfulMinCost = config.comment("minimum enchantability for skillful enchantment").define("skillfulMinCost", 1);
    	EnchantmentConfig.skillfulMaxCost = config.comment("maximum enchantability for skillful enchantment").define("skillfulMaxCost", 11);

    	EnchantmentConfig.skillfulMaxDamagePerLevel = config.comment("maximum additional damage for each level of skillful enchantment").define("skillfulMaxDamagePerLevel", 3.0F);
    	EnchantmentConfig.skillfulDurabilityPerLevel = config.comment("minimum durability required to increase damage for each level of skillful enchantment").define("skillfulDurabilityPerLevel", 900.0F);
    	EnchantmentConfig.skillfulDamagePerLevel = config.comment("additional damage when enough durability used for each level of skillful enchantment").define("skillfulDamagePerLevel", 2.0F);
    	
    	EnchantmentConfig.cellDivisionMinCost = config.comment("minimum enchantability for cell division enchantment").define("cellDivisionMinCost", 1);
    	EnchantmentConfig.cellDivisionMaxCost = config.comment("maximum enchantability for cell division enchantment").define("cellDivisionMaxCost", 11);

    	EnchantmentConfig.cellDivisionScalePerSplit = config.comment("scale decrease amount for each split of cell division enchantment").define("cellDivisionScalePerSplit", 0.1F);
    	EnchantmentConfig.cellDivisionMaxSplitPerLevel = config.comment("maximum split amount for each level of cell division enchantment").define("cellDivisionMaxSplitPerLevel", 1);
    	EnchantmentConfig.cellDivisionSplitAmountPerLevel = config.comment("amount for additional projectile of each split for each level of cell division enchantment").define("cellDivisionSplitAmountPerLevel", 2);
    	
        config.pop();
        
    	config.push("Table Settings");
        config.pop();
        
    	config.push("Extra Settings");
    	EnchantmentConfig.noEnchantCap = config.comment("you can combine any enchantment in anvil").define("noEnchantCap", true);
    	EnchantmentConfig.disenchanting = config.comment("you can disenchanting with vanilla book").define("disenchanting", true);
    	EnchantmentConfig.noIncreasingRepairCost = config.comment("remove repair cost increasing").define("noIncreasingRepairCost", true);
        config.pop();
    }
}
