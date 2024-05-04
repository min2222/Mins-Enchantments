package com.min01.minsenchantments.blessment;

import java.lang.reflect.Method;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class BlessmentSoulOfTerrarian extends AbstractBlessment implements IProjectileEnchantment
{
	public BlessmentSoulOfTerrarian()
	{
		super(CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.soulOfTerrarianMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.soulOfTerrarianMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.soulOfTerrarianMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onImpact(Projectile projectile, Entity owner, HitResult ray, EnchantmentData data, IEnchantmentCapability cap) 
	{
		CompoundTag tag = data.getData();
		if(ray instanceof EntityHitResult result)
		{
			if(result.getEntity() instanceof LivingEntity living)
			{
				if(!tag.contains(EnchantmentTags.SOUL_OF_TERRARIAN_SUMMONED))
				{
					int level = data.getEnchantLevel();
					for(int i = 0; i < level * EnchantmentConfig.soulOfTerrarianProjAmountPerLevel.get(); i++)
					{
						Projectile extra = (Projectile) projectile.getType().create(projectile.level());
						double spawnRange = living.getBbWidth() + level;
		                double x = (double)living.getX() + (projectile.level().getRandom().nextDouble() - projectile.level().getRandom().nextDouble()) * (double)spawnRange + 0.5D;
		                double y = (double)(living.getY() + living.getEyeHeight() + projectile.level().getRandom().nextInt(2) - 1);
		                double z = (double)living.getZ() + (projectile.level().getRandom().nextDouble() - projectile.level().getRandom().nextDouble()) * (double)spawnRange + 0.5D;
		                extra.setPos(x, y, z);
		                extra.setOwner(owner);
		                Vec3 vec = EnchantmentUtil.fromToVector(extra.position(), living.position().add(0, living.getEyeHeight(), 0), 1);
		                extra.setDeltaMovement(vec);
		                extra.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
		                {
		                	CompoundTag tag2 = new CompoundTag();
		                	tag2.putBoolean(EnchantmentTags.SOUL_OF_TERRARIAN_SUMMONED, true);
		                	t.setEnchantmentData(this, new EnchantmentData(level, tag2));
		                });
		                extra.hasImpulse = true;
		                extra.setNoGravity(true);
		                if(extra instanceof AbstractArrow arrow)
		                {
		                	arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		                }
		                CompoundTag projTag = new CompoundTag();
						Method m = ObfuscationReflectionHelper.findMethod(Projectile.class, "m_7378_", CompoundTag.class);
						Method m2 = ObfuscationReflectionHelper.findMethod(Projectile.class, "m_7380_", CompoundTag.class);
						try 
						{
							m2.invoke(projectile, projTag);
							m.invoke(extra, projTag);
						}
						catch (Exception e) 
						{
							
						}
		                projectile.level().addFreshEntity(extra);
					}
				}
				
				if(tag.contains(EnchantmentTags.SOUL_OF_TERRARIAN_SUMMONED))
				{
					living.invulnerableTime = 0;
					projectile.discard();
				}
			}
		}
	}

	@Override
	public Enchantment self() 
	{
		return this;
	}

	@Override
	public CompoundTag getData(LivingEntity entity, @NotNull ItemStack item, int duration)
	{
		return new CompoundTag();
	}
}
