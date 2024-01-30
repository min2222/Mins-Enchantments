package com.min01.minsenchantments.misc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.min01.entitytimer.TimerUtil;
import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.mixin.AbstractArrowInvoker;
import com.min01.minsenchantments.network.CellScaleSyncPacket;
import com.min01.minsenchantments.network.EnchantmentNetwork;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Bus.FORGE)
public class EventHandlerForge
{
	public static final String HARDENING = "Hardening";
	public static final String HARDENING_ORIGINAL = "HardeningOriginal";
	public static final String RAGE = "Rage";
	public static final String RAGE_ORIGINAL = "RageOriginal";
	public static final String RETURNING = "Returning";
	public static final String HOMING = "Homing";
	public static final String HOMING_LVL = "HomingLvL";
	public static final String HOMING_TIME = "HomingTime";
	public static final String SOUL_OF_TERRARIAN = "SoulofTerrarian";
	public static final String SOUL_OF_TERRARIAN_SUMMONED = "SoulofTerrarianSummoned";
	public static final String SOUL_OF_TERRARIAN_LVL = "SoulofTerrarianLvL";
	
	public static final String WATER_BOLT = "WaterBolt";
	public static final String SHARP_WAVES = "SharpWaves";
	public static final String SHARP_WAVES_LVL = "SharpWavesLvL";
	public static final String SHARP_WAVES_DMG = "SharpWavesDmg";
	public static final String TIDE = "Tide";
	public static final String POSEIDONS_GRACE = "PoseidonsGrace";
	public static final String POSEIDONS_GRACE_ITEM = "PoseidonsGraceItem";
	public static final String POSEIDONS_GRACE_SUMMONED = "PoseidonsGraceSummoned";
	public static final String POSEIDONS_GRACE_TARGET = "PoseidonsGraceTarget";
	public static final String WAVES_PROTECTION = "WavesProtection";
	public static final String LORD_OF_THE_SEA = "LordOfTheSea";
	public static final String LORD_OF_THE_SEA_DMG = "LordOfTheSeaDmg";
	public static final String WATER_JET = "WaterJet";
	public static final String AQUATIC_AURA = "AquaticAura";
	public static final String SOUL_FIRE = "SoulFire";
	public static final String TELEPORTATION = "Teleportation";
	public static final String RECOCHET = "Recochet";
	public static final String RECOCHET_BOUNCE = "RecochetBounce";
	public static final String RECOCHET_LVL = "RecochetLvL";
	public static final String WALLBREAK = "Wallbreak";
	public static final String SNIPE = "Snipe";
	public static final String SNIPE_LVL = "SnipeLvL";
	public static final String SNIPE_TICK = "SnipeTick";
	public static final String AUTO_SHIELDING = "AutoShielding";
	public static final String SKILLFUL = "Skillful";
	public static final String SKILLFUL_DMG = "SkillfulDmg";
	public static final String SKILLFUL_SPEED = "SkillfulSpeed";
	public static final String SKILLFUL_COUNT = "SkillfulCount";
	public static final String CELL_DIVISION = "CellDivision";
	public static final String CELL_DIVISION_LVL = "CellDivisionLvL";
	public static final String CELL_DIVISION_NUMBER = "CellDivisionNumber";
	public static final String CELL_DIVISION_SCALE = "CellDivisionScale";
	public static final String ACCELERATE = "Accelerate";
	public static final String ACCELERATE_ORIGINAL = "AccelerateOriginal";
	public static final String MINER = "Miner";
	public static final String MINER_LVL = "MinerLvL";
	public static final String MINER_COUNT = "MinerCount";
	
	public static final Map<LivingEntity, List<LivingEntity>> AQUATIC_AURA_MAP = new HashMap<>();
	public static final Map<LivingEntity, List<LivingEntity>> AQUATIC_AURA_MAP2 = new HashMap<>();
	
	public static final Map<Item, ItemStack> ITEM_MAP = new HashMap<>();
	
	@SubscribeEvent
	public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event)
	{
		ITEM_MAP.put(event.getItemStack().getItem(), event.getItemStack());
	}
	
	@SubscribeEvent
	public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
	{
		ITEM_MAP.put(event.getItemStack().getItem(), event.getItemStack());
	}
	
	@SubscribeEvent
	public static void onPlaySoundAtEntity(PlayLevelSoundEvent.AtEntity event)
	{
		Entity entity = event.getEntity();
		if(entity instanceof LivingEntity living)
		{
			if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CONCEALMENT.get(), living) > 0)
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event)
	{
		Player player = event.getPlayer();
		ItemStack stack = player.getMainHandItem();
		if(stack.getEnchantmentLevel(CustomEnchantments.INFECTION.get()) > 0)
		{
			if(event.getExpToDrop() > 0)
			{
				int level = stack.getEnchantmentLevel(CustomEnchantments.INFECTION.get());
				event.setExpToDrop(event.getExpToDrop() + (level * EnchantmentConfig.infectionExpPerLevel.get()));
			}
		}
	}
	
	@SubscribeEvent
	public static void onBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		Player player = event.getEntity();
		ItemStack stack = player.getMainHandItem();
		if(stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get()) > 0)
		{
			float speed = stack.getOrCreateTag().getFloat(SKILLFUL_SPEED);
			event.setNewSpeed(event.getOriginalSpeed() + speed);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		Player player = event.getEntity();
		if(event.isWasDeath())
		{
			Iterable<ItemStack> armors = player.getArmorSlots();
			armors.forEach((stack) ->
			{
				if(stack.getEnchantmentLevel(CustomEnchantments.RETURNING.get()) > 0)
				{
					int level = stack.getEnchantmentLevel(CustomEnchantments.RETURNING.get());
					if(stack.getOrCreateTag().contains(RETURNING))
					{
						if(stack.getMaxDamage() - stack.getDamageValue() >=  EnchantmentConfig.returningDurabilityPerLevel.get() / level)
						{
							stack.hurtAndBreak(EnchantmentConfig.returningDurabilityPerLevel.get() / level, player, (living) -> 
							{
								
							});
							player.setPos(Vec3.atCenterOf(NbtUtils.readBlockPos(stack.getOrCreateTag().getCompound(RETURNING))));
							player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE);
						}
					}
				}
			});
		}
	}
	
	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		LivingEntity living = event.getEntity();
		Iterable<ItemStack> armors = living.getArmorSlots();
		armors.forEach((stack) ->
		{
			if(stack.getEnchantmentLevel(CustomEnchantments.RETURNING.get()) > 0)
			{
				stack.getOrCreateTag().put(RETURNING, NbtUtils.writeBlockPos(living.blockPosition()));
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingEntityStartUseItem(LivingEntityUseItemEvent.Start event)
	{
		LivingEntity living = event.getEntity();
		ItemStack stack = event.getItem();
		
		if(stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get()) > 0)
		{
			living.getPersistentData().putInt(SNIPE_TICK, stack.getUseDuration());
		}
	}
	
	@SubscribeEvent
	public static void onLivingEntityStopUseItem(LivingEntityUseItemEvent.Stop event)
	{
		LivingEntity living = event.getEntity();
		ItemStack stack = event.getItem();
		
		if(stack.getEnchantmentLevel(CustomEnchantments.SHARP_WAVES.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.SHARP_WAVES.get());
			living.getPersistentData().putBoolean(SHARP_WAVES, true);
			living.getPersistentData().putInt(SHARP_WAVES_LVL, level);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.POSEIDONS_GRACE.get()) > 0)
		{
			living.getPersistentData().putBoolean(POSEIDONS_GRACE, true);
			living.getPersistentData().put(POSEIDONS_GRACE_ITEM, stack.save(new CompoundTag()));
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.RECOCHET.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.RECOCHET.get());
			living.getPersistentData().putBoolean(RECOCHET, true);
			living.getPersistentData().putInt(RECOCHET_LVL, level);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.WALLBREAK.get()) > 0)
		{
			living.getPersistentData().putBoolean(WALLBREAK, true);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get());
			living.getPersistentData().putBoolean(SNIPE, true);
			living.getPersistentData().putInt(SNIPE_LVL, level);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.CELL_DIVISION.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.CELL_DIVISION.get());
			living.getPersistentData().putBoolean(CELL_DIVISION, true);
			living.getPersistentData().putInt(CELL_DIVISION_LVL, level);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.MINER.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.MINER.get());
			living.getPersistentData().putBoolean(MINER, true);
			living.getPersistentData().putInt(MINER_LVL, level);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.WATER_JET.get()) > 0)
		{
			if(living.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && living.getProjectile(stack).getOrCreateTag().contains(WATER_JET))
			{
				living.getPersistentData().putBoolean(WATER_JET, true);
			}
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get()) > 0)
		{
			float damage = stack.getOrCreateTag().getFloat(SKILLFUL_DMG);
			living.getPersistentData().putFloat(SKILLFUL_DMG, damage);
			living.getPersistentData().putBoolean(SKILLFUL, true);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.TELEPORTATION.get()) > 0)
		{
			living.getPersistentData().putBoolean(TELEPORTATION, true);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.HOMING.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.HOMING.get());
			living.getPersistentData().putBoolean(HOMING, true);
			living.getPersistentData().putInt(HOMING_LVL, level);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.SOUL_OF_TERRARIAN.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.SOUL_OF_TERRARIAN.get());
			living.getPersistentData().putBoolean(SOUL_OF_TERRARIAN, true);
			living.getPersistentData().putInt(SOUL_OF_TERRARIAN_LVL, level);
		}
	}
	
	@SubscribeEvent
	public static void onLivingEntityUseItem(LivingEntityUseItemEvent.Tick event)
	{
		LivingEntity living = event.getEntity();
		ItemStack stack = event.getItem();
		if(stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get());
			int speed = (int) (level * EnchantmentConfig.snipeChargeSpeedPerLevel.get());
			int tick = living.getPersistentData().getInt(SNIPE_TICK);
			if(living.tickCount % speed * 20 == 0)
			{
				living.getPersistentData().putInt(SNIPE_TICK, tick - 1);
			}
			
			event.setDuration(tick);
		}
	}
	
	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event)
	{
		LivingEntity living = event.getEntity();
		Entity source = event.getSource().getEntity();
		Entity directSource = event.getSource().getDirectEntity();
		
		if(source != null && source instanceof LivingEntity attacker)
		{
			if(living.getOffhandItem().getEnchantmentLevel(CustomEnchantments.MIRROR.get()) > 0 && living.isBlocking())
			{
				if(!(directSource instanceof Projectile proj))
				{
					int level = living.getOffhandItem().getEnchantmentLevel(CustomEnchantments.MIRROR.get());
					if(Math.random() <= (level * EnchantmentConfig.mirrorReflectChancePerLevel.get()) / 100)
					{
						attacker.hurt(event.getSource(), event.getAmount());
					}
				}
			}
			
			if(attacker.getMainHandItem().getEnchantmentLevel(CustomEnchantments.LEECH.get()) > 0)
			{
				int level = attacker.getMainHandItem().getEnchantmentLevel(CustomEnchantments.LEECH.get());
				if(Math.random() <= (level * EnchantmentConfig.leechChancePerLevel.get()) / 100)
				{
					attacker.heal(level * EnchantmentConfig.leechHealAmountPerLevel.get());
				}
			}
			
			if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.FLAME_THORN.get(), living) > 0)
			{
				boolean flag = directSource instanceof Projectile proj ? proj.getOwner() != null && proj.getOwner() == living : directSource == living;
				//FIXME crash with wild fire from friends & foes
				if(attacker != living && !flag && !(event.getSource().getMsgId().equals("thorns")))
				{
					int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.FLAME_THORN.get(), living);
					attacker.hurt(living.damageSources().thorns(living), level * EnchantmentConfig.flameThornDamagePerLevel.get());
					attacker.setSecondsOnFire((level * EnchantmentConfig.flameThornFireDurationPerLevel.get()) * 20);
				}
			}
			
			if(attacker.getMainHandItem().getEnchantmentLevel(CustomEnchantments.RAGE.get()) > 0)
			{
				ItemStack stack = attacker.getMainHandItem();
				int level = stack.getEnchantmentLevel(CustomEnchantments.RAGE.get());
				if(stack.getTag().contains(RAGE))
				{
					float damage = stack.getTag().getFloat(RAGE);
					float original = stack.getTag().getFloat(RAGE_ORIGINAL);
					if(original + damage < original + (level * EnchantmentConfig.rageMaxDamagePerLevel.get()))
					{
						stack.getTag().putFloat(RAGE, damage + (level * EnchantmentConfig.rageDamagePerLevel.get()));
					}
				}
			}
			
			if(attacker.getMainHandItem().getEnchantmentLevel(CustomEnchantments.ACCELERATE.get()) > 0)
			{
				ItemStack stack = attacker.getMainHandItem();
				int level = stack.getEnchantmentLevel(CustomEnchantments.ACCELERATE.get());
				if(stack.getTag().contains(ACCELERATE))
				{
					float speed = stack.getTag().getFloat(ACCELERATE);
					float original = stack.getTag().getFloat(ACCELERATE_ORIGINAL);
					if(original + speed < original + (level * EnchantmentConfig.accelerateMaxSpeedPerLevel.get()))
					{
						stack.getTag().putFloat(ACCELERATE, speed + (level * EnchantmentConfig.accelerateSpeedPerLevel.get()));
					}
				}
			}
			
			if(attacker.getMainHandItem().getEnchantmentLevel(CustomEnchantments.SOUL_FIRE.get()) > 0)
			{
				int level = attacker.getMainHandItem().getEnchantmentLevel(CustomEnchantments.SOUL_FIRE.get());
				if(!living.isOnFire())
				{
					living.getPersistentData().putInt(SOUL_FIRE, level * (EnchantmentConfig.soulFireDurationPerLevel.get() * 20));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event)
	{
		LivingEntity living = event.getEntity();
		if(living.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(CustomEnchantments.OVERGRAVITY.get()) > 0)
		{
			int level = living.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(CustomEnchantments.OVERGRAVITY.get());
			event.setDamageMultiplier(event.getDamageMultiplier() + (level *  EnchantmentConfig.overgravityFallDamagePerLevel.get()));
		}
	}
	
	@SubscribeEvent
	public static void onLivingBreath(LivingBreatheEvent event)
	{
		LivingEntity living = event.getEntity();
		if(living.getPersistentData().contains(AQUATIC_AURA))
		{
			event.setCanBreathe(false);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		Player player = event.player;
		
		if(player.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(CustomEnchantments.TAKEOFF.get()) > 0)
		{
			if(player.getItemBySlot(EquipmentSlot.CHEST).canElytraFly(player))
			{
				if(player.getDeltaMovement().y > 0)
				{
					player.startFallFlying();
				}
			}
		}
		
		if(player.getMainHandItem().getEnchantmentLevel(CustomEnchantments.RAGE.get()) > 0)
		{
			if(player.getMainHandItem().getOrCreateTag().contains(RAGE))
			{
				float damage = player.getMainHandItem().getOrCreateTag().getFloat(RAGE);
				if(damage + player.getMainHandItem().getOrCreateTag().getFloat(RAGE_ORIGINAL) > player.getMainHandItem().getOrCreateTag().getFloat(RAGE_ORIGINAL))
				{
					player.getMainHandItem().getOrCreateTag().putFloat(RAGE, damage - 0.002F);
				}
			}
			else if(!player.getMainHandItem().getOrCreateTag().contains(RAGE_ORIGINAL))
			{
				player.getMainHandItem().getOrCreateTag().putFloat(RAGE, 0);
				Multimap<Attribute, AttributeModifier> map = player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
				for(Entry<Attribute, AttributeModifier> entry : map.entries())
				{
					if(entry.getKey() == Attributes.ATTACK_DAMAGE)
					{
						player.getMainHandItem().getOrCreateTag().putFloat(RAGE_ORIGINAL, (float) (entry.getValue().getAmount()));
					}
				}
			}
		}
		
		if(player.getMainHandItem().getEnchantmentLevel(CustomEnchantments.ACCELERATE.get()) > 0)
		{
			if(player.getMainHandItem().getOrCreateTag().contains(ACCELERATE))
			{
				float speed = player.getMainHandItem().getOrCreateTag().getFloat(ACCELERATE);
				if(speed + player.getMainHandItem().getOrCreateTag().getFloat(ACCELERATE_ORIGINAL) > player.getMainHandItem().getOrCreateTag().getFloat(ACCELERATE_ORIGINAL))
				{
					player.getMainHandItem().getOrCreateTag().putFloat(ACCELERATE, speed - 0.002F);
				}
			}
			else if(!player.getMainHandItem().getOrCreateTag().contains(ACCELERATE_ORIGINAL))
			{
				player.getMainHandItem().getOrCreateTag().putFloat(ACCELERATE, 0);
				Multimap<Attribute, AttributeModifier> map = player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
				for(Entry<Attribute, AttributeModifier> entry : map.entries())
				{
					if(entry.getKey() == Attributes.ATTACK_SPEED)
					{
						player.getMainHandItem().getOrCreateTag().putFloat(ACCELERATE_ORIGINAL, (float) (entry.getValue().getAmount()));
					}
				}
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.HARDENING.get(), player) > 0)
		{
			Iterable<ItemStack> armors = player.getArmorSlots();
			armors.forEach((stack) ->
			{
				if(stack.getOrCreateTag().contains(HARDENING))
				{
					float armor = stack.getOrCreateTag().getFloat(HARDENING);
					if(armor + stack.getOrCreateTag().getFloat(HARDENING_ORIGINAL) > stack.getOrCreateTag().getFloat(HARDENING_ORIGINAL))
					{
						stack.getOrCreateTag().putFloat(HARDENING, armor - 0.002F);
					}
				}
				else if(!stack.getOrCreateTag().contains(HARDENING_ORIGINAL))
				{
					stack.getOrCreateTag().putFloat(HARDENING, 0);
					Multimap<Attribute, AttributeModifier> map = stack.getAttributeModifiers(stack.getEquipmentSlot());
					for(Entry<Attribute, AttributeModifier> entry : map.entries())
					{
						if(entry.getKey() == Attributes.ARMOR)
						{
							player.getMainHandItem().getOrCreateTag().putFloat(HARDENING_ORIGINAL, (float) (entry.getValue().getAmount()));
						}
					}
				}
			});
		}
		
		if(player.isUsingItem())
		{
			ItemStack stack = player.getUseItem();
			if(stack.getEnchantmentLevel(CustomEnchantments.QUICKDRAW.get()) > 0)
			{
				int level = stack.getEnchantmentLevel(CustomEnchantments.QUICKDRAW.get());
				
   				for(int i = 0; i < level; i++) 
				{
					Method m = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "m_21329_");
					try 
					{
						m.invoke(player);
					}
					catch (Exception e) 
					{
						
					}
				}
			}
		}
		
		if(player.isInWater())
		{
			for(int i = 0; i < player.getInventory().getContainerSize(); i++)
			{
				ItemStack itemstack = player.getInventory().getItem(i);
				if(itemstack.getEnchantmentLevel(CustomEnchantments.RUSTY.get()) > 0)
				{
					int level = itemstack.getEnchantmentLevel(CustomEnchantments.RUSTY.get());
					if(player.tickCount % ((EnchantmentConfig.rustyDecreaseIntervalPerLevel.get() * 20) / level) == 0 && (itemstack.getDamageValue() > 1 || !itemstack.isDamaged()))
					{
						itemstack.hurtAndBreak(level, player, (t) -> 
						{
							
						});
					}
				}
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.TIDE.get(), player) > 0)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.TIDE.get(), player);
			float tide = player.getPersistentData().getFloat(TIDE);
			double swimSpeed = player.getAttributeBaseValue(ForgeMod.SWIM_SPEED.get());
			float maxSpeed = (float) (swimSpeed + (level * EnchantmentConfig.tideMaxSpeedPerLevel.get()));
			float speed = (level * EnchantmentConfig.tideSpeedPerLevel.get()) / 20;
			if(player.isInWater() && player.isSwimming())
			{
				if(swimSpeed + tide < maxSpeed)
				{
					player.getPersistentData().putFloat(TIDE, tide + speed);
				}
				Vec3 lookPos = player.position().add(EnchantmentUtil.getLookPos(player.getXRot(), player.getYRot(), 0, 0.001F));
				Vec3 motion = EnchantmentUtil.fromToVector(player.position(), lookPos, (float) (swimSpeed + tide));
				player.setDeltaMovement(motion.x, motion.y, motion.z);
				if(player instanceof ServerPlayer serverPlayer)
				{
					serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
				}
			}
			else
			{
				player.getPersistentData().putFloat(TIDE, 0);
			}
		}
		
		if(player.getPersistentData().contains(WAVES_PROTECTION))
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.WAVES_PROTECTION.get(), player);
			float wave = player.getPersistentData().getFloat(WAVES_PROTECTION);
			if(wave > 0)
			{
				player.getPersistentData().putFloat(WAVES_PROTECTION, wave - 1);
				if(player.isInWater() && player.isSwimming())
				{
					Vec3 lookPos = player.position().add(EnchantmentUtil.getLookPos(player.getXRot(), player.getYRot(), 0, 0.1F));
					Vec3 motion = EnchantmentUtil.fromToVector(player.position(), lookPos, level * EnchantmentConfig.wavesProtectionSpeedPerLevel.get());
					player.setDeltaMovement(motion.x, player.getDeltaMovement().y, motion.z);
					if(player instanceof ServerPlayer serverPlayer)
					{
						serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onLivingTick(LivingTickEvent event)
	{
		LivingEntity living = event.getEntity();
		
		if(AQUATIC_AURA_MAP.containsKey(living))
		{
			List<LivingEntity> list = AQUATIC_AURA_MAP.get(living);
			list.forEach((entity) ->
			{
				AQUATIC_AURA_MAP2.put(entity, list);
			});
		}
		
		if(AQUATIC_AURA_MAP2.containsKey(living))
		{
			for(Entry<LivingEntity, List<LivingEntity>> entry : AQUATIC_AURA_MAP.entrySet())
			{
				List<LivingEntity> list = entry.getValue();
				if(living.getPersistentData().contains(AQUATIC_AURA) && !list.contains(living))
				{
					living.getPersistentData().remove(AQUATIC_AURA);
				}
			}
		}
		
		if(living.level() instanceof ServerLevel level)
		{
			if(living.getPersistentData().contains(LORD_OF_THE_SEA) && living instanceof WaterAnimal waterAnimal)
			{
				UUID ownerUUID = waterAnimal.getPersistentData().getUUID(LORD_OF_THE_SEA);
				float damage = waterAnimal.getPersistentData().getFloat(LORD_OF_THE_SEA_DMG);
				Entity owner = level.getEntity(ownerUUID);
				if(owner != null && waterAnimal.getTarget() != null)
				{
					Entity target = waterAnimal.getTarget();
					waterAnimal.getLookControl().setLookAt(target, 30.0F, 30.0F);
					double distance = waterAnimal.getPerceivedTargetDistanceSquareForMeleeAttack((LivingEntity) target);
					if(distance <= EnchantmentUtil.getAttackReachSqr(waterAnimal, target))
					{
						target.hurt(owner.damageSources().mobAttack((LivingEntity) owner), damage);
					}
					else
					{
						waterAnimal.getNavigation().moveTo(target, waterAnimal.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
					}
				}
				
				if(owner == null || waterAnimal.getTarget() == null)
				{
					waterAnimal.getPersistentData().remove(LORD_OF_THE_SEA);
					waterAnimal.getPersistentData().remove(LORD_OF_THE_SEA_DMG);
				}
			}
		}
		
		if(living.getPersistentData().contains(SOUL_FIRE))
		{
			int remainingSoulFireTicks = living.getPersistentData().getInt(SOUL_FIRE);
			if (remainingSoulFireTicks > 0) 
			{
				if (!living.fireImmune())
				{
		            if (remainingSoulFireTicks % 20 == 0 && !living.isInLava()) 
		            {
		            	living.hurt(living.damageSources().onFire(), 2.0F);
		            }
		            
		            living.getPersistentData().putInt(SOUL_FIRE, remainingSoulFireTicks - 1);
				}
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_UNDEAD.get(), living) > 0)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_UNDEAD.get(), living);
			if (living.level().isDay() && !living.level().isClientSide)
			{
				float f = living.getLightLevelDependentMagicValue();
				BlockPos blockpos = BlockPos.containing(living.getX(), living.getEyeY(), living.getZ());
				boolean flag = living.isInWaterRainOrBubble() || living.isInPowderSnow || living.wasInPowderSnow;
				if (f > 0.5F && living.level().random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && living.level().canSeeSky(blockpos)) 
				{
					living.setSecondsOnFire(level * (EnchantmentConfig.undeadCurseFireDurationPerLevel.get() * 20));
				}
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_ENDERMAN.get(), living) > 0)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_ENDERMAN.get(), living);
			if(living.isInWater())
			{
				living.hurt(living.damageSources().drown(), level * EnchantmentConfig.endermanCurseDamagePerLevel.get());
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_SINKING.get(), living) > 0)
		{
			if(living.isInWater())
			{
				int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_SINKING.get(), living);
				living.setDeltaMovement(living.getDeltaMovement().subtract(0, level * EnchantmentConfig.sinkingCurseSpeedPerLevel.get(), 0));
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.AQUATIC_AURA.get(), living) > 0)
		{
			int enchantLevel = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.AQUATIC_AURA.get(), living);
			List<LivingEntity> list = living.level().getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(enchantLevel * EnchantmentConfig.aquaticAuraRadiusPerLevel.get()));
			List<Projectile> projList = living.level().getEntitiesOfClass(Projectile.class, living.getBoundingBox().inflate(enchantLevel * EnchantmentConfig.aquaticAuraRadiusPerLevel.get()));
			list.removeIf((entity) -> entity == living);
			projList.removeIf((proj) -> (proj.getOwner() != null && proj.getOwner() == living) || proj instanceof ThrownTrident);	
			AQUATIC_AURA_MAP.put(living, list);
			list.forEach((entity) -> 
			{
				entity.getPersistentData().putBoolean(AQUATIC_AURA, true);
			});
			
			projList.forEach((proj) -> 
			{
				if(proj instanceof AbstractArrow arrow)
				{
					float waterInertia = ((AbstractArrowInvoker)arrow).Invoke_getWaterInertia();
					arrow.setDeltaMovement(arrow.getDeltaMovement().scale(waterInertia));
				}
				else
				{
					proj.setDeltaMovement(proj.getDeltaMovement().scale(0.6F));
				}
				
				if(proj.getDeltaMovement() != Vec3.ZERO && !proj.onGround())
				{
					Vec3 vec3 = proj.getDeltaMovement();
					double d5 = vec3.x;
					double d6 = vec3.y;
					double d1 = vec3.z;
			         
					double d7 = proj.getX() + d5;
					double d2 = proj.getY() + d6;
					double d3 = proj.getZ() + d1;
			         
		            for(int j = 0; j < 4; ++j) 
		            {
		                proj.level().addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25D, d2 - d6 * 0.25D, d3 - d1 * 0.25D, d5, d6, d1);
		            }
				}
			});
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.SAILING.get(), living) > 0)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.SAILING.get(), living);
			if(living.isPassenger())
			{
				Entity entity = living.getVehicle();
				if(living.zza > 0)
				{
					Vec3 lookPos = entity.position().add(EnchantmentUtil.getLookPos(entity.getXRot(), entity.getYRot(), 0, 0.001F));
					Vec3 motion = EnchantmentUtil.fromToVector(entity.position(), lookPos, level * EnchantmentConfig.sailingSpeedPerLevel.get());
					entity.setDeltaMovement(motion.x, entity.getDeltaMovement().y, motion.z);
				}
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.DRY.get(), living) > 0)
		{
			if(living.isShiftKeyDown())
			{
				int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.DRY.get(), living);
				int radius = level * EnchantmentConfig.dryRadiusPerLevel.get();
				
				if(EnchantmentUtil.removeWaterBreadthFirstSearch(living.level(), living.blockPosition(), radius))
				{
					living.level().levelEvent(2001, living.blockPosition(), Block.getId(Blocks.WATER.defaultBlockState()));
				}
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.BARRIER.get(), living) > 0)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.BARRIER.get(), living);
			
			float radius = level * EnchantmentConfig.barrierRadiusPerLevel.get();
			
			int i = Mth.ceil((float)Math.PI * radius * radius);

			for(int j = 0; j < i; ++j)
			{
				float f2 = living.getRandom().nextFloat() * ((float)Math.PI * 2F);
				float f3 = Mth.sqrt(living.getRandom().nextFloat()) * radius;
				
	            List<LivingEntity> list = living.level().getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(Mth.cos(f2) * f3, radius, Mth.sin(f2) * f3));
	            List<Projectile> projList = living.level().getEntitiesOfClass(Projectile.class, living.getBoundingBox().inflate(Mth.cos(f2) * f3, radius, Mth.sin(f2) * f3));
	            projList.removeIf((proj) -> proj.getOwner() != null && proj.getOwner() == living);
	            projList.forEach((entity) ->
	            {
	            	Vec3 vec = EnchantmentUtil.fromToVector(living.position(), entity.position(), level * EnchantmentConfig.barrierPushPowerPerLevel.get());
	            	entity.setDeltaMovement(vec.x, entity.getDeltaMovement().y, vec.z);
	            });
	            list.removeIf((entity) -> entity == living);
	            list.removeIf((entity) -> entity instanceof Player && EnchantmentConfig.disableBarrierPushingPlayer.get());
	            list.forEach((entity) ->
	            {
	            	Vec3 vec = EnchantmentUtil.fromToVector(living.position(), entity.position(), level * EnchantmentConfig.barrierPushPowerPerLevel.get());
	            	entity.setDeltaMovement(vec.x, entity.getDeltaMovement().y, vec.z);
	            });
			}
		}
		
		if(living.getPersistentData().contains(AUTO_SHIELDING) && living.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ShieldItem)
		{
			ItemStack stack = living.getItemBySlot(EquipmentSlot.OFFHAND);
			int shielding = living.getPersistentData().getInt(AUTO_SHIELDING);
			if(shielding > 0)
			{
				if(stack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get()) > 0)
				{
					living.getPersistentData().putInt(AUTO_SHIELDING, shielding - 1);
					living.startUsingItem(InteractionHand.OFF_HAND);
				}
			}
		}
		
		if(living.getPersistentData().contains(AUTO_SHIELDING))
		{
			int shielding = living.getPersistentData().getInt(AUTO_SHIELDING);
			if(shielding <= 0)
			{
				living.stopUsingItem();
				living.getPersistentData().remove(AUTO_SHIELDING);
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.MAGNET.get(), living) > 0 && living.isShiftKeyDown())
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.MAGNET.get(), living);
			List<ItemEntity> itemList = living.level().getEntitiesOfClass(ItemEntity.class, living.getBoundingBox().inflate(level * EnchantmentConfig.magnetRadiusPerLevel.get()));
			itemList.forEach((item) -> 
			{
				item.setDeltaMovement(EnchantmentUtil.fromToVector(item.position(), living.position(), 0.05F));
			});
			
			List<ExperienceOrb> xpList = living.level().getEntitiesOfClass(ExperienceOrb.class, living.getBoundingBox().inflate(level * EnchantmentConfig.magnetRadiusPerLevel.get()));
			xpList.forEach((xp) -> 
			{
				xp.setDeltaMovement(EnchantmentUtil.fromToVector(xp.position(), living.position(), 0.1F));
			});
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CLIMB.get(), living) > 0)
		{
			if(living.horizontalCollision)
			{
				int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CLIMB.get(), living);
				double climbSpeed = level * EnchantmentConfig.climbSpeedPerLevel.get();
			    if(living.isShiftKeyDown())
			    {
			    	living.setDeltaMovement(living.getDeltaMovement().x, 0.0, living.getDeltaMovement().z);
			    }
			    else
			    {
					living.setDeltaMovement(living.getDeltaMovement().x, climbSpeed, living.getDeltaMovement().z);
			    }
				living.fallDistance = 0.0F;
			}
		}
		
		if(living.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(CustomEnchantments.LAVA_WALKER.get()) > 0)
		{
			BlockPos pos = BlockPos.containing(living.getX(), Mth.floor(living.getBoundingBox().minY), living.getZ());
	        if(living.level().getBlockState(pos).getBlock() == Blocks.LAVA) 
	        {
	            if(living.getDeltaMovement().y < 0) 
	            {
	            	living.setDeltaMovement(living.getDeltaMovement().x, 0, living.getDeltaMovement().z);
	            	living.setPos(living.position().add(0, -living.getDeltaMovement().y, 0));
	            }
	            living.fallDistance = 0.0F;
	            living.setOnGround(true);
	        }
		}
		
		if(living.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(CustomEnchantments.OVERGRAVITY.get()) > 0)
		{
			int level = living.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(CustomEnchantments.OVERGRAVITY.get());
			living.setDeltaMovement(living.getDeltaMovement().subtract(0, level * EnchantmentConfig.overgravityFallSpeedPerLevel.get(), 0));
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_FLOATING.get(), living) > 0)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_FLOATING.get(), living);
			if(living.isEyeInFluidType(ForgeMod.WATER_TYPE.get()))
			{
				living.setDeltaMovement(living.getDeltaMovement().add(0, level * EnchantmentConfig.floatingCurseFloatingSpeedPerLevel.get(), 0));
			}
		}
		
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_MERMAID.get(), living) > 0 && living.isInWater())
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_MERMAID.get(), living);
			List<Mob> list = living.level().getEntitiesOfClass(Mob.class, living.getBoundingBox().inflate(level * EnchantmentConfig.mermaidsCurseRadiusPerLevel.get()));
			list.forEach((mob) ->
			{
				if(mob.getTarget() != living || mob.getTarget() == null)
				{
					mob.setTarget(living);
				}
			});
		}
		
		if(living instanceof Mob mob)
		{
			if(mob.getTarget() != null)
			{
				Entity target = mob.getTarget();
				if(target instanceof LivingEntity livingTarget)
				{
					if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.MERMAIDS_BLESSING.get(), livingTarget) > 0)
					{
						int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.MERMAIDS_BLESSING.get(), livingTarget);
						if(livingTarget.isInWater() && livingTarget instanceof Player player)
						{
							if(player.isSwimming())
							{
								if(mob.getHealth() <= level * EnchantmentConfig.mermaidsBlessingMaxMobHealthPerLevel.get() && mob.getAttribute(Attributes.ATTACK_DAMAGE) != null && mob.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) <= level * EnchantmentConfig.mermaidsBlessingMaxMobDamagePerLevel.get())
								{
									mob.setTarget(null);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent event)
	{
		if(event.getEntity() instanceof Projectile proj)
		{
			if(proj.getOwner() != null)
			{
				Entity entity = proj.getOwner();
				
				if(entity instanceof LivingEntity living)
				{
					ItemStack stack = living.getUseItem();
					
					if(stack.isEmpty())
					{
						if(living.getMainHandItem().isEmpty())
						{
							stack = living.getOffhandItem();
						}
						else
						{
							stack = living.getMainHandItem();
						}
					}
					
					if(proj instanceof AbstractArrow arrow)
					{
						if(stack.getEnchantmentLevel(CustomEnchantments.WATERBOLT.get()) > 0)
						{
							arrow.getPersistentData().putBoolean(WATER_BOLT, true);
						}
						
						if(stack.getEnchantmentLevel(CustomEnchantments.SONIC_BOOM.get()) > 0)
						{
							int level = stack.getEnchantmentLevel(CustomEnchantments.SONIC_BOOM.get());
				            Vec3 vec3 = living.position().add(0, living.getEyeHeight(), 0);
				            Vec3 lookPos = vec3.add(EnchantmentUtil.getLookPos(living.getXRot(), living.getYRot(), 0, 1));
				            Vec3 vec31 = lookPos.subtract(vec3);
				            Vec3 vec32 = vec31.normalize();

				            for(int i = 1; i < Mth.floor(vec31.length()) + (level * EnchantmentConfig.sonicBoomDistancePerLevel.get()); ++i)
				            {
				            	Vec3 vec33 = vec3.add(vec32.scale((double)i));				            	
				            	if(living.level() instanceof ServerLevel serverLevel)
				            	{
				            		serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
				            	}
				            	AABB aabb = new AABB(vec3, vec33);
				            	List<LivingEntity> list = living.level().getEntitiesOfClass(LivingEntity.class, aabb);
				            	list.removeIf((entity1) -> entity1 == living);
				            	list.forEach((entity1) ->
				            	{
				            		entity1.hurt(arrow.damageSources().sonicBoom(living), level * EnchantmentConfig.sonicBoomDamagePerLevel.get());
						            double d1 = 0.5D * (1.0D - entity1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
						            double d0 = 2.5D * (1.0D - entity1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
						            entity1.push(vec32.x() * d0, vec32.y() * d1, vec32.z() * d0);
				            	});
				            }

				            living.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
							event.setCanceled(true);
						}
					}
					
					if(proj instanceof ThrownTrident trident)
					{
						if(living.getPersistentData().contains(SHARP_WAVES))
						{
							int level = living.getPersistentData().getInt(SHARP_WAVES_LVL);
							trident.getPersistentData().put(SHARP_WAVES, NbtUtils.writeBlockPos(trident.blockPosition()));
							trident.getPersistentData().putInt(SHARP_WAVES_LVL, level);
							trident.getPersistentData().putFloat(SHARP_WAVES_DMG, 0);
							
							living.getPersistentData().remove(SHARP_WAVES);
							living.getPersistentData().remove(SHARP_WAVES_LVL);
						}
						else if(stack.getEnchantmentLevel(CustomEnchantments.SHARP_WAVES.get()) > 0)
						{
							int level = stack.getEnchantmentLevel(CustomEnchantments.SHARP_WAVES.get());
							trident.getPersistentData().put(SHARP_WAVES, NbtUtils.writeBlockPos(trident.blockPosition()));
							trident.getPersistentData().putInt(SHARP_WAVES_LVL, level);
							trident.getPersistentData().putFloat(SHARP_WAVES_DMG, 0);
						}
						
						if(living.getPersistentData().contains(POSEIDONS_GRACE))
						{
							ItemStack tridentItem = ItemStack.of(living.getPersistentData().getCompound(POSEIDONS_GRACE_ITEM));
							trident.getPersistentData().putBoolean(POSEIDONS_GRACE, true);
							trident.getPersistentData().put(POSEIDONS_GRACE_ITEM, tridentItem.save(new CompoundTag()));
							
							living.getPersistentData().remove(POSEIDONS_GRACE);
							living.getPersistentData().remove(POSEIDONS_GRACE_ITEM);
						}
						else if(stack.getEnchantmentLevel(CustomEnchantments.POSEIDONS_GRACE.get()) > 0)
						{
							trident.getPersistentData().putBoolean(POSEIDONS_GRACE, true);
							trident.getPersistentData().put(POSEIDONS_GRACE_ITEM, stack.save(new CompoundTag()));
						}
					}
					
					if(living.getPersistentData().contains(MINER))
					{
						int level = living.getPersistentData().getInt(MINER_LVL);
						proj.getPersistentData().putBoolean(MINER, true);
						proj.getPersistentData().putInt(MINER_LVL, level);
						
						living.getPersistentData().remove(MINER);
						living.getPersistentData().remove(MINER_LVL);
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.MINER.get()) > 0)
					{
						int level = stack.getEnchantmentLevel(CustomEnchantments.MINER.get());
						proj.getPersistentData().putBoolean(MINER, true);
						proj.getPersistentData().putInt(MINER_LVL, level);
					}

					if(living.getPersistentData().contains(RECOCHET))
					{
						int level = living.getPersistentData().getInt(RECOCHET_LVL);
						proj.getPersistentData().putBoolean(RECOCHET, true);
						proj.getPersistentData().putInt(RECOCHET_LVL, level);
						living.getPersistentData().remove(RECOCHET);
						living.getPersistentData().remove(RECOCHET_LVL);
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.RECOCHET.get()) > 0)
					{
						int level = stack.getEnchantmentLevel(CustomEnchantments.RECOCHET.get());
						proj.getPersistentData().putBoolean(RECOCHET, true);
						proj.getPersistentData().putInt(RECOCHET_LVL, level);
					}
					
					if(living.getPersistentData().contains(WALLBREAK))
					{
						proj.getPersistentData().putBoolean(WALLBREAK, true);
						living.getPersistentData().remove(WALLBREAK);
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.WALLBREAK.get()) > 0)
					{
						proj.getPersistentData().putBoolean(WALLBREAK, true);
					}
					
					if(living.getPersistentData().contains(SNIPE))
					{
						int level = living.getPersistentData().getInt(SNIPE_LVL);
						TimerUtil.setTickrate(proj, 20 + (level * EnchantmentConfig.snipeProjectileSpeedPerLevel.get()));
						proj.getPersistentData().putBoolean(SNIPE, true);
						proj.getPersistentData().putInt(SNIPE_LVL, level);
						
						living.getPersistentData().remove(SNIPE);
						living.getPersistentData().remove(SNIPE_LVL);
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get()) > 0)
					{
						int level = stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get());
						TimerUtil.setTickrate(proj, 20 + (level * EnchantmentConfig.snipeProjectileSpeedPerLevel.get()));
						proj.getPersistentData().putBoolean(SNIPE, true);
						proj.getPersistentData().putInt(SNIPE_LVL, level);
					}
					
					if(living.getPersistentData().contains(CELL_DIVISION))
					{
						if(proj.getPersistentData().getInt(CELL_DIVISION_NUMBER) <= 0)
						{
							int level = living.getPersistentData().getInt(CELL_DIVISION_LVL);
							proj.getPersistentData().putBoolean(CELL_DIVISION, true);
							proj.getPersistentData().putInt(CELL_DIVISION_LVL, level);
							proj.getPersistentData().putInt(CELL_DIVISION_NUMBER, 0);
							proj.getPersistentData().putFloat(CELL_DIVISION_SCALE, 1.0F);
							
							living.getPersistentData().remove(CELL_DIVISION);
							living.getPersistentData().remove(CELL_DIVISION_LVL);
						}
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.CELL_DIVISION.get()) > 0)
					{
						if(proj.getPersistentData().getInt(CELL_DIVISION_NUMBER) <= 0)
						{
							int level = stack.getEnchantmentLevel(CustomEnchantments.CELL_DIVISION.get());
							proj.getPersistentData().putBoolean(CELL_DIVISION, true);
							proj.getPersistentData().putInt(CELL_DIVISION_LVL, level);
							proj.getPersistentData().putInt(CELL_DIVISION_NUMBER, 0);
							proj.getPersistentData().putFloat(CELL_DIVISION_SCALE, 1.0F);
						}
					}
					
					if(living.getPersistentData().contains(WATER_JET))
					{
						if(living.isEyeInFluidType(ForgeMod.WATER_TYPE.get()))
						{
							proj.setNoGravity(true);
							proj.getPersistentData().putBoolean(WATER_JET, true);
							living.getPersistentData().remove(WATER_JET);
						}
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.WATER_JET.get()) > 0)
					{
						if(living.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && living.getProjectile(stack).getOrCreateTag().contains(WATER_JET))
						{
							proj.setNoGravity(true);
							proj.getPersistentData().putBoolean(WATER_JET, true);
						}
					}
					
					if(living.getPersistentData().contains(SKILLFUL))
					{
						float damage = living.getPersistentData().getFloat(SKILLFUL_DMG);
						proj.getPersistentData().putBoolean(SKILLFUL, true);
						proj.getPersistentData().putFloat(SKILLFUL_DMG, damage);
						
						living.getPersistentData().remove(SKILLFUL);
						living.getPersistentData().remove(SKILLFUL_DMG);
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get()) > 0)
					{
						float damage = stack.getOrCreateTag().getFloat(SKILLFUL_DMG);
						proj.getPersistentData().putBoolean(SKILLFUL, true);
						proj.getPersistentData().putFloat(SKILLFUL_DMG, damage);
					}
					
					if(living.getPersistentData().contains(TELEPORTATION))
					{
						proj.getPersistentData().putBoolean(TELEPORTATION, true);
						
						living.getPersistentData().remove(TELEPORTATION);
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.TELEPORTATION.get()) > 0)
					{
						proj.getPersistentData().putBoolean(TELEPORTATION, true);
					}
					
					if(living.getPersistentData().contains(HOMING))
					{
						int level = living.getPersistentData().getInt(HOMING_LVL);
						
						proj.getPersistentData().putBoolean(HOMING, true);
						proj.getPersistentData().putInt(HOMING_LVL, level);
						
						living.getPersistentData().remove(HOMING);
						living.getPersistentData().remove(HOMING_LVL);
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.HOMING.get()) > 0)
					{
						int level = stack.getEnchantmentLevel(CustomEnchantments.HOMING.get());
						proj.getPersistentData().putInt(HOMING_LVL, level);
						proj.getPersistentData().putBoolean(HOMING, true);
					}
					
					if(living.getPersistentData().contains(SOUL_OF_TERRARIAN))
					{
						int level = living.getPersistentData().getInt(SOUL_OF_TERRARIAN_LVL);
						proj.getPersistentData().putBoolean(SOUL_OF_TERRARIAN, true);
						proj.getPersistentData().putInt(SOUL_OF_TERRARIAN_LVL, level);
						
						living.getPersistentData().remove(SOUL_OF_TERRARIAN);
						living.getPersistentData().remove(SOUL_OF_TERRARIAN_LVL);
					}
					else if(stack.getEnchantmentLevel(CustomEnchantments.SOUL_OF_TERRARIAN.get()) > 0)
					{
						int level = stack.getEnchantmentLevel(CustomEnchantments.SOUL_OF_TERRARIAN.get());
						proj.getPersistentData().putBoolean(SOUL_OF_TERRARIAN, true);
						proj.getPersistentData().putInt(SOUL_OF_TERRARIAN_LVL, level);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		LivingEntity living = event.getEntity();
		ItemStack offHandStack = living.getOffhandItem();
		
		if(offHandStack.getItem() instanceof ShieldItem)
		{
			if(offHandStack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get()) > 0 && EnchantmentUtil.isDamageSourceBlocked(living, event.getSource()))
			{
				int level = offHandStack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get());
				if(Math.random() <= (level * EnchantmentConfig.autoShieldingChancePerLevel.get()) / 100)
				{
					int shielding = living.getPersistentData().getInt(AUTO_SHIELDING);
					if(shielding <= 0)
					{
						living.getPersistentData().putInt(AUTO_SHIELDING, 15);
						float amount = event.getAmount();
						if(amount > 0.0F)
						{
							net.minecraftforge.event.entity.living.ShieldBlockEvent ev = net.minecraftforge.common.ForgeHooks.onShieldBlock(living, event.getSource(), amount);
							if(!ev.isCanceled())
							{
								if(ev.shieldTakesDamage())
								{
									living.hurtCurrentlyUsedShield(amount);
								}
								amount -= ev.getBlockedDamage();
								event.setAmount(amount);
								if (!event.getSource().is(DamageTypeTags.IS_PROJECTILE))
								{
									Entity entity = event.getSource().getDirectEntity();
									if (entity instanceof LivingEntity) 
									{
										LivingEntity livingentity = (LivingEntity)entity;
										living.blockUsingShield(livingentity);
									}
								}
								
								if(amount <= 0)
								{
									living.level().broadcastEntityEvent(living, (byte)29);
								}
								event.setCanceled(true);
							}
						}
					}
				}
			}
		}
		
		if(event.getSource().getEntity() != null)
		{
			Entity entity = event.getSource().getEntity();
			
			if(entity instanceof LivingEntity sourceEntity)
			{
				ItemStack stack = sourceEntity.getMainHandItem();
				if(stack.getEnchantmentLevel(CustomEnchantments.MALICE.get()) > 0)
				{
					int level = stack.getEnchantmentLevel(CustomEnchantments.MALICE.get());
					float percent = level * EnchantmentConfig.malicePercentagePerLevel.get();
					float damage = (living.getHealth() * percent) / 100; 
					event.setAmount(event.getAmount() + damage);
				}
				
				if(stack.getEnchantmentLevel(CustomEnchantments.ARMOR_CRACK.get()) > 0)
				{
					int level = stack.getEnchantmentLevel(CustomEnchantments.ARMOR_CRACK.get());
					double armorPoint = living.getAttributeBaseValue(Attributes.ARMOR);
					float damage = (float) ((armorPoint * (level * EnchantmentConfig.armorCrackDamagePerLevel.get())) / 100);
					event.setAmount(event.getAmount() + damage);
				}
			}
			
			Iterable<ItemStack> armors = living.getArmorSlots();
			armors.forEach((stack) ->
			{
				if(stack.getEnchantmentLevel(CustomEnchantments.HARDENING.get()) > 0)
				{
					int level = stack.getEnchantmentLevel(CustomEnchantments.HARDENING.get());
					if(stack.getTag().contains(HARDENING))
					{
						float armor = stack.getTag().getFloat(HARDENING);
						float original = stack.getTag().getFloat(HARDENING_ORIGINAL);
						if(original + armor < original + (level * EnchantmentConfig.hardeningMaxArmorPerLevel.get()))
						{
							stack.getTag().putFloat(HARDENING, armor + (level * EnchantmentConfig.hardeningArmorPerLevel.get()));
						}
					}
				}
			});
			
			if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.LORD_OF_THE_SEA.get(), living) > 0 && entity instanceof LivingEntity source && living.isInWater())
			{
				int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.LORD_OF_THE_SEA.get(), living);
				List<WaterAnimal> list = living.level().getEntitiesOfClass(WaterAnimal.class, living.getBoundingBox().inflate(level * EnchantmentConfig.lordoftheseaRadiusPerLevel.get()));
				if(list.size() <= level * EnchantmentConfig.lordoftheseaMaxAnimalsAmountPerLevel.get())
				{
					list.forEach((waterAnimal) ->
					{
						waterAnimal.getPersistentData().putUUID(LORD_OF_THE_SEA, living.getUUID());
						waterAnimal.getPersistentData().putFloat(LORD_OF_THE_SEA_DMG, level * EnchantmentConfig.lordoftheseaAnimalsDamagePerLevel.get());
						waterAnimal.setTarget(source);
					});
				}
			}
			
			if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.WAVES_PROTECTION.get(), living) > 0)
			{
				int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.WAVES_PROTECTION.get(), living);
				float wave = living.getPersistentData().getFloat(WAVES_PROTECTION);
				if(living.isInWater() && wave <= 0)
				{
					living.getPersistentData().putFloat(WAVES_PROTECTION, level * (EnchantmentConfig.wavesProtectionDurationPerLevel.get() * 20));
				}
			}
		}
		
		if(event.getSource().getDirectEntity() != null)
		{
			Entity entity = event.getSource().getDirectEntity();
			
			if(entity instanceof Projectile proj)
			{
				Entity owner = proj.getOwner();
				
				if(owner != null)
				{
					if(proj.getPersistentData().contains(SNIPE))
					{
						int level = proj.getPersistentData().getInt(SNIPE_LVL);
						event.setAmount(event.getAmount() + (level * EnchantmentConfig.snipeAdditionalDamagePerLevel.get()));
					}
					
					if(proj.getPersistentData().contains(SKILLFUL))
					{
						float damage = proj.getPersistentData().getFloat(SKILLFUL_DMG);
						event.setAmount(event.getAmount() + damage);
					}
					
					if(proj instanceof ThrownTrident trident)
					{
						if(trident.getPersistentData().contains(SHARP_WAVES) && owner.isInWater())
						{
							BlockPos pos = NbtUtils.readBlockPos(trident.getPersistentData().getCompound(SHARP_WAVES));
							double distance = trident.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
							int level = trident.getPersistentData().getInt(SHARP_WAVES_LVL);
							float damage = trident.getPersistentData().getFloat(SHARP_WAVES_DMG);
							if(distance % (EnchantmentConfig.sharpWavesDistancePerLevel.get() / level) == 0)
							{
								if(damage <= level * EnchantmentConfig.sharpWavesMaxDamagePerLevel.get())
								{
									trident.getPersistentData().putFloat(SHARP_WAVES_DMG, damage + (level * EnchantmentConfig.sharpWavesDamagePerLevel.get()));
								}
								if(damage > level * EnchantmentConfig.sharpWavesMaxDamagePerLevel.get())
								{
									trident.getPersistentData().putFloat(SHARP_WAVES_DMG, level * EnchantmentConfig.sharpWavesMaxDamagePerLevel.get());
								}
							}
							event.setAmount(event.getAmount() + damage);
						}
						
						if(trident.getPersistentData().contains(POSEIDONS_GRACE) && !trident.getPersistentData().contains(POSEIDONS_GRACE_SUMMONED))
						{
							int level = ItemStack.of(trident.getPersistentData().getCompound(POSEIDONS_GRACE_ITEM)).getEnchantmentLevel(CustomEnchantments.POSEIDONS_GRACE.get());
							for(int i = 0; i < level * 2; i++)
							{
								double spawnRange = level * 5;
			                    double x = (double)owner.getX() + (trident.level().getRandom().nextDouble() - trident.level().getRandom().nextDouble()) * (double)spawnRange + 0.5D;
			                    double z = (double)owner.getZ() + (trident.level().getRandom().nextDouble() - trident.level().getRandom().nextDouble()) * (double)spawnRange + 0.5D;
			                    
			                    if(trident.level().getFluidState(BlockPos.containing(x, event.getEntity().getY() - 1, z)).is(Fluids.WATER) || trident.level().getFluidState(BlockPos.containing(x, event.getEntity().getY() - 1, z)).is(Fluids.FLOWING_WATER))
			                    {
									ThrownTrident summonedTrident = (ThrownTrident) trident.getType().create(trident.level());
									ObfuscationReflectionHelper.setPrivateValue(ThrownTrident.class, summonedTrident, ItemStack.of(trident.getPersistentData().getCompound(POSEIDONS_GRACE_ITEM)), "f_37555_");
									summonedTrident.setOwner(trident.getOwner());
									summonedTrident.setPos(x, event.getEntity().getY() - 1, z);
									summonedTrident.setDeltaMovement(0, 0.35, 0);
									summonedTrident.setNoGravity(true);
									summonedTrident.pickup = AbstractArrow.Pickup.DISALLOWED;
									summonedTrident.getPersistentData().putBoolean(POSEIDONS_GRACE_SUMMONED, true);
									summonedTrident.getPersistentData().putUUID(POSEIDONS_GRACE_TARGET, event.getEntity().getUUID());
									trident.level().addFreshEntity(summonedTrident);
			                    }
							}
						}
						
						if(trident.getPersistentData().contains(POSEIDONS_GRACE_SUMMONED))
						{
							event.getEntity().invulnerableTime = 0;
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onProjectileImpact(ProjectileImpactEvent event)
	{
		Projectile proj = event.getProjectile();
		Entity owner = proj.getOwner();
		
		if(proj instanceof ThrownTrident trident)
		{
			if(trident.getPersistentData().contains(POSEIDONS_GRACE_SUMMONED))
			{
				trident.discard();
			}
		}
		
		if(event.getRayTraceResult() instanceof EntityHitResult entityHit)
		{
			Entity entity = entityHit.getEntity();
			if(entity instanceof LivingEntity living)
			{
				if(living.getOffhandItem().getEnchantmentLevel(CustomEnchantments.MIRROR.get()) > 0 && living.isBlocking())
				{
					int level = living.getOffhandItem().getEnchantmentLevel(CustomEnchantments.MIRROR.get());
					if(Math.random() <= (level * EnchantmentConfig.mirrorReflectChancePerLevel.get()) / 100)
					{
						proj.setDeltaMovement(proj.getDeltaMovement().reverse());
						proj.setOwner(living);
						proj.hasImpulse = true;
					}
				}
				
				if(proj.getPersistentData().contains(SOUL_OF_TERRARIAN) && !proj.getPersistentData().contains(SOUL_OF_TERRARIAN_SUMMONED))
				{
					int level = proj.getPersistentData().getInt(SOUL_OF_TERRARIAN_LVL);
					for(int i = 0; i < level * EnchantmentConfig.soulOfTerrarianProjAmountPerLevel.get(); i++)
					{
						Projectile extra = (Projectile) proj.getType().create(proj.level());
						double spawnRange = living.getBbWidth() + level;
	                    double x = (double)living.getX() + (proj.level().getRandom().nextDouble() - proj.level().getRandom().nextDouble()) * (double)spawnRange + 0.5D;
	                    double y = (double)(living.getY() + living.getEyeHeight() + proj.level().getRandom().nextInt(2) - 1);
	                    double z = (double)living.getZ() + (proj.level().getRandom().nextDouble() - proj.level().getRandom().nextDouble()) * (double)spawnRange + 0.5D;
	                    extra.setPos(x, y, z);
	                    extra.setOwner(proj.getOwner());
	                    Vec3 vec = EnchantmentUtil.fromToVector(extra.position(), living.position().add(0, living.getEyeHeight(), 0), 1);
	                    extra.setDeltaMovement(vec);
	                    extra.getPersistentData().putBoolean(SOUL_OF_TERRARIAN_SUMMONED, true);
	                    extra.hasImpulse = true;
	                    if(extra instanceof AbstractArrow arrow)
	                    {
	                    	arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
	                    }
	                    proj.level().addFreshEntity(extra);
					}
				}
				
				if(proj.getPersistentData().contains(SOUL_OF_TERRARIAN_SUMMONED))
				{
					living.invulnerableTime = 0;
					proj.discard();
				}
			}
		}
		
		if(owner != null)
		{
			if(proj instanceof ThrownPotion thrownPotion)
			{
				Potion potion = PotionUtils.getPotion(thrownPotion.getItem());
				List<MobEffectInstance> list = PotionUtils.getMobEffects(thrownPotion.getItem());
				boolean flag = potion == Potions.WATER && list.isEmpty();
				List<Player> players = thrownPotion.level().getEntitiesOfClass(Player.class, thrownPotion.getBoundingBox().inflate(1.5));
				players.forEach((player) ->
				{
					for(InteractionHand hand : InteractionHand.values())
					{
						if(player.getItemInHand(hand).getEnchantmentLevel(CustomEnchantments.WATER_JET.get()) > 0)
						{
							ItemStack stack = player.getItemInHand(hand);
							if(stack.getItem() instanceof CrossbowItem crossbow)
							{
								if(flag)
								{
									CrossbowItem.setCharged(stack, true);
									EnchantmentUtil.addChargedProjectile(stack, new ItemStack(Items.ARROW));
								}
							}
						}
					}
				});
			}
			
			if(proj.getPersistentData().contains(TELEPORTATION))
			{
				owner.setPos(event.getRayTraceResult().getLocation());
			}
			
			if(proj.getPersistentData().contains(WATER_JET))
			{
				if(proj instanceof AbstractArrow arrow)
				{
					arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
				}
			}
			
			if(proj.getPersistentData().contains(MINER))
			{
				int level = proj.getPersistentData().getInt(MINER_LVL);
				int count = proj.getPersistentData().getInt(MINER_COUNT);
				
				if(count < level * EnchantmentConfig.minerMaxBlockPerLevel.get() && event.getRayTraceResult().getType() == HitResult.Type.BLOCK)
				{
					BlockPos pos = ((BlockHitResult) event.getRayTraceResult()).getBlockPos();
					BlockState state = proj.level().getBlockState(pos);
					if(state.getDestroySpeed(proj.level(), pos) > -1.0F)
					{
						if(proj.level().destroyBlock(pos, true, owner))
						{	
							proj.getPersistentData().putInt(MINER_COUNT, count + 1);
						}
					}
				}
			}
			
			if(proj.getPersistentData().contains(CELL_DIVISION) || proj.getPersistentData().contains(CELL_DIVISION_NUMBER))
			{
				int level = proj.getPersistentData().getInt(CELL_DIVISION_LVL);
				int number = proj.getPersistentData().getInt(CELL_DIVISION_NUMBER);
				float scale = proj.getPersistentData().getFloat(CELL_DIVISION_SCALE);
				float maxNumber = level * EnchantmentConfig.cellDivisionMaxSplitPerLevel.get();
				if(number < maxNumber)
				{
					for(int i = 0; i < (level * EnchantmentConfig.cellDivisionSplitAmountPerLevel.get()); i++)
					{
						Projectile cell = (Projectile) proj.getType().create(proj.level());
						cell.setOwner(owner);
						cell.getPersistentData().putInt(CELL_DIVISION_LVL, level);
						cell.getPersistentData().putInt(CELL_DIVISION_NUMBER, number + 1);
						cell.getPersistentData().putFloat(CELL_DIVISION_SCALE, scale - EnchantmentConfig.cellDivisionScalePerSplit.get());
						cell.setPos(proj.position().add(0, 0.5, 0));
						if(proj instanceof AbstractArrow arrow && cell instanceof AbstractArrow cellArrow)
						{
							cellArrow.setBaseDamage(arrow.getBaseDamage());
							cellArrow.setKnockback(arrow.getKnockback());
							cellArrow.setPierceLevel(arrow.getPierceLevel());
							if(arrow.isOnFire())
							{
								cellArrow.setSecondsOnFire(100);
							}
						}
						Level world = proj.level();
						cell.setDeltaMovement(world.random.nextGaussian() * 0.2D, 0.4D, world.random.nextGaussian() * 0.2D);
						world.addFreshEntity(cell);
						if(!world.isClientSide)
						{
							EnchantmentNetwork.sendToAll(new CellScaleSyncPacket(cell.getId(), cell.getPersistentData().getFloat(CELL_DIVISION_SCALE)));
						}
					}
				}
				
				if(proj instanceof AbstractArrow arrow)
				{
					if(number > 0)
					{
						arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
					}
				}
				
				if(number > 0)
				{
					proj.discard();
				}
			}
		}
		
		if(proj.getPersistentData().contains(RECOCHET) && event.getRayTraceResult().getType() == HitResult.Type.BLOCK)
		{
		    int bounce = proj.getPersistentData().getInt(RECOCHET_BOUNCE);
		    int level = proj.getPersistentData().getInt(RECOCHET_LVL);
		    double motionx = proj.getDeltaMovement().x;
		    double motiony = proj.getDeltaMovement().y;
		    double motionz = proj.getDeltaMovement().z;

			Direction direction = ((BlockHitResult) event.getRayTraceResult()).getDirection();
			
			if(direction == Direction.EAST) 
			{
				motionx = -motionx;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if(direction == Direction.SOUTH) 
			{
				motionz = -motionz;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if(direction == Direction.WEST) 
			{
				motionx = -motionx;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if(direction == Direction.NORTH)
			{
				motionz = -motionz;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if(direction == Direction.UP)
			{
				motiony = -motiony;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if(direction == Direction.DOWN)
			{
				motiony = -motiony;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			
			if(bounce < level * EnchantmentConfig.recochetBouncePerLevel.get()) 
			{
				proj.setDeltaMovement(motionx, motiony, motionz);
			}
		}
	}
	
	@SubscribeEvent
	public static void onLevelTick(LevelTickEvent event)
	{
		if(event.level instanceof ServerLevel serverLevel)
		{
			for(Entity entity : serverLevel.getAllEntities())
			{
				if(entity instanceof Projectile proj)
				{
					if(proj.getOwner() != null)
					{
						Entity owner = proj.getOwner();
						if(proj instanceof AbstractArrow arrow)
						{
							if((arrow.getPersistentData().contains(WATER_BOLT) || arrow.getPersistentData().contains(WATER_JET)) && arrow.isInWater())
							{
								float waterInertia = ((AbstractArrowInvoker) arrow).Invoke_getWaterInertia();
								arrow.setDeltaMovement(arrow.getDeltaMovement().x / waterInertia, arrow.getDeltaMovement().y / waterInertia, arrow.getDeltaMovement().z / waterInertia);
							}
						}
						
						if(proj.getPersistentData().contains(HOMING) && !proj.onGround())
						{
							int level = proj.getPersistentData().getInt(HOMING_LVL);
							List<LivingEntity> list = proj.level().getEntitiesOfClass(LivingEntity.class, proj.getBoundingBox().inflate(level));
							list.removeIf((living) -> living == owner);
							list.removeIf((living) -> living instanceof Player && EnchantmentConfig.disableHomingPlayers.get());
							list.forEach((living) ->
							{
								int time = proj.getPersistentData().getInt(HOMING_TIME);
								if(time < level * (EnchantmentConfig.homingMaxHomingTimePerLevel.get() * 20))
								{
									Vec3 vec = EnchantmentUtil.fromToVector(proj.position(), living.position().add(0, 0.5F, 0), 0.5F * level);
									proj.setDeltaMovement(vec);
									proj.hasImpulse = true;
									proj.getPersistentData().putInt(HOMING_TIME, time + 1);
								}
							});
						}
						
						if(proj instanceof ThrownTrident trident)
						{
							if(trident.getPersistentData().contains(POSEIDONS_GRACE_SUMMONED))
							{
								if(trident.tickCount >= 20)
								{
									if(trident.level() instanceof ServerLevel level)
									{
										Entity target = level.getEntity(trident.getPersistentData().getUUID(POSEIDONS_GRACE_TARGET));
										if(target != null)
										{
											trident.setDeltaMovement(EnchantmentUtil.fromToVector(trident.position(), target.position().add(0, target.getEyeHeight(), 0), EnchantmentConfig.poseidonsGraceTridentSpeed.get()));
										}
										else
										{
											trident.discard();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event)
    {
        ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		
    	if(EnchantmentConfig.noIncreasingRepairCost.get())
    	{
    		resetRepairValue(event.getOutput());
    		resetRepairValue(event.getLeft());
    		resetRepairValue(event.getRight());
    	}
		
		if(!left.isEmpty() || !right.isEmpty())
		{
            if(Items.BOOK == right.getItem() && EnchantmentConfig.disenchanting.get()) 
            {
                boolean isEnchantedBook = false;
                ListTag enchTags;
                if(left.getItem() instanceof EnchantedBookItem)
                {
                    enchTags = EnchantedBookItem.getEnchantments(left);
                    isEnchantedBook = true;
                }
                else
                {
                    enchTags = left.getEnchantmentTags();
                }
                
                if(!(enchTags.isEmpty() || (isEnchantedBook && enchTags.size() <= 1))) 
                {
                    ItemStack out = left.copy();
                    ListTag newTags = enchTags.copy();
                    newTags.remove(0);
                    out.addTagElement(isEnchantedBook ? "StoredEnchantments" : "Enchantments", newTags);

                    CompoundTag ench = enchTags.getCompound(0);
                    int cost = getApplyCost(getEnchantmentFromStringId(ench.getString("id")), 1);

                    event.setOutput(out);
                    event.setCost(cost);
                    event.setMaterialCost(1);
                }
            }
            
            if(EnchantmentConfig.anvilOverlevelBooks.get() || EnchantmentConfig.anvilAlwaysAllowBooks.get())
            {
                int cost = 0;
                Map<Enchantment, Integer> currentEnchants = EnchantmentHelper.getEnchantments(left);

                for(Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(right).entrySet())
                {
                    Enchantment ench = entry.getKey();
                    int level = entry.getValue();
                    if(null == ench || 0 == level) { continue; }
                    
                    if(canApplyEnchant(left, ench)) 
                    {
                        int currentLevel = 0;
                        if(currentEnchants.containsKey(ench))
                        {
                            currentLevel = currentEnchants.get(ench);
                        }

                        if(currentLevel > level) { continue; }

                        int outLevel = (currentLevel == level) ? level + 1 : level;
                        
                        if(!EnchantmentConfig.anvilOverlevelBooks.get()) 
                        {
                            outLevel = Math.min(outLevel, ench.getMaxLevel());
                        }

                        if(outLevel > currentLevel) 
                        {
                            currentEnchants.put(ench, outLevel);
                            cost += getApplyCost(ench, outLevel);
                        }
                    }
                    
                    if(cost > 0)
                    {
                        ItemStack out = left.copy();
                        EnchantmentHelper.setEnchantments(currentEnchants, out);
                        String name = event.getName();
                        if(name != null && !name.isEmpty() && name != out.getDisplayName().getString())
                        {
                            out.setHoverName(Component.literal(name));
                            cost++;
                        }
                        event.setOutput(out);
                        event.setCost(cost);
                    }
                    else 
                    {
                        event.setCanceled(true);
                    }
                }
            }
		}
    }
    
    public static boolean canApplyEnchant(ItemStack i, Enchantment e)
    {
    	if(EnchantmentConfig.anvilAlwaysAllowBooks.get() && i.getMaxStackSize() == 1)
    	{ 
    		return true; 
    	}
    	 
        if(Items.ENCHANTED_BOOK == i.getItem()) 
        { 
        	return true; 
        }
        
        if(!e.canEnchant(i)) 
        {
        	return false;
        }
        
        for(Enchantment enchCompare : EnchantmentHelper.getEnchantments(i).keySet()) 
        {
            if(enchCompare != null && enchCompare != e && !enchCompare.isCompatibleWith(e))
            {
            	return false;
            }
        }
        return true;
    }
    
	public static void resetRepairValue(ItemStack stack) 
	{
		if(!stack.isEmpty() && stack.hasTag())
		{
			stack.getTag().remove("RepairCost");
		}
	}
    
    @SubscribeEvent
    public static void onAnvilRepair(AnvilRepairEvent event)  
    {
    	if(EnchantmentConfig.disenchanting.get())
    	{
            if(Items.BOOK == event.getRight().getItem())
            {
                ItemStack left = event.getLeft();

                boolean isEnchantedBook = false;
                ListTag enchTags;
                if(left.getItem() instanceof EnchantedBookItem) 
                {
                    enchTags = EnchantedBookItem.getEnchantments(left);
                    isEnchantedBook = true;
                }
                else 
                {
                    enchTags = left.getEnchantmentTags();
                }
                if(!(enchTags.isEmpty() || (isEnchantedBook && enchTags.size() <= 1))) 
                {
                    CompoundTag ench = enchTags.getCompound(0);

                    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                    ListTag newTags = new ListTag();
                    newTags.add(ench);
                    book.getOrCreateTag().put("StoredEnchantments", newTags);

                    Player player = event.getEntity();
                    Containers.dropItemStack(player.level(), player.getX(), player.getY(), player.getZ(), book);
                }
            }
    	}
    }
    
    private static int getApplyCost(Enchantment e, int lvl)
    {
        int rarityFactor =  10; // 10 for unknown rarity - should NEVER happen
                            
        Enchantment.Rarity r = e.getRarity();
        if(Enchantment.Rarity.COMMON == r)         { rarityFactor = 1; }
        else if(Enchantment.Rarity.UNCOMMON == r)   { rarityFactor = 2; }
        else if(Enchantment.Rarity.RARE == r)       { rarityFactor = 4; }
        else if(Enchantment.Rarity.VERY_RARE == r)  { rarityFactor = 8; }
        
        return rarityFactor * lvl;
    }

    static private Enchantment getEnchantmentFromStringId(String id)
    {
        return ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(id));
    }
}
