package com.min01.minsenchantments.util;

import java.lang.reflect.Method;
import java.util.Queue;

import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EnchantmentUtil
{
	@SuppressWarnings("unchecked")
	public static Iterable<Entity> getAllEntities(Level level)
	{
		Method m = ObfuscationReflectionHelper.findMethod(Level.class, "m_142646_");
		try 
		{
			LevelEntityGetter<Entity> entities = (LevelEntityGetter<Entity>) m.invoke(level);
			return entities.getAll();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isDamageSourceBlocked(LivingEntity living, DamageSource p_21276_) 
	{
		Entity entity = p_21276_.getDirectEntity();
		boolean flag = false;
		if (entity instanceof AbstractArrow abstractarrow)
		{
			if (abstractarrow.getPierceLevel() > 0) 
			{
				flag = true;
			}
		}

		if (!p_21276_.isBypassArmor() && !flag)
		{
			Vec3 vec32 = p_21276_.getSourcePosition();
			if (vec32 != null) 
			{
				Vec3 vec3 = living.getViewVector(1.0F);
				Vec3 vec31 = vec32.vectorTo(living.position()).normalize();
				vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
				if (vec31.dot(vec3) < 0.0D)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static Vec3 getLookPos(float xRot, float yRot, float yPos, double distance)
	{
		float f = -Mth.sin(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
		float f1 = -Mth.sin((xRot + yPos) * ((float)Math.PI / 180F));
		float f2 = Mth.cos(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
		return new Vec3(f, f1, f2).scale(distance);
	}
	
	public static Vec3 fromToVector(Vec3 from, Vec3 to, float speed)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion.scale(speed);
	}
	
	public static double getAttackReachSqr(Entity attacker, Entity target)
	{
		return (double)(attacker.getBbWidth() * 2.0F * attacker.getBbWidth() * 2.0F + target.getBbWidth());
	}
		
	public static boolean removeWaterBreadthFirstSearch(Level p_56808_, BlockPos p_56809_, int radius) 
	{
		Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
		queue.add(new Tuple<>(p_56809_, 0));
		int i = 0;
		BlockState state = p_56808_.getBlockState(p_56809_);

		while(!queue.isEmpty())
		{
			Tuple<BlockPos, Integer> tuple = queue.poll();
			BlockPos blockpos = tuple.getA();
			int j = tuple.getB();

			for(Direction direction : Direction.values())
			{
				BlockPos blockpos1 = blockpos.relative(direction);
				BlockState blockstate = p_56808_.getBlockState(blockpos1);
				FluidState fluidstate = p_56808_.getFluidState(blockpos1);
				Material material = blockstate.getMaterial();
				if (state.canBeHydrated(p_56808_, p_56809_, fluidstate, blockpos1)) 
				{
					if (blockstate.getBlock() instanceof BucketPickup && !((BucketPickup)blockstate.getBlock()).pickupBlock(p_56808_, blockpos1, blockstate).isEmpty()) 
					{
						++i;
						if (j < radius) 
						{
							queue.add(new Tuple<>(blockpos1, j + 1));
						}
					}
					else if (blockstate.getBlock() instanceof LiquidBlock) 
					{
						p_56808_.setBlock(blockpos1, Blocks.AIR.defaultBlockState(), 3);
						++i;
						if (j < radius) 
						{
							queue.add(new Tuple<>(blockpos1, j + 1));
						}
					} 
					else if (material == Material.WATER_PLANT || material == Material.REPLACEABLE_WATER_PLANT) 
					{
						BlockEntity blockentity = blockstate.hasBlockEntity() ? p_56808_.getBlockEntity(blockpos1) : null;
						Block.dropResources(blockstate, p_56808_, blockpos1, blockentity);
						p_56808_.setBlock(blockpos1, Blocks.AIR.defaultBlockState(), 3);
						++i;
						if (j < radius) 
						{
							queue.add(new Tuple<>(blockpos1, j + 1));
						}
					}
				}
			}

			if (i > 64) 
			{
				break;
			}
		}
		
		return i > 0;
	}
	
	public static void addChargedProjectile(ItemStack p_40929_, ItemStack p_40930_)
	{
		CompoundTag compoundtag = p_40929_.getOrCreateTag();
		ListTag listtag;
		if (compoundtag.contains("ChargedProjectiles", 9))
		{
			listtag = compoundtag.getList("ChargedProjectiles", 10);
		} 
		else
		{
			listtag = new ListTag();
		}

		CompoundTag compoundtag1 = new CompoundTag();
		p_40930_.save(compoundtag1);
		listtag.add(compoundtag1);
		compoundtag.put("ChargedProjectiles", listtag);
	}
}
