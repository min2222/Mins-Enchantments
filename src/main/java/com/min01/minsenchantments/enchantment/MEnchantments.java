package com.min01.minsenchantments.enchantment;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.enchantment.bless.AccelerateBlessment;
import com.min01.minsenchantments.enchantment.bless.ResoluteWardBlessment;
import com.min01.minsenchantments.enchantment.bless.BarrierBlessment;
import com.min01.minsenchantments.enchantment.bless.CriticalStrikeBlessment;
import com.min01.minsenchantments.enchantment.bless.MaliceBlessment;
import com.min01.minsenchantments.enchantment.bless.MasterTouchBlessment;
import com.min01.minsenchantments.enchantment.bless.MirrorBlessment;
import com.min01.minsenchantments.enchantment.bless.TerrarianSoulBlessment;
import com.min01.minsenchantments.enchantment.bless.TimeBreakBlessment;
import com.min01.minsenchantments.enchantment.end.ChorusEvasionEnchantment;
import com.min01.minsenchantments.enchantment.end.ChorusPiercingEnchantment;
import com.min01.minsenchantments.enchantment.end.DimensionalAnchorEnchantment;
import com.min01.minsenchantments.enchantment.end.EnderGazeEnchantment;
import com.min01.minsenchantments.enchantment.end.GazeAversionEnchantment;
import com.min01.minsenchantments.enchantment.end.GravityStabilityEnchantment;
import com.min01.minsenchantments.enchantment.end.ObsidianShatterEnchantment;
import com.min01.minsenchantments.enchantment.end.ShulkerStanceEnchantment;
import com.min01.minsenchantments.enchantment.end.UpwardStrikeEnchantment;
import com.min01.minsenchantments.enchantment.end.VoidSnipeEnchantment;
import com.min01.minsenchantments.enchantment.nether.CoreDestructionEnchantment;
import com.min01.minsenchantments.enchantment.nether.HellbaneEnchantment;
import com.min01.minsenchantments.enchantment.nether.HellfireEnchantment;
import com.min01.minsenchantments.enchantment.nether.InfernalBarrageEnchantment;
import com.min01.minsenchantments.enchantment.nether.ObsidianAegisEnchantment;
import com.min01.minsenchantments.enchantment.nether.PiglinDeceptionEnchantment;
import com.min01.minsenchantments.enchantment.nether.PyroMendingEnchantment;
import com.min01.minsenchantments.enchantment.nether.SoulFireAspectEnchantment;
import com.min01.minsenchantments.enchantment.nether.WitherProtectionEnchantment;
import com.min01.minsenchantments.enchantment.ocean.BubbleGuardEnchantment;
import com.min01.minsenchantments.enchantment.ocean.DepthAnglerEnchantment;
import com.min01.minsenchantments.enchantment.ocean.HydrodynamicsEnchantment;
import com.min01.minsenchantments.enchantment.ocean.SinkingStrikeEnchantment;
import com.min01.minsenchantments.enchantment.ocean.UpstreamEnchantment;
import com.min01.minsenchantments.enchantment.sculk.CatalystAbsorptionEnchantment;
import com.min01.minsenchantments.enchantment.sculk.LifeErosionEnchantment;
import com.min01.minsenchantments.enchantment.sculk.SculkAdaptationEnchantment;
import com.min01.minsenchantments.enchantment.sculk.SilentStepEnchantment;
import com.min01.minsenchantments.enchantment.sculk.SilentTouchEnchantment;
import com.min01.minsenchantments.enchantment.sculk.SonicCleaveEnchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MEnchantments 
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MinsEnchantments.MODID);

	//ocean
	public static final RegistryObject<MEnchantment> HYDRODYNAMICS = ENCHANTMENTS.register("hydrodynamics", () -> new HydrodynamicsEnchantment());
	public static final RegistryObject<MEnchantment> BUBBLE_GUARD = ENCHANTMENTS.register("bubble_guard", () -> new BubbleGuardEnchantment());
	public static final RegistryObject<MEnchantment> DEPTH_ANGLER = ENCHANTMENTS.register("depth_angler", () -> new DepthAnglerEnchantment());
	public static final RegistryObject<MEnchantment> UPSTREAM = ENCHANTMENTS.register("upstream", () -> new UpstreamEnchantment());
	public static final RegistryObject<MEnchantment> SINKING_STRIKE = ENCHANTMENTS.register("sinking_strike", () -> new SinkingStrikeEnchantment());
	
	//nether
	public static final RegistryObject<MEnchantment> PIGLIN_DECEPTION = ENCHANTMENTS.register("piglin_deception", () -> new PiglinDeceptionEnchantment());
	public static final RegistryObject<MEnchantment> HELLBANE = ENCHANTMENTS.register("hellbane", () -> new HellbaneEnchantment());
	public static final RegistryObject<MEnchantment> SOUL_FIRE_ASPECT = ENCHANTMENTS.register("soul_fire_aspect", () -> new SoulFireAspectEnchantment());
	public static final RegistryObject<MEnchantment> CORE_DESTRUCTION = ENCHANTMENTS.register("core_destruction", () -> new CoreDestructionEnchantment());
	public static final RegistryObject<MEnchantment> WITHER_PROTECTION = ENCHANTMENTS.register("wither_protection", () -> new WitherProtectionEnchantment());
	public static final RegistryObject<MEnchantment> PYRO_MENDING = ENCHANTMENTS.register("pyro_mending", () -> new PyroMendingEnchantment());
	public static final RegistryObject<MEnchantment> HELLFIRE = ENCHANTMENTS.register("hellfire", () -> new HellfireEnchantment());
	public static final RegistryObject<MEnchantment> OBSIDIAN_AEGIS = ENCHANTMENTS.register("obsidian_aegis", () -> new ObsidianAegisEnchantment());
	public static final RegistryObject<MEnchantment> INFERNAL_BARRAGE = ENCHANTMENTS.register("infernal_barrage", () -> new InfernalBarrageEnchantment());
	
	//end
	public static final RegistryObject<MEnchantment> GAZE_AVERSION = ENCHANTMENTS.register("gaze_aversion", () -> new GazeAversionEnchantment());
	public static final RegistryObject<MEnchantment> GRAVITY_STABILITY = ENCHANTMENTS.register("gravity_stability", () -> new GravityStabilityEnchantment());
	public static final RegistryObject<MEnchantment> UPWARD_STRIKE = ENCHANTMENTS.register("upward_strike", () -> new UpwardStrikeEnchantment());
	public static final RegistryObject<MEnchantment> DIMENSIONAL_ANCHOR = ENCHANTMENTS.register("dimensional_anchor", () -> new DimensionalAnchorEnchantment());
	public static final RegistryObject<MEnchantment> CHORUS_PIERCING = ENCHANTMENTS.register("chorus_piercing", () -> new ChorusPiercingEnchantment());
	public static final RegistryObject<MEnchantment> OBSIDIAN_SHATTER = ENCHANTMENTS.register("obsidian_shatter", () -> new ObsidianShatterEnchantment());
	public static final RegistryObject<MEnchantment> SHULKER_STANCE = ENCHANTMENTS.register("shulker_stance", () -> new ShulkerStanceEnchantment());
	public static final RegistryObject<MEnchantment> CHORUS_EVASION = ENCHANTMENTS.register("chorus_evasion", () -> new ChorusEvasionEnchantment());
	public static final RegistryObject<MEnchantment> VOID_SNIPE = ENCHANTMENTS.register("void_snipe", () -> new VoidSnipeEnchantment());
	public static final RegistryObject<MEnchantment> ENDER_GAZE = ENCHANTMENTS.register("ender_gaze", () -> new EnderGazeEnchantment());

	//sculk
	public static final RegistryObject<MEnchantment> SCULK_ADAPTATION = ENCHANTMENTS.register("sculk_adaptation", () -> new SculkAdaptationEnchantment());
	public static final RegistryObject<MEnchantment> SILENT_STEP = ENCHANTMENTS.register("silent_step", () -> new SilentStepEnchantment());
	public static final RegistryObject<MEnchantment> SONIC_CLEAVE = ENCHANTMENTS.register("sonic_cleave", () -> new SonicCleaveEnchantment());
	public static final RegistryObject<MEnchantment> CATALYST_ABSORPTION = ENCHANTMENTS.register("catalyst_absorption", () -> new CatalystAbsorptionEnchantment());
	public static final RegistryObject<MEnchantment> SILENT_TOUCH = ENCHANTMENTS.register("silent_touch", () -> new SilentTouchEnchantment());
	public static final RegistryObject<MEnchantment> LIFE_EROSION = ENCHANTMENTS.register("life_erosion", () -> new LifeErosionEnchantment());

	//bless
	public static final RegistryObject<MEnchantment> MASTER_TOUCH = ENCHANTMENTS.register("master_touch", () -> new MasterTouchBlessment());
	public static final RegistryObject<MEnchantment> ACCELERATE = ENCHANTMENTS.register("accelerate", () -> new AccelerateBlessment());
	public static final RegistryObject<MEnchantment> RESOLUTE_WARD = ENCHANTMENTS.register("resolute_ward", () -> new ResoluteWardBlessment());
	public static final RegistryObject<MEnchantment> BARRIER = ENCHANTMENTS.register("barrier", () -> new BarrierBlessment());
	public static final RegistryObject<MEnchantment> CRITICAL_STRIKE = ENCHANTMENTS.register("critical_strike", () -> new CriticalStrikeBlessment());
	public static final RegistryObject<MEnchantment> MALICE = ENCHANTMENTS.register("malice", () -> new MaliceBlessment());
	public static final RegistryObject<MEnchantment> MIRROR = ENCHANTMENTS.register("mirror", () -> new MirrorBlessment());
	public static final RegistryObject<MEnchantment> TIME_BREAK = ENCHANTMENTS.register("time_break", () -> new TimeBreakBlessment());
	public static final RegistryObject<MEnchantment> TERRARIAN_SOUL = ENCHANTMENTS.register("terrarian_soul", () -> new TerrarianSoulBlessment());
}
