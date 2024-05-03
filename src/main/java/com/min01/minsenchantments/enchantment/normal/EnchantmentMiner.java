package com.min01.minsenchantments.enchantment.normal;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EnchantmentMiner extends AbstractMinsEnchantment implements IProjectileEnchantment
{
	public EnchantmentMiner()
	{
		super(Rarity.RARE, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.minerMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.minerMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.minerMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}

	@Override
	public Enchantment self() 
	{
		return this;
	}

	@Override
	public void onImpact(Projectile projectile, Entity owner, HitResult ray, EnchantmentData data, IEnchantmentCapability cap) 
	{	
		int level = data.getEnchantLevel();
		CompoundTag tag = data.getData();
		int count = tag.getInt(EnchantmentTags.MINER_COUNT);
		
		if(count < level * EnchantmentConfig.minerMaxBlockPerLevel.get() && ray.getType() == HitResult.Type.BLOCK)
		{
			BlockPos pos = ((BlockHitResult) ray).getBlockPos();
			BlockState state = projectile.level().getBlockState(pos);
			if(state.getDestroySpeed(projectile.level(), pos) > -1.0F)
			{
				if(projectile.level().destroyBlock(pos, true, owner))
				{	
					tag.putInt(EnchantmentTags.MINER_COUNT, count + 1);
					cap.setEnchantmentData(this, new EnchantmentData(level, tag));
				}
			}
		}
	}

	@Override
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration) 
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(EnchantmentTags.MINER_COUNT, 0);
		return tag;
	}
}
