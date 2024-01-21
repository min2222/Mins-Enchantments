package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EventHandlerForge;
import com.min01.minsenchantments.misc.IExtraEntity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements IExtraEntity
{
	private static final EntityDataAccessor<Boolean> IS_SOUL_FIRE = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_IN_WATER = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);
	
	public MixinLivingEntity(EntityType<?> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
	}
	
	@Inject(at = @At("HEAD"), method = "defineSynchedData", cancellable = true)
	private void defineSynchedData(CallbackInfo ci)
	{
		this.getEntityData().define(IS_SOUL_FIRE, false);
		this.getEntityData().define(IS_IN_WATER, false);
	}
	
	@Inject(at = @At("HEAD"), method = "tick", cancellable = true)
	private void tick(CallbackInfo ci)
	{
		Level level = this.level();
		if(!level.isClientSide)
		{
			this.getEntityData().set(IS_SOUL_FIRE, this.getPersistentData().getInt(EventHandlerForge.SOUL_FIRE) > 0);
			this.getEntityData().set(IS_IN_WATER, this.getPersistentData().contains(EventHandlerForge.AQUATIC_AURA));
		}
	}

	@Override
	public boolean isSoulFire() 
	{
		return this.getEntityData().get(IS_SOUL_FIRE);
	}
	
	@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"))
	private boolean isInWater(LivingEntity instance) 
	{
		if(this.getEntityData().get(IS_IN_WATER))
		{
			return true;
		}
		return instance.isInWater();
	}
	
	@Inject(at = @At("HEAD"), method = "stopUsingItem", cancellable = true)
    private void stopUsingItem(CallbackInfo ci)
    {
		LivingEntity living = LivingEntity.class.cast(this);
		if(living.getPersistentData().contains(EventHandlerForge.AUTO_SHIELDING) && living.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ShieldItem)
		{
			ItemStack stack = living.getItemBySlot(EquipmentSlot.OFFHAND);
			int shielding = living.getPersistentData().getInt(EventHandlerForge.AUTO_SHIELDING);
			if(shielding > 0)
			{
				if(stack.getEnchantmentLevel(CustomEnchantments.AUTO_SHIELDING.get()) > 0)
				{
					ci.cancel();
				}
			}
		}
    }
}
