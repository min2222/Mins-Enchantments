package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.min01.minsenchantments.util.EnchantmentUtil;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.Phase;

public class EnchantmentWavesProtection extends AbstractOceanEnchantment
{
	public EnchantmentWavesProtection()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.wavesProtectionMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.wavesProtectionMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.wavesProtectionMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public boolean checkCompatibility(Enchantment p_44590_)
	{
		return p_44590_ instanceof EnchantmentTide ? false : super.checkCompatibility(p_44590_);
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player) 
	{
		player.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t ->
		{
			if(t.hasEnchantment(this))
			{
				EnchantmentData data = t.getEnchantmentData(this);
				CompoundTag tag = data.getData();
				float wave = tag.getFloat(EnchantmentTags.WAVES_PROTECTION);
				if(wave > 0)
				{
					tag.putFloat(EnchantmentTags.WAVES_PROTECTION, wave - 1);
					if(player.isInWater() && player.isSwimming())
					{
						Vec3 lookPos = player.position().add(EnchantmentUtil.getLookPos(player.getXRot(), player.getYRot(), 0, 0.1F));
						Vec3 motion = EnchantmentUtil.fromToVector(player.position(), lookPos, data.getEnchantLevel() * EnchantmentConfig.wavesProtectionSpeedPerLevel.get());
						player.setDeltaMovement(motion.x, player.getDeltaMovement().y, motion.z);
						if(player instanceof ServerPlayer serverPlayer)
						{
							serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
						}
					}
				}
				else
				{
					t.removeEnchantment(this);
				}
			}
		});
	}
	
	@Override
	public Pair<Boolean, Float> onLivingDamage(LivingEntity entity, DamageSource source, float amount) 
	{
		entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t ->
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, entity);
			if(level > 0)
			{
				if(!t.hasEnchantment(this) && entity.isInWater())
				{
					CompoundTag tag = new CompoundTag();
					tag.putFloat(EnchantmentTags.WAVES_PROTECTION, level * (EnchantmentConfig.wavesProtectionDurationPerLevel.get() * 20));
					t.setEnchantmentData(this, new EnchantmentData(level, tag));
				}
			}
		});
		return super.onLivingDamage(entity, source, amount);
	}
}
