package com.min01.minsenchantments.enchantment.bless;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.event.ProjectileHitEvent;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentCategory;
import com.min01.minsenchantments.misc.MEnchantmentNbtTagKeys;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TerrarianSoulBlessment extends MEnchantment
{
	public TerrarianSoulBlessment()
	{
		super(Rarity.VERY_RARE, MEnchantmentCategory.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, EnchantmentType.BLESS);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 15;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
	
	@Override
	public boolean requiredSummonContext() 
	{
		return true;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public void onProjectileHit(ProjectileHitEvent event) 
	{
		Projectile projectile = event.getProjectile();
		HitResult result = event.getRayTraceResult();
		if(result instanceof EntityHitResult entityHit)
		{
			Entity entity = entityHit.getEntity();
			EnchantmentData data = MEUtil.getEnchantmentData(projectile, this);
			
			if(data != null)
			{
				RandomSource random = entity.level.random;
				EnchantmentInstance instance = data.instance();
				CompoundTag tag = data.tag();
				int level = instance.level;
				float width = entity.getBbWidth() * 4.0F;
				
				if(tag.contains(MEnchantmentNbtTagKeys.TERRARIAN_SOUL_SUMMONED))
				{
					entity.invulnerableTime = 0;
				}
				else
				{
					CompoundTag saved = projectile.saveWithoutId(new CompoundTag());
					saved.remove("Pos");
					saved.remove("Motion");
					saved.remove("Rotation");
					saved.remove("FallDistance");
					saved.remove("Fire");
					saved.remove("Air");
					saved.remove("OnGround");
					saved.remove("Invulnerable");
					saved.remove("PortalCooldown");
					saved.remove("UUID");
					if(saved.contains("ForgeCaps", 10))
					{
						//copy since summoned projectiles have same instance with original one;
						CompoundTag caps = saved.getCompound("ForgeCaps");
						saved.put("ForgeCaps", caps.copy());
					}
					//prevent dupe exploit of trident or other similar items;
					for(String key : saved.getAllKeys())
					{
						Tag itemTag = saved.get(key);
						if(itemTag instanceof CompoundTag compoundTag && compoundTag.contains("tag", 10))
						{
							//we might need to check if this tag is real item stack tag, in case if other mod is using "tag" key for other objects;
							//but i'm not sure;
							if(compoundTag.contains("tag", 10))
							{
								CompoundTag itemCompoundTag = compoundTag.getCompound("tag");
								itemCompoundTag.putBoolean(MEnchantmentNbtTagKeys.TERRARIAN_SOUL_SUMMONED, true);
							}
						}
					}
					for(int i = 0; i < level * MEConfig.terrarianSoulAmountPerLevel.get(); i++)
					{
						Vec3 pos = MEUtil.randomPointAroundBox(entity.getBoundingBox(), random, width);
						Projectile copy = (Projectile) projectile.getType().create(projectile.level);
						Vec3 motion = MEUtil.getVelocityTowards(pos, entity.getEyePosition(), 0.5F);
						copy.load(saved);
						copy.setPos(pos);
						copy.setDeltaMovement(motion);

						EnchantmentData copiedData = MEUtil.getEnchantmentData(copy, this);
						if(copiedData != null)
						{
							CompoundTag copiedTag = copiedData.tag();
							copiedTag.putBoolean(MEnchantmentNbtTagKeys.TERRARIAN_SOUL_SUMMONED, true);
						}
						projectile.level.addFreshEntity(copy);
					}
				}
			}
		}
	}
}
