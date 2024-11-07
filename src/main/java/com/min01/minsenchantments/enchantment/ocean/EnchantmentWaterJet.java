package com.min01.minsenchantments.enchantment.ocean;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.capabilities.IEnchantmentCapability;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.mixin.AbstractArrowInvoker;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.HitResult;

public class EnchantmentWaterJet extends AbstractOceanEnchantment implements IProjectileEnchantment
{
	public EnchantmentWaterJet()
	{
		super(Rarity.RARE, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.waterJetMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.waterJetMinCost.get();
	}
	
	@Override
	public void onEntityTick(Entity entity)
	{
		if(entity instanceof AbstractArrow arrow)
		{
			arrow.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
			{
				if(t.hasEnchantment(this) && arrow.isInWater())
				{
					float waterInertia = ((AbstractArrowInvoker) arrow).Invoke_getWaterInertia();
					arrow.setDeltaMovement(arrow.getDeltaMovement().x / waterInertia, arrow.getDeltaMovement().y / waterInertia, arrow.getDeltaMovement().z / waterInertia);
				}
			});
		}
	}
	
	@Override
	public void onImpact(Projectile projectile, Entity owner, HitResult ray, EnchantmentData data, IEnchantmentCapability cap) 
	{
		if(projectile instanceof ThrownPotion thrownPotion)
		{
			Potion potion = PotionUtils.getPotion(thrownPotion.getItem());
			List<MobEffectInstance> list = PotionUtils.getMobEffects(thrownPotion.getItem());
			boolean flag = potion == Potions.WATER && list.isEmpty();
			List<Player> players = thrownPotion.level.getEntitiesOfClass(Player.class, thrownPotion.getBoundingBox().inflate(1.5));
			players.forEach((player) ->
			{
				for(InteractionHand hand : InteractionHand.values())
				{
					if(player.getItemInHand(hand).getEnchantmentLevel(CustomEnchantments.WATER_JET.get()) > 0)
					{
						ItemStack stack = player.getItemInHand(hand);
						if(stack.getItem() instanceof CrossbowItem)
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
		
		if(projectile instanceof AbstractArrow arrow)
		{
			arrow.pickup = Pickup.DISALLOWED;
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
