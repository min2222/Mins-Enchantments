package com.min01.minsenchantments.enchantment.nether;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentFlameThorn extends AbstractNetherEnchantment
{
	public EnchantmentFlameThorn()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.flameThornMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.flameThornMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.flameThornMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingAttack(LivingEntity living, DamageSource damageSource, float amount) 
	{
		int level = EnchantmentHelper.getEnchantmentLevel(this, living);
		Entity source = damageSource.getEntity();
		Entity directSource = damageSource.getDirectEntity();
		
		if(source != null && source instanceof LivingEntity attacker && level > 0)
		{
			boolean flag = directSource instanceof Projectile proj ? proj.getOwner() != null && proj.getOwner() == living : directSource == living;
			if(attacker != living && !flag && !(damageSource.getMsgId().equals("thorns")) && ForgeRegistries.ENTITY_TYPES.getKey(attacker.getType()).toString() != "friendsandfoes:wildfire")
			{
				attacker.hurt(living.damageSources().thorns(living), level * EnchantmentConfig.flameThornDamagePerLevel.get());
				attacker.setSecondsOnFire((level * EnchantmentConfig.flameThornFireDurationPerLevel.get()) * 20);
			}
		}
	}
}
