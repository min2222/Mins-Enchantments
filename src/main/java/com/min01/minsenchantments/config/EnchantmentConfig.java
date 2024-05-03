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
    
    //blessment
	public static ConfigValue<Integer> barrierMinCost;
	public static ConfigValue<Integer> barrierMaxCost;
	
	public static ConfigValue<Float> barrierRadiusPerLevel;
	public static ConfigValue<Float> barrierPushPowerPerLevel;
	
	public static ForgeConfigSpec.BooleanValue disableBarrierPushingPlayer;
	
	public static ConfigValue<Integer> hardeningMinCost;
	public static ConfigValue<Integer> hardeningMaxCost;
	
	public static ConfigValue<Float> hardeningArmorPerLevel;
	public static ConfigValue<Float> hardeningMaxArmorPerLevel;
	
	public static ConfigValue<Integer> rageMinCost;
	public static ConfigValue<Integer> rageMaxCost;
	
	public static ConfigValue<Float> rageDamagePerLevel;
	public static ConfigValue<Float> rageMaxDamagePerLevel;
	
	public static ConfigValue<Integer> returningMinCost;
	public static ConfigValue<Integer> returningMaxCost;
	
	public static ConfigValue<Integer> returningDurabilityPerLevel;
	
	public static ConfigValue<Integer> homingMinCost;
	public static ConfigValue<Integer> homingMaxCost;
	
	public static ConfigValue<Float> homingMaxHomingTimePerLevel;
	public static ForgeConfigSpec.BooleanValue disableHomingPlayers;
	
	public static ConfigValue<Integer> mirrorMinCost;
	public static ConfigValue<Integer> mirrorMaxCost;
	
	public static ConfigValue<Float> mirrorReflectChancePerLevel;
	
	public static ConfigValue<Integer> soulOfTerrarianMinCost;
	public static ConfigValue<Integer> soulOfTerrarianMaxCost;
	
	public static ConfigValue<Integer> soulOfTerrarianProjAmountPerLevel;
	
	public static ConfigValue<Integer> godHandMinCost;
	public static ConfigValue<Integer> godHandMaxCost;
	
	public static ConfigValue<Integer> godHandCooldownPerLevel;
	
	public static ConfigValue<Integer> maliceMinCost;
	public static ConfigValue<Integer> maliceMaxCost;

	public static ConfigValue<Float> maliceDamagePerLevel;
	public static ConfigValue<Float> malicePercentagePerLevel;
	
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
	
	public static ConfigValue<Integer> sailingMinCost;
	public static ConfigValue<Integer> sailingMaxCost;
	
	public static ConfigValue<Float> sailingSpeedPerLevel;
	
	public static ConfigValue<Integer> waterJetMinCost;
	public static ConfigValue<Integer> waterJetMaxCost;
	
	public static ConfigValue<Integer> aquaticAuraMinCost;
	public static ConfigValue<Integer> aquaticAuraMaxCost;
	
	public static ConfigValue<Float> aquaticAuraRadiusPerLevel;
	public static ForgeConfigSpec.BooleanValue disableAquaticAuraToPlayer;
	
	//curse
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
	
	public static ConfigValue<Integer> undeadCurseMinCost;
	public static ConfigValue<Integer> undeadCurseMaxCost;
	
	public static ConfigValue<Integer> undeadCurseFireDurationPerLevel;
	
	public static ConfigValue<Integer> endermanCurseMinCost;
	public static ConfigValue<Integer> endermanCurseMaxCost;
	
	public static ConfigValue<Float> endermanCurseDamagePerLevel;
	
	public static ConfigValue<Integer> sinkingCurseMinCost;
	public static ConfigValue<Integer> sinkingCurseMaxCost;
	
	public static ConfigValue<Float> sinkingCurseSpeedPerLevel;
	
	//nether
	public static ConfigValue<Integer> lavaWalkerMinCost;
	public static ConfigValue<Integer> lavaWalkerMaxCost;
	
	public static ConfigValue<Integer> autoSmeltMinCost;
	public static ConfigValue<Integer> autoSmeltMaxCost;
	
	public static ConfigValue<Integer> flameThornMinCost;
	public static ConfigValue<Integer> flameThornMaxCost;	
	
	public static ConfigValue<Float> flameThornDamagePerLevel;
	public static ConfigValue<Integer> flameThornFireDurationPerLevel;
	
	public static ConfigValue<Integer> dryMinCost;
	public static ConfigValue<Integer> dryMaxCost;
	
	public static ConfigValue<Integer> dryRadiusPerLevel;
	
	public static ConfigValue<Integer> soulFireMinCost;
	public static ConfigValue<Integer> soulFireMaxCost;
	
	public static ConfigValue<Integer> soulFireDurationPerLevel;
	
	//end
	public static ConfigValue<Integer> teleportationMinCost;
	public static ConfigValue<Integer> teleportationMaxCost;
	
	//sculk
	public static ConfigValue<Integer> sonicBoomMinCost;
	public static ConfigValue<Integer> sonicBoomMaxCost;
	
	public static ConfigValue<Float> sonicBoomDamagePerLevel;
	public static ConfigValue<Float> sonicBoomDistancePerLevel;
	
	public static ConfigValue<Integer> infectionMinCost;
	public static ConfigValue<Integer> infectionMaxCost;
	
	public static ConfigValue<Integer> infectionExpPerLevel;
	
	public static ConfigValue<Integer> concealmentMinCost;
	public static ConfigValue<Integer> concealmentMaxCost;
	
	//normal
	public static ConfigValue<Integer> recochetMinCost;
	public static ConfigValue<Integer> recochetMaxCost;
	
	public static ConfigValue<Integer> recochetBouncePerLevel;
	
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
	public static ConfigValue<Float> snipeChargeSpeedPerLevel;
	
	public static ConfigValue<Integer> magnetMinCost;
	public static ConfigValue<Integer> magnetMaxCost;
	
	public static ConfigValue<Float> magnetRadiusPerLevel;
	
	public static ConfigValue<Integer> autoShieldingMinCost;
	public static ConfigValue<Integer> autoShieldingMaxCost;
	
	public static ConfigValue<Float> autoShieldingChancePerLevel;
	
	public static ConfigValue<Integer> skillfulMinCost;
	public static ConfigValue<Integer> skillfulMaxCost;
	
	public static ConfigValue<Float> skillfulMaxDamagePerLevel;
	public static ConfigValue<Float> skillfulMaxSpeedPerLevel;
	public static ConfigValue<Integer> skillfulDurabilityPerLevel;
	public static ConfigValue<Float> skillfulSpeedPerLevel;
	public static ConfigValue<Float> skillfulDamagePerLevel;
	
	public static ConfigValue<Integer> cellDivisionMinCost;
	public static ConfigValue<Integer> cellDivisionMaxCost;

	public static ConfigValue<Float> cellDivisionScalePerSplit;
	public static ConfigValue<Integer> cellDivisionMaxSplitPerLevel;
	public static ConfigValue<Integer> cellDivisionSplitAmountPerLevel;
	
	public static ConfigValue<Integer> quickdrawMinCost;
	public static ConfigValue<Integer> quickdrawMaxCost;
	
	public static ConfigValue<Integer> takeoffMinCost;
	public static ConfigValue<Integer> takeoffMaxCost;
	
	public static ConfigValue<Integer> armorCrackMinCost;
	public static ConfigValue<Integer> armorCrackMaxCost;
	
	public static ConfigValue<Float> armorCrackDamagePerLevel;
	
	public static ConfigValue<Integer> accelerateMinCost;
	public static ConfigValue<Integer> accelerateMaxCost;
	
	public static ConfigValue<Float> accelerateMaxSpeedPerLevel;
	public static ConfigValue<Float> accelerateSpeedPerLevel;
	
	public static ConfigValue<Integer> minerMinCost;
	public static ConfigValue<Integer> minerMaxCost;
	
	public static ConfigValue<Integer> minerMaxBlockPerLevel;
	
	//tables
	
	//blessment
	
	//ocean
	
	//nether
	
	//end
	
	//sculk
	
	//normal
	
    //extra
	public static ForgeConfigSpec.BooleanValue noEnchantCap;
	public static ForgeConfigSpec.BooleanValue noIncreasingRepairCost;
	public static ForgeConfigSpec.BooleanValue anvilOverlevelBooks;
	public static ForgeConfigSpec.BooleanValue anvilAlwaysAllowBooks;
	public static ForgeConfigSpec.BooleanValue disenchanting;
    
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
    	config.push("Blessment Settings");
    	EnchantmentConfig.barrierMinCost = config.comment("minimum enchantability for barrier enchantment").define("barrierMinCost", 1);
    	EnchantmentConfig.barrierMaxCost = config.comment("maximum enchantability for barrier enchantment").define("barrierMaxCost", 11);
    	
    	EnchantmentConfig.barrierPushPowerPerLevel = config.comment("pushing power for each level of barrier enchantment").define("barrierPushPowerPerLevel", 0.01F);
    	EnchantmentConfig.barrierRadiusPerLevel = config.comment("pushing radius for each level of barrier enchantment").define("barrierRadiusPerLevel", 2.0F);
    	
    	EnchantmentConfig.disableBarrierPushingPlayer = config.comment("disable players pushed by barrier enchantment").define("disableBarrierPushingPlayer", true);
    	
    	EnchantmentConfig.hardeningMinCost = config.comment("minimum enchantability for hardening blessment").define("hardeningMinCost", 1);
    	EnchantmentConfig.hardeningMaxCost = config.comment("maximum enchantability for hardening blessment").define("hardeningMaxCost", 11);

    	EnchantmentConfig.hardeningArmorPerLevel = config.comment("armor increase amount for each level of hardening blessment").define("hardeningArmorPerLevel", 0.05F);
    	EnchantmentConfig.hardeningMaxArmorPerLevel = config.comment("maximum armor point for each level of hardening blessment").define("hardeningMaxArmorPerLevel", 2.0F);
    	
    	EnchantmentConfig.rageMinCost = config.comment("minimum enchantability for rage blessment").define("rageMinCost", 1);
    	EnchantmentConfig.rageMaxCost = config.comment("maximum enchantability for rage blessment").define("rageMaxCost", 11);
    	
    	EnchantmentConfig.rageDamagePerLevel = config.comment("damage increase amount for each level of rage blessment").define("rageDamagePerLevel", 0.05F);
    	EnchantmentConfig.rageMaxDamagePerLevel = config.comment("maximum damage for each level of rage blessment").define("rageMaxDamagePerLevel", 2.0F);
    	
    	EnchantmentConfig.returningMinCost = config.comment("minimum enchantability for returning blessment").define("returningMinCost", 1);
    	EnchantmentConfig.returningMaxCost = config.comment("maximum enchantability for returning blessment").define("returningMaxCost", 11);
    	
    	EnchantmentConfig.returningDurabilityPerLevel = config.comment("minimum durability required for each level of returning blessment").define("returningDurabilityPerLevel", 1000);
    	
    	EnchantmentConfig.homingMinCost = config.comment("minimum enchantability for homing blessment").define("homingMinCost", 1);
    	EnchantmentConfig.homingMaxCost = config.comment("maximum enchantability for homing blessment").define("homingMaxCost", 11);
    	
    	EnchantmentConfig.homingMaxHomingTimePerLevel = config.comment("maximum homing time for each level of homing blessment").define("homingMaxHomingTimePerLevel", 3.0F);
    	EnchantmentConfig.disableHomingPlayers = config.comment("disable homing blessment work with other players").define("disableHomingPlayers", true);
    	
    	EnchantmentConfig.mirrorMinCost = config.comment("minimum enchantability for mirror blessment").define("mirrorMinCost", 1);
    	EnchantmentConfig.mirrorMaxCost = config.comment("maximum enchantability for mirror blessment").define("mirrorMaxCost", 11);
    	
    	EnchantmentConfig.mirrorReflectChancePerLevel = config.comment("chance to reflect damage and projectile for each level of mirror blessment").define("mirrorReflectChancePerLevel", 20.0F);
    	
    	EnchantmentConfig.soulOfTerrarianMinCost = config.comment("minimum enchantability for soul of terrarian blessment").define("soulOfTerrarianMinCost", 1);
    	EnchantmentConfig.soulOfTerrarianMaxCost = config.comment("maximum enchantability for soul of terrarian blessment").define("soulOfTerrarianMaxCost", 11);
    	
    	EnchantmentConfig.soulOfTerrarianProjAmountPerLevel = config.comment("additional projectile amount for each level of soul of terrarian blessment").define("soulOfTerrarianProjAmountPerLevel", 2);
    	
    	EnchantmentConfig.godHandMinCost = config.comment("minimum enchantability for god hand blessment").define("godHandMinCost", 1);
    	EnchantmentConfig.godHandMaxCost = config.comment("maximum enchantability for god hand blessment").define("godHandMaxCost", 11);
    	
    	EnchantmentConfig.godHandCooldownPerLevel = config.comment("cooldown decrease amount for each level of god hand blessment").define("godHandCooldownPerLevel", 1);
    	
    	EnchantmentConfig.maliceMinCost = config.comment("minimum enchantability for malice blessment").define("maliceMinCost", 1);
    	EnchantmentConfig.maliceMaxCost = config.comment("maximum enchantability for malice blessment").define("maliceMaxCost", 11);
    	
    	EnchantmentConfig.maliceDamagePerLevel = config.comment("damage decrease amount for each level of malice blessment").define("maliceDamagePerLevel", 2.0F);
    	EnchantmentConfig.malicePercentagePerLevel = config.comment("health percentage for each level of malice blessment").define("malicePercentagePerLevel", 0.4F);
    	
    	config.pop();
    	
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
    	EnchantmentConfig.sharpWavesDistancePerLevel = config.comment("minimum distance required to increase damage for each level of sharp waves enchantment").define("sharpWavesDistancePerLevel", 100.0F);
    	EnchantmentConfig.sharpWavesDamagePerLevel = config.comment("additional damage amount when enough distance traveled for each level of sharp waves enchantment").define("sharpWavesDamagePerDistanceAndLevel", 0.5F);
    	
    	EnchantmentConfig.tideMinCost = config.comment("minimum enchantability for tide enchantment").define("tideMinCost", 1);
    	EnchantmentConfig.tideMaxCost = config.comment("maximum enchantability for tide enchantment").define("tideMaxCost", 11);
    	
    	EnchantmentConfig.tideMaxSpeedPerLevel = config.comment("maximum speed for each level of tide enchantment").define("tideMaxSpeedPerLevel", 0.01F);
    	EnchantmentConfig.tideSpeedPerLevel = config.comment("tide speed for each level of tide enchantment").define("tideSpeedPerLevel", 0.002F);
    	
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
    	
    	EnchantmentConfig.sailingMinCost = config.comment("minimum enchantability for sailing enchantment").define("sailingMinCost", 1);
    	EnchantmentConfig.sailingMaxCost = config.comment("maximum enchantability for sailing enchantment").define("sailingMaxCost", 11);
    	
    	EnchantmentConfig.sailingSpeedPerLevel = config.comment("additional speed of vehicle for each level of sailing enchantment").define("sailingSpeedPerLevel", 0.1F);
    	
    	EnchantmentConfig.waterJetMinCost = config.comment("minimum enchantability for water jet enchantment").define("waterJetMinCost", 25);
    	EnchantmentConfig.waterJetMaxCost = config.comment("maximum enchantability for water jet enchantment").define("waterJetMaxCost", 50);
    	
    	EnchantmentConfig.aquaticAuraMinCost = config.comment("minimum enchantability for aquatic aura enchantment").define("aquaticAuraMinCost", 1);
    	EnchantmentConfig.aquaticAuraMaxCost = config.comment("maximum enchantability for aquatic aura enchantment").define("aquaticAuraMaxCost", 11);
    	
    	EnchantmentConfig.aquaticAuraRadiusPerLevel = config.comment("effect radius for each level of aquatic aura enchantment").define("aquaticAuraRadiusPerLevel", 3.0F);
    	EnchantmentConfig.disableAquaticAuraToPlayer = config.comment("disable players affected by aquatic aura enchantment").define("disableAquaticAuraToPlayer", true);
    	
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
    	
    	EnchantmentConfig.undeadCurseMinCost = config.comment("minimum enchantability for curse of undead enchantment").define("undeadCurseMinCost", 1);
    	EnchantmentConfig.undeadCurseMaxCost = config.comment("maximum enchantability for curse of undead enchantment").define("undeadCurseMaxCost", 11);
    	
    	EnchantmentConfig.undeadCurseFireDurationPerLevel = config.comment("fire duration for each level of curse of undead enchantment").define("undeadCurseFireDurationPerLevel", 5);
    	
    	EnchantmentConfig.endermanCurseMinCost = config.comment("minimum enchantability for curse of enderman enchantment").define("endermanCurseMinCost", 1);
    	EnchantmentConfig.endermanCurseMaxCost = config.comment("maximum enchantability for curse of enderman enchantment").define("endermanCurseMaxCost", 11);
    	
    	EnchantmentConfig.endermanCurseDamagePerLevel = config.comment("damage amount for each level of curse of enderman enchantment").define("endermanCurseDamagePerLevel", 0.2F);
    	
    	EnchantmentConfig.sinkingCurseMinCost = config.comment("minimum enchantability for curse of sinking enchantment").define("sinkingCurseMinCost", 1);
    	EnchantmentConfig.sinkingCurseMaxCost = config.comment("maximum enchantability for curse of sinking enchantment").define("sinkingCurseMaxCost", 11);
    	
    	EnchantmentConfig.sinkingCurseSpeedPerLevel = config.comment("sinking speed for each level of curse of sinking enchantment").define("sinkingCurseSpeedPerLevel", 0.05F);

    	EnchantmentConfig.lavaWalkerMinCost = config.comment("minimum enchantability for lavawalker enchantment").define("lavaWalkerMinCost", 25);
    	EnchantmentConfig.lavaWalkerMaxCost = config.comment("maximum enchantability for lavawalker enchantment").define("lavaWalkerMaxCost", 50);
    	
    	EnchantmentConfig.autoSmeltMinCost = config.comment("minimum enchantability for auto smelt enchantment").define("autoSmeltMinCost", 25);
    	EnchantmentConfig.autoSmeltMaxCost = config.comment("maximum enchantability for auto smelt enchantment").define("autoSmeltMaxCost", 50);
    	
    	EnchantmentConfig.flameThornMinCost = config.comment("minimum enchantability for flame thorn enchantment").define("flameThornMinCost", 1);
    	EnchantmentConfig.flameThornMaxCost = config.comment("maximum enchantability for flame thorn enchantment").define("flameThornMaxCost", 11);
    	
    	EnchantmentConfig.flameThornDamagePerLevel = config.comment("thorn damage for each level of flame thorn enchantment").define("flameThornDamagePerLevel", 1.5F);
    	EnchantmentConfig.flameThornFireDurationPerLevel = config.comment("fire duration for each level of flame thorn enchantment").define("flameThornFireDurationPerLevel", 1);
    	
    	EnchantmentConfig.dryMinCost = config.comment("minimum enchantability for dry enchantment").define("dryMinCost", 1);
    	EnchantmentConfig.dryMaxCost = config.comment("maximum enchantability for dry enchantment").define("dryMaxCost", 11);
    	
    	EnchantmentConfig.dryRadiusPerLevel = config.comment("absorb radius for each level of dry enchantment").define("dryRadiusPerLevel", 5);
    	
    	EnchantmentConfig.soulFireMinCost = config.comment("minimum enchantability for soul fire enchantment").define("soulFireMinCost", 1);
    	EnchantmentConfig.soulFireMaxCost = config.comment("maximum enchantability for soul fire enchantment").define("soulFireMaxCost", 11);
    	
    	EnchantmentConfig.soulFireDurationPerLevel = config.comment("fire duration for each level of soul fire enchantment").define("soulFireDurationPerLevel", 1);
    	
    	EnchantmentConfig.teleportationMinCost = config.comment("minimum enchantability for teleportation enchantment").define("teleportationMinCost", 25);
    	EnchantmentConfig.teleportationMaxCost = config.comment("maximum enchantability for teleportation enchantment").define("teleportationMaxCost", 50);
    	
    	EnchantmentConfig.sonicBoomMinCost = config.comment("minimum enchantability for sonic boom enchantment").define("sonicBoomMinCost", 1);
    	EnchantmentConfig.sonicBoomMaxCost = config.comment("maximum enchantability for sonic boom enchantment").define("sonicBoomMaxCost", 11);
    	
    	EnchantmentConfig.sonicBoomDamagePerLevel = config.comment("damage for each level of sonic boom enchantment").define("sonicBoomDamagePerLevel", 1.0F);
    	EnchantmentConfig.sonicBoomDistancePerLevel = config.comment("distance for each level of sonic boom enchantment").define("sonicBoomDistancePerLevel", 2.0F);
    	
    	EnchantmentConfig.infectionMinCost = config.comment("minimum enchantability for infection enchantment").define("infectionMinCost", 1);
    	EnchantmentConfig.infectionMaxCost = config.comment("maximum enchantability for infection enchantment").define("infectionMaxCost", 11);
    	
    	EnchantmentConfig.infectionExpPerLevel = config.comment("additional exp orb count for each level of infection enchantment").define("infectionExpPerLevel", 1);
    	
    	EnchantmentConfig.concealmentMinCost = config.comment("minimum enchantability for concealment enchantment").define("concealmentMinCost", 25);
    	EnchantmentConfig.concealmentMaxCost = config.comment("maximum enchantability for concealment enchantment").define("concealmentMaxCost", 50);
    	
    	EnchantmentConfig.recochetMinCost = config.comment("minimum enchantability for recochet enchantment").define("recochetMinCost", 1);
    	EnchantmentConfig.recochetMaxCost = config.comment("maximum enchantability for recochet enchantment").define("recochetMaxCost", 11);
    	
    	EnchantmentConfig.recochetBouncePerLevel = config.comment("bouncing count for each level of recochet enchantment").define("recochetBouncePerLevel", 2);
    	
    	EnchantmentConfig.climbMinCost = config.comment("minimum enchantability for climb enchantment").define("climbMinCost", 1);
    	EnchantmentConfig.climbMaxCost = config.comment("maximum enchantability for climb enchantment").define("climbMaxCost", 11);
    	
    	EnchantmentConfig.climbSpeedPerLevel = config.comment("climb speed for each level of climb enchantment").define("climbSpeedPerLevel", 0.05D);
    	
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
    	EnchantmentConfig.snipeAdditionalDamagePerLevel = config.comment("additional damage for each level of snipe enchantment").define("snipeAdditionalDamagePerLevel", 5F);
    	EnchantmentConfig.snipeChargeSpeedPerLevel = config.comment("decrease amount of charge speed for each level of snipe enchantment").define("snipeChargeSpeedPerLevel", 1F);
    	
    	EnchantmentConfig.magnetMinCost = config.comment("minimum enchantability for magnet enchantment").define("magnetMinCost", 1);
    	EnchantmentConfig.magnetMaxCost = config.comment("maximum enchantability for magnet enchantment").define("magnetMaxCost", 11);
    	
    	EnchantmentConfig.magnetRadiusPerLevel = config.comment("attract range for each level of magnet enchantment").define("magnetRadiusPerLevel", 3.0F);
    	
    	EnchantmentConfig.autoShieldingMinCost = config.comment("minimum enchantability for auto shielding enchantment").define("autoShieldingMinCost", 1);
    	EnchantmentConfig.autoShieldingMaxCost = config.comment("maximum enchantability for auto shielding enchantment").define("autoShieldingMaxCost", 11);
    	
    	EnchantmentConfig.autoShieldingChancePerLevel = config.comment("shielding change for each level of auto shielding enchantment").define("autoShieldingChancePerLevel", 5.0F);
    	
    	EnchantmentConfig.skillfulMinCost = config.comment("minimum enchantability for skillful enchantment").define("skillfulMinCost", 1);
    	EnchantmentConfig.skillfulMaxCost = config.comment("maximum enchantability for skillful enchantment").define("skillfulMaxCost", 11);

    	EnchantmentConfig.skillfulMaxDamagePerLevel = config.comment("maximum additional damage for each level of skillful enchantment").define("skillfulMaxDamagePerLevel", 3.0F);
    	EnchantmentConfig.skillfulMaxSpeedPerLevel = config.comment("maximum additional mining speed for each level of skillful enchantment").define("skillfulMaxSpeedPerLevel", 3.0F);
    	EnchantmentConfig.skillfulDurabilityPerLevel = config.comment("minimum durability required to increase damage for each level of skillful enchantment").define("skillfulDurabilityPerLevel", 900);
    	EnchantmentConfig.skillfulDamagePerLevel = config.comment("additional damage when enough durability used for each level of skillful enchantment").define("skillfulDamagePerLevel", 2.0F);
    	EnchantmentConfig.skillfulSpeedPerLevel = config.comment("additional mining speed when enough durability used for each level of skillful enchantment").define("skillfulSpeedPerLevel", 2.0F);
    	
    	EnchantmentConfig.cellDivisionMinCost = config.comment("minimum enchantability for cell division enchantment").define("cellDivisionMinCost", 1);
    	EnchantmentConfig.cellDivisionMaxCost = config.comment("maximum enchantability for cell division enchantment").define("cellDivisionMaxCost", 11);

    	EnchantmentConfig.cellDivisionScalePerSplit = config.comment("scale decrease amount for each split of cell division enchantment").define("cellDivisionScalePerSplit", 0.3F);
    	EnchantmentConfig.cellDivisionMaxSplitPerLevel = config.comment("maximum split amount for each level of cell division enchantment").define("cellDivisionMaxSplitPerLevel", 1);
    	EnchantmentConfig.cellDivisionSplitAmountPerLevel = config.comment("amount for additional projectile of each split for each level of cell division enchantment").define("cellDivisionSplitAmountPerLevel", 1);

    	EnchantmentConfig.quickdrawMinCost = config.comment("minimum enchantability for quickdraw enchantment").define("quickdrawMinCost", 1);
    	EnchantmentConfig.quickdrawMaxCost = config.comment("maximum enchantability for quickdraw enchantment").define("quickdrawMaxCost", 11);
    	
    	EnchantmentConfig.takeoffMinCost = config.comment("minimum enchantability for takeoff enchantment").define("takeoffMinCost", 25);
    	EnchantmentConfig.takeoffMaxCost = config.comment("maximum enchantability for takeoff enchantment").define("takeoffMaxCost", 50);
    	
    	EnchantmentConfig.armorCrackMinCost = config.comment("minimum enchantability for armor crack enchantment").define("armorCrackMinCost", 1);
    	EnchantmentConfig.armorCrackMaxCost = config.comment("maximum enchantability for armor crack enchantment").define("armorCrackMaxCost", 11);
    	
    	EnchantmentConfig.armorCrackDamagePerLevel = config.comment("additional damage percentage for each level of armor crack enchantment").define("armorCrackDamagePerLevel", 10.0F);
    	
    	EnchantmentConfig.accelerateMinCost = config.comment("minimum enchantability for accelerate enchantment").define("accelerateMinCost", 1);
    	EnchantmentConfig.accelerateMaxCost = config.comment("maximum enchantability for accelerate enchantment").define("accelerateMaxCost", 11);
    	
    	EnchantmentConfig.accelerateMaxSpeedPerLevel = config.comment("maximum attack speed for each level of accelerate enchantment").define("accelerateMaxSpeedPerLevel", 0.1F);
    	EnchantmentConfig.accelerateSpeedPerLevel = config.comment("attack speed increase amount of each hit for each level of accelerate enchantment").define("accelerateSpeedPerLevel", 0.03F);
    	
    	EnchantmentConfig.minerMinCost = config.comment("minimum enchantability for accelerate enchantment").define("minerMinCost", 1);
    	EnchantmentConfig.minerMaxCost = config.comment("maximum enchantability for accelerate enchantment").define("minerMaxCost", 11);
    	
    	EnchantmentConfig.minerMaxBlockPerLevel = config.comment("maximum block break count for each level of miner enchantment").define("minerMaxBlockPerLevel", 1);
    	
        config.pop();
        
    	config.push("Table Settings");
        config.pop();
        
    	config.push("Extra Settings");
    	EnchantmentConfig.noEnchantCap = config.comment("able to combine any enchantments in anvil").define("noEnchantCap", true);
    	EnchantmentConfig.noIncreasingRepairCost = config.comment("remove repair cost increasing to prevent too expensive message").define("noIncreasingRepairCost", true);
    	EnchantmentConfig.anvilOverlevelBooks = config.comment("able to merge enchants to get enchant with higher level").define("anvilOverlevelBooks", true);
    	EnchantmentConfig.anvilAlwaysAllowBooks = config.comment("able to enchant anything that max stack is 1").define("anvilAlwaysAllowBooks", true);
    	EnchantmentConfig.disenchanting = config.comment("able to disenchanting with vanilla book").define("disenchanting", true);
        config.pop();
    }
}
