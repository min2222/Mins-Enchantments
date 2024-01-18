package com.min01.minsenchantments.misc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.collect.Multimap;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Bus.FORGE)
public class EventHandlerForge
{
	public static final String WATER_BOLT = "WaterBolt";
	public static final String SHARP_WAVES = "SharpWaves";
	public static final String SHARP_WAVES_LVL = "SharpWavesLvl";
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
	public static final String RECOCHET = "Recochet";
	public static final String RECOCHET_BOUNCE = "RecochetBounce";
	public static final String WALLBREAK = "Wallbreak";
	public static final String SNIPE = "Snipe";
	public static final String SNIPE_LVL = "SnipeLvL";
	public static final String SNIPE_TICK = "SnipeTick";
	public static final String AUTO_SHIELDING = "AutoShielding";
	public static final String CELL_DIVISION = "CellDivision";
	public static final String CELL_DIVISION_LVL = "CellDivisionLvl";
	public static final String CELL_DIVISION_NUMBER = "CellDivisionNumber";
	public static final String CELL_DIVISION_SCALE = "CellDivisionScale";
	public static final String ACCELERATE = "Accelerate";
	public static final String ACCELERATE_ORIGINAL = "AccelerateOriginal";
	public static final String MINER = "Miner";
	public static final String MINER_LVL = "MinerLvL";
	public static final String MINER_COUNT = "MinerCount";
	
	public static final Map<Class<? extends Entity>, Object> ENTITY_MAP = new HashMap<>();
	
	@SubscribeEvent
	public static void onLivingEntityStopUseItem(LivingEntityUseItemEvent.Stop event)
	{
		LivingEntity living = event.getEntity();
		ItemStack stack = event.getItem();
		
		if(stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get()) > 0)
		{
			living.getPersistentData().putInt(SNIPE_TICK, stack.getUseDuration());
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get()) > 0)
		{
			living.getPersistentData().putInt(SNIPE_TICK, stack.getUseDuration());
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.SHARP_WAVES.get()) > 0)
		{
			int level = stack.getEnchantmentLevel(CustomEnchantments.SHARP_WAVES.get());
			living.getPersistentData().putBoolean(SHARP_WAVES, true);
			living.getPersistentData().putInt(SHARP_WAVES_LVL, level);
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.POSEIDONS_GRACE.get()) > 0)
		{
			living.getPersistentData().putBoolean(POSEIDONS_GRACE, true);
			living.getPersistentData().put(POSEIDONS_GRACE_ITEM, stack.save(stack.getTag()));
		}
		
		if(stack.getEnchantmentLevel(CustomEnchantments.RECOCHET.get()) > 0)
		{
			living.getPersistentData().putBoolean(RECOCHET, true);
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
		
		if(source != null && source instanceof LivingEntity attacker)
		{
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
				int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.FLAME_THORN.get(), living);
				source.hurt(living.damageSources().thorns(living), level * EnchantmentConfig.flameThornDamagePerLevel.get());
				source.setSecondsOnFire((level * EnchantmentConfig.flameThornFireDurationPerLevel.get()) * 20);
			}
			
			if(attacker.getMainHandItem().getEnchantmentLevel(CustomEnchantments.ACCELERATE.get()) > 0)
			{
				ItemStack stack = attacker.getMainHandItem();
				int level = stack.getEnchantmentLevel(CustomEnchantments.ACCELERATE.get());
				if(stack.getTag().contains(ACCELERATE))
				{
					float speed = stack.getTag().getFloat(ACCELERATE);
					//float original = stack.getTag().getFloat(ACCELERATE_ORIGINAL);
					//TODO max speed implementation
					stack.getTag().putFloat(ACCELERATE, speed + (level * EnchantmentConfig.accelerateSpeedPerLevel.get()));
				}
			}
		}
		
		if(living.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ShieldItem)
		{
			ItemStack stack = living.getItemBySlot(EquipmentSlot.OFFHAND);
			if(stack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get()) > 0 && EnchantmentUtil.isDamageSourceBlocked(living, event.getSource()))
			{
				int level = stack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get());
				if(Math.random() <= (level * EnchantmentConfig.autoShieldingChancePerLevel.get()) / 100)
				{
					int shielding = living.getPersistentData().getInt(AUTO_SHIELDING);
					if(shielding <= 0)
					{
						living.getPersistentData().putInt(AUTO_SHIELDING, 20);
					}
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
			
			if(level >= 5)
			{
				living.level().explode(null, living.getX(), living.getY(), living.getZ(), level * 2, ExplosionInteraction.NONE);
			}
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
			else
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
		
		if(player.isUsingItem())
		{
			ItemStack stack = player.getItemInHand(player.getUsedItemHand());
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
				player.setDeltaMovement(motion.x, player.getDeltaMovement().y, motion.z);
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
			
			list.forEach((entity) -> 
			{
				//FIXME not working
				ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, true, "f_19798_");
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
			else if(living.getPersistentData().contains(AUTO_SHIELDING))
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
					living.setDeltaMovement(living.getDeltaMovement().x, 0.05 + (living.getDeltaMovement().y + climbSpeed), living.getDeltaMovement().z);
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
		ENTITY_MAP.put(event.getEntity().getClass(), event.getEntity());
		
		if(event.getEntity() instanceof AbstractArrow arrow)
		{
			if(arrow.getOwner() != null)
			{
				Entity entity = arrow.getOwner();
				if(entity instanceof LivingEntity living)
				{
					ItemStack stack = living.getItemInHand(living.getUsedItemHand());
					if(stack.getEnchantmentLevel(CustomEnchantments.WATERBOLT.get()) > 0)
					{
						arrow.getPersistentData().putBoolean(WATER_BOLT, true);
					}
				}
			}
		}
		
		if(event.getEntity() instanceof Projectile proj)
		{
			if(proj.getOwner() != null)
			{
				Entity entity = proj.getOwner();
				if(entity instanceof LivingEntity living)
				{
					if(living.getPersistentData().contains(MINER))
					{
						int level = living.getPersistentData().getInt(MINER_LVL);
						proj.getPersistentData().putBoolean(MINER, true);
						proj.getPersistentData().putInt(MINER_LVL, level);
						
						living.getPersistentData().remove(MINER);
						living.getPersistentData().remove(MINER_LVL);
					}
					
					if(living.getPersistentData().contains(RECOCHET))
					{
						proj.getPersistentData().putBoolean(RECOCHET, true);
						living.getPersistentData().remove(RECOCHET);
					}
					
					if(living.getPersistentData().contains(WALLBREAK))
					{
						proj.getPersistentData().putBoolean(WALLBREAK, true);
						living.getPersistentData().remove(WALLBREAK);
					}
					
					if(living.getPersistentData().contains(SNIPE))
					{
						int level = living.getPersistentData().getInt(SNIPE_LVL);
						EnchantmentUtil.setTickrate(proj, 20 + (level * EnchantmentConfig.snipeProjectileSpeedPerLevel.get()));
						proj.getPersistentData().putBoolean(SNIPE, true);
						proj.getPersistentData().putInt(SNIPE_LVL, level);
						
						living.getPersistentData().remove(SNIPE);
						living.getPersistentData().remove(SNIPE_LVL);
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
					
					if(living.getPersistentData().contains(WATER_JET))
					{
						if(living.isEyeInFluidType(ForgeMod.WATER_TYPE.get()))
						{
							proj.setNoGravity(true);
							proj.getPersistentData().putBoolean(WATER_JET, true);
							living.getPersistentData().remove(WATER_JET);
						}
					}
				}
			}
		}
		
		if(event.getEntity() instanceof ThrownTrident trident)
		{
			if(trident.getOwner() != null)
			{
				Entity entity = trident.getOwner();
				if(entity instanceof LivingEntity living)
				{
					if(living.getPersistentData().contains(SHARP_WAVES))
					{
						trident.getPersistentData().put(SHARP_WAVES, NbtUtils.writeBlockPos(trident.blockPosition()));
						trident.getPersistentData().putInt(SHARP_WAVES_LVL, living.getPersistentData().getInt(SHARP_WAVES_LVL));
						trident.getPersistentData().putFloat(SHARP_WAVES_DMG, 0);
						
						living.getPersistentData().remove(SHARP_WAVES);
						living.getPersistentData().remove(SHARP_WAVES_LVL);
					}
					
					if(living.getPersistentData().contains(POSEIDONS_GRACE))
					{
						ItemStack stack = ItemStack.of(living.getPersistentData().getCompound(POSEIDONS_GRACE_ITEM));
						trident.getPersistentData().putBoolean(POSEIDONS_GRACE, true);
						trident.getPersistentData().put(POSEIDONS_GRACE_ITEM, stack.save(stack.getTag()));
						
						living.getPersistentData().remove(POSEIDONS_GRACE);
						living.getPersistentData().remove(POSEIDONS_GRACE_ITEM);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		LivingEntity living = event.getEntity();
		ItemStack stack = living.getMainHandItem();
		
		if(event.getSource().getEntity() != null)
		{
			Entity entity = event.getSource().getEntity();
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
			
			if(stack.getEnchantmentLevel(CustomEnchantments.ARMOR_CRACK.get()) > 0)
			{
				int level = stack.getEnchantmentLevel(CustomEnchantments.ARMOR_CRACK.get());
				double armorPoint = living.getAttributeBaseValue(Attributes.ARMOR);
				float damage = (float) ((armorPoint * (level * EnchantmentConfig.armorCrackDamagePerLevel.get())) / 100);
				event.setAmount(event.getAmount() + damage);
			}
		}
		
		if(event.getSource().getDirectEntity() != null)
		{
			Entity entity = event.getSource().getDirectEntity();
			
			if(entity instanceof Projectile proj)
			{
				if(proj.getPersistentData().contains(SNIPE))
				{
					int level = proj.getPersistentData().getInt(SNIPE_LVL);
					event.setAmount(event.getAmount() + (level * EnchantmentConfig.snipeAdditionalDamagePerLevel.get()));
				}
			}
			
			if(entity instanceof ThrownTrident trident)
			{
				if(trident.getOwner() != null)
				{
					Entity owner = trident.getOwner();
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
		                    
		                    if(trident.level().getBlockState(BlockPos.containing(x, event.getEntity().getY() - 1, z)).liquid())
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
				//TODO
				//float maxNumber = level * EnchantmentConfig.cellDivisionMaxSplitPerLevel.get();
				if(number < 3)
				{
					for(int i = 0; i < (level * EnchantmentConfig.cellDivisionSplitAmountPerLevel.get()) + 1; i++)
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
					if(proj instanceof ThrownTrident)
					{
						if(number > 0)
						{
							arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
						}
					}
					else
					{
						arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
					}
				}
				
				if(event.getRayTraceResult().getType() == HitResult.Type.BLOCK)
				{
					if(proj instanceof ThrownTrident)
					{
						if(number > 0)
						{
							proj.discard();
						}
					}
					else
					{
						proj.discard();
					}
				}
			}
		}
		
		if(proj.getPersistentData().contains(RECOCHET) && event.getRayTraceResult().getType() == HitResult.Type.BLOCK)
		{
		    int bounce = proj.getPersistentData().getInt(RECOCHET_BOUNCE);
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
			
			if(bounce < EnchantmentConfig.recochetMaxBounce.get()) 
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
				if(entity instanceof AbstractArrow arrow)
				{
					if((arrow.getPersistentData().contains(WATER_BOLT) || arrow.getPersistentData().contains(WATER_JET)) && arrow.isInWater())
					{
						float waterInertia = ((AbstractArrowInvoker) arrow).Invoke_getWaterInertia();
						arrow.setDeltaMovement(arrow.getDeltaMovement().x / waterInertia, arrow.getDeltaMovement().y / waterInertia, arrow.getDeltaMovement().z / waterInertia);
					}
				}
				
				if(entity instanceof ThrownTrident trident)
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
