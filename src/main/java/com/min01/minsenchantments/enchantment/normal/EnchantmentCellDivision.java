package com.min01.minsenchantments.enchantment.normal;

import java.lang.reflect.Method;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EnchantmentCellDivision extends AbstractMinsEnchantment implements IProjectileEnchantment
{
	public EnchantmentCellDivision()
	{
		super(Rarity.RARE, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.cellDivisionMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.cellDivisionMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.cellDivisionMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}

	@Override
	public Enchantment self() 
	{
		return this;
	}

	@Override
	public void onImpact(Projectile projectile, Entity owner, HitResult ray, EnchantmentData data, IEnchantmentCapability cap)
	{
		CompoundTag tag = data.getData();
		int level = data.getEnchantLevel();
		int number = tag.getInt(EnchantmentTags.CELL_DIVISION_NUMBER);
		float scale = tag.getFloat(EnchantmentTags.CELL_DIVISION_SCALE);
		float maxNumber = level * EnchantmentConfig.cellDivisionMaxSplitPerLevel.get();
		if(number < maxNumber)
		{
			for(int i = 0; i < (level * EnchantmentConfig.cellDivisionSplitAmountPerLevel.get()); i++)
			{
				Projectile cell = (Projectile) projectile.getType().create(projectile.level);
				cell.setOwner(owner);
				cell.setPos(projectile.position().add(0, 0.5, 0));
				Level world = projectile.level;
				cell.setDeltaMovement(world.random.nextGaussian() * 0.2D, 0.4D, world.random.nextGaussian() * 0.2D);
                CompoundTag projTag = new CompoundTag();
				Method m = ObfuscationReflectionHelper.findMethod(Projectile.class, "m_7378_", CompoundTag.class);
				Method m2 = ObfuscationReflectionHelper.findMethod(Projectile.class, "m_7380_", CompoundTag.class);
				try 
				{
					m2.invoke(projectile, projTag);
					m.invoke(cell, projTag);
				}
				catch (Exception e) 
				{
					
				}
				world.addFreshEntity(cell);
				cell.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
				{
					CompoundTag cellTag = new CompoundTag();
					cellTag.putInt(EnchantmentTags.CELL_DIVISION_NUMBER, number + 1);
					cellTag.putFloat(EnchantmentTags.CELL_DIVISION_SCALE, scale - EnchantmentConfig.cellDivisionScalePerSplit.get());
					t.setEnchantmentData(this, new EnchantmentData(level, cellTag));
				});
			}
		}
		
		if(projectile instanceof AbstractArrow arrow)
		{
			arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
		}
		projectile.discard();
	}

	@Override
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(EnchantmentTags.CELL_DIVISION_NUMBER, 0);
		tag.putFloat(EnchantmentTags.CELL_DIVISION_SCALE, 1.0F);
		return tag;
	}
}
