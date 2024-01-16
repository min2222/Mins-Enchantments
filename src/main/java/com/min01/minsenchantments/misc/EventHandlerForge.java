package com.min01.minsenchantments.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.mixin.AbstractArrowInvoker;
import com.min01.minsenchantments.network.CellScaleSyncPacket;
import com.min01.minsenchantments.network.EnchantmentNetwork;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
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
	public static final String RECOCHET = "Recochet";
	public static final String RECOCHET_BOUNCE = "RecochetBounce";
	public static final String WALLBREAK = "Wallbreak";
	public static final String SNIPE = "Snipe";
	public static final String SNIPE_LVL = "SnipeLvL";
	public static final String AUTO_SHIELDING = "AutoShielding";
	public static final String CELL_DIVISION = "CellDivision";
	public static final String CELL_DIVISION_LVL = "CellDivisionLvl";
	public static final String CELL_DIVISION_NUMBER = "CellDivisionNumber";
	public static final String CELL_DIVISION_SCALE = "CellDivisionScale";

	public static final Map<Class<? extends Entity>, Object> ENTITY_MAP = new HashMap<>();
	
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
				if(living.zza > 0.0F && living.getDeltaMovement().y < climbSpeed)
				{
					living.setDeltaMovement(living.getDeltaMovement().x, climbSpeed, living.getDeltaMovement().z);
					living.fallDistance = 0.0F;
				}
			}
		}
		
		if(living.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(CustomEnchantments.LAVA_WALKER.get()) > 0)
		{
	        BlockPos pos = living.blockPosition();
	        if (living.level().getBlockState(pos).getBlock() == Blocks.LAVA) 
	        {
	            if (living.getDeltaMovement().y < 0) 
	            {
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
		
		if(living instanceof Player player)
		{
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
				if(player.isInWater() && player.isSwimming())
				{
					if(tide < 1 + (level * EnchantmentConfig.tideMaxSpeedPerLevel.get()))
					{
						player.getPersistentData().putFloat(TIDE, tide + ((level * EnchantmentConfig.tideSpeedPerLevel.get()) / 20));
						EnchantmentUtil.speedupEntity(player, tide);
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
					if(player.isInWater() && player.isSwimming() && wave <= 95)
					{
						//FIXME not working
						EnchantmentUtil.speedupEntity(player, level * EnchantmentConfig.wavesProtectionSpeedPerLevel.get());
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
					ItemStack stack = living.getItemInHand(living.getUsedItemHand());
					if(stack.getEnchantmentLevel(CustomEnchantments.RECOCHET.get()) > 0)
					{
						proj.getPersistentData().putBoolean(RECOCHET, true);
					}
					
					if(stack.getEnchantmentLevel(CustomEnchantments.WALLBREAK.get()) > 0)
					{
						proj.getPersistentData().putBoolean(WALLBREAK, true);
					}
					
					if(stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get()) > 0)
					{
						int level = stack.getEnchantmentLevel(CustomEnchantments.SNIPE.get());
						EnchantmentUtil.setTickrate(proj, 20 + (level * EnchantmentConfig.snipeProjectileSpeedPerLevel.get()));
						proj.getPersistentData().putBoolean(SNIPE, true);
						proj.getPersistentData().putInt(SNIPE_LVL, level);
					}
					
					if(stack.getEnchantmentLevel(CustomEnchantments.CELL_DIVISION.get()) > 0)
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
					ItemStack stack = living.getItemInHand(living.getUsedItemHand());
					if(stack.getEnchantmentLevel(CustomEnchantments.SHARP_WAVES.get()) > 0)
					{
						trident.getPersistentData().put(SHARP_WAVES, NbtUtils.writeBlockPos(trident.blockPosition()));
						trident.getPersistentData().putInt(SHARP_WAVES_LVL, stack.getEnchantmentLevel(CustomEnchantments.SHARP_WAVES.get()));
						trident.getPersistentData().putFloat(SHARP_WAVES_DMG, 0);
					}
					
					if(stack.getEnchantmentLevel(CustomEnchantments.POSEIDONS_GRACE.get()) > 0)
					{
						trident.getPersistentData().putBoolean(POSEIDONS_GRACE, true);
						trident.getPersistentData().put(POSEIDONS_GRACE_ITEM, stack.save(new CompoundTag()));
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
								summonedTrident.setOwner(trident.getOwner());
								ObfuscationReflectionHelper.setPrivateValue(ThrownTrident.class, summonedTrident, ItemStack.of(trident.getPersistentData().getCompound(POSEIDONS_GRACE_ITEM)), "f_37555_");
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
		if(proj instanceof ThrownTrident trident)
		{
			if(trident.getPersistentData().contains(POSEIDONS_GRACE_SUMMONED))
			{
				trident.discard();
			}
		}
		
		if(proj.getPersistentData().contains(CELL_DIVISION) || proj.getPersistentData().contains(CELL_DIVISION_NUMBER))
		{
			int level = proj.getPersistentData().getInt(CELL_DIVISION_LVL);
			int number = proj.getPersistentData().getInt(CELL_DIVISION_NUMBER);
			float scale = proj.getPersistentData().getFloat(CELL_DIVISION_SCALE);
			//float maxNumber = level * EnchantmentConfig.cellDivisionMaxSplitPerLevel.get();
			//TODO
			if(number < 3)
			{
				for(int i = 0; i < level * EnchantmentConfig.cellDivisionSplitAmountPerLevel.get(); i++)
				{
					Projectile cell = (Projectile) proj.getType().create(proj.level());
					cell.setOwner(proj.getOwner());
					cell.getPersistentData().putInt(CELL_DIVISION_LVL, level);
					cell.getPersistentData().putInt(CELL_DIVISION_NUMBER, number + 1);
					cell.getPersistentData().putFloat(CELL_DIVISION_SCALE, scale - EnchantmentConfig.cellDivisionScalePerSplit.get());
					cell.setPos(proj.position().add(0, 0.5, 0));
					Level world = proj.level();
					cell.setDeltaMovement(world.random.nextGaussian() * 0.2D, 0.4D, world.random.nextGaussian() * 0.2D);
					world.addFreshEntity(cell);
					if(!world.isClientSide)
					{
						EnchantmentNetwork.sendToAll(new CellScaleSyncPacket(cell.getId(), cell.getPersistentData().getFloat(CELL_DIVISION_SCALE)));
					}
				}
			}
			
			if(event.getRayTraceResult().getType() == HitResult.Type.BLOCK)
			{
				proj.discard();
			}
		}
		
		if(proj.getPersistentData().contains(RECOCHET) && event.getRayTraceResult().getType() == HitResult.Type.BLOCK)
		{
		    int bounce = proj.getPersistentData().getInt(RECOCHET_BOUNCE);
		    double motionx = proj.getDeltaMovement().x;
		    double motiony = proj.getDeltaMovement().y;
		    double motionz = proj.getDeltaMovement().z;

			Direction direction = ((BlockHitResult) event.getRayTraceResult()).getDirection();
			
			if (direction == Direction.EAST) 
			{
				motionx = -motionx;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if (direction == Direction.SOUTH) 
			{
				motionz = -motionz;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if (direction == Direction.WEST) 
			{
				motionx = -motionx;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if (direction == Direction.NORTH)
			{
				motionz = -motionz;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if (direction == Direction.UP)
			{
				motiony = -motiony;
			    proj.getPersistentData().putInt(RECOCHET_BOUNCE, bounce + 1);
			}
			else if (direction == Direction.DOWN)
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
					if(arrow.getPersistentData().contains(WATER_BOLT) && arrow.isInWater())
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
    	if(EnchantmentConfig.noIncreasingRepairCost.get())
    	{
    		resetRepairValue(event.getOutput());
    		resetRepairValue(event.getLeft());
    		resetRepairValue(event.getRight());
    	}
    	if(EnchantmentConfig.disenchanting.get())
    	{	
            ItemStack left = event.getLeft();
    		ItemStack right = event.getRight();
    		
            if(left.isEmpty() || right.isEmpty()) { return; }
            if(Items.BOOK == right.getItem()) 
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
    	}
    }
    
	private static void resetRepairValue(ItemStack stack) 
	{
		if (!stack.isEmpty() && stack.hasTag())
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
