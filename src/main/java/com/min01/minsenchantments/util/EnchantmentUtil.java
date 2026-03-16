package com.min01.minsenchantments.util;

import java.lang.reflect.Method;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EnchantmentUtil
{
	public static final Method GET_ENTITY = ObfuscationReflectionHelper.findMethod(Level.class, "m_142646_");
	
	public static void setTickrateWithTime(Entity entity, int tickrate, int time)
	{
		entity.getPersistentData().putInt("ForceTickCountME", time);
		entity.getPersistentData().putInt("TickrateME", tickrate);
	}
	
	@SuppressWarnings("unchecked")
	public static Iterable<Entity> getAllEntities(Level level)
	{
		try 
		{
			LevelEntityGetter<Entity> entities = (LevelEntityGetter<Entity>) GET_ENTITY.invoke(level);
			return entities.getAll();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Entity getEntityByUUID(Level level, UUID uuid)
	{
		try 
		{
			LevelEntityGetter<Entity> entities = (LevelEntityGetter<Entity>) GET_ENTITY.invoke(level);
			return entities.get(uuid);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isDamageSourceBlocked(LivingEntity living, DamageSource pDamageSource) 
	{
		Entity entity = pDamageSource.getDirectEntity();
		boolean flag = false;
		if(entity instanceof AbstractArrow abstractarrow) 
		{
			if(abstractarrow.getPierceLevel() > 0) 
			{
				flag = true;
			}
		}
		if(!pDamageSource.is(DamageTypeTags.BYPASSES_SHIELD) && !flag)
		{
			Vec3 vec32 = pDamageSource.getSourcePosition();
			if(vec32 != null)
			{
				Vec3 vec3 = living.getViewVector(1.0F);
				Vec3 vec31 = vec32.vectorTo(living.position()).normalize();
				vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
				if(vec31.dot(vec3) < 0.0D)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static Vec3 getLookPos(float xRot, float yRot, float yPos, double distance)
	{
		float f = -Mth.sin(yRot * ((float)Math.PI / 180.0F)) * Mth.cos(xRot * ((float)Math.PI / 180.0F));
		float f1 = -Mth.sin((xRot + yPos) * ((float)Math.PI / 180F));
		float f2 = Mth.cos(yRot * ((float)Math.PI / 180.0F)) * Mth.cos(xRot * ((float)Math.PI / 180.0F));
		return new Vec3(f, f1, f2).scale(distance);
	}
	
	public static Vec3 getVelocityTowards(Vec3 from, Vec3 to, float speed)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion.scale(speed);
	}
	
	public static boolean removeWaterBreadthFirstSearch(Level pLevel, BlockPos pPos, int radius)
	{
		BlockState spongeState = pLevel.getBlockState(pPos);
		return BlockPos.breadthFirstTraversal(pPos, radius, 65, (pos, consumer) -> 
		{
			for(Direction direction : Direction.values()) 
			{
				consumer.accept(pos.relative(direction));
			}
		}, (pos) -> 
		{
			if(pos.equals(pPos)) 
			{
				return true;
			} 
			else 
			{
				BlockState blockstate = pLevel.getBlockState(pos);
				FluidState fluidstate = pLevel.getFluidState(pos);
				if(!spongeState.canBeHydrated(pLevel, pPos, fluidstate, pos)) 
				{
					return false;
				}
				else 
				{
					Block block = blockstate.getBlock();
					if(block instanceof BucketPickup) 
					{
						BucketPickup bucketpickup = (BucketPickup)block;
						if(!bucketpickup.pickupBlock(pLevel, pos, blockstate).isEmpty()) 
						{
							return true;
						}
					}
					if(blockstate.getBlock() instanceof LiquidBlock) 
					{
						pLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					}
					else 
					{
						if(!blockstate.is(Blocks.KELP) && !blockstate.is(Blocks.KELP_PLANT) && !blockstate.is(Blocks.SEAGRASS) && !blockstate.is(Blocks.TALL_SEAGRASS))
						{
							return false;
						}
						
						BlockEntity blockentity = blockstate.hasBlockEntity() ? pLevel.getBlockEntity(pos) : null;
						Block.dropResources(blockstate, pLevel, pos, blockentity);
						pLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					}
					return true;
				}
			}
		}) > 1;
	}
	
	public static void addChargedProjectile(ItemStack pCrossbowStack, ItemStack pAmmoStack)
	{
		CompoundTag compoundtag = pCrossbowStack.getOrCreateTag();
		ListTag listtag;
		if(compoundtag.contains("ChargedProjectiles", 9)) 
		{
			listtag = compoundtag.getList("ChargedProjectiles", 10);
		} 
		else 
		{
			listtag = new ListTag();
		}
		CompoundTag compoundtag1 = new CompoundTag();
		pAmmoStack.save(compoundtag1);
		listtag.add(compoundtag1);
		compoundtag.put("ChargedProjectiles", listtag);
	}
}
