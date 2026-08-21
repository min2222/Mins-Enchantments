package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.api.event.EntityAddedToWorldEvent;
import com.min01.minsenchantments.api.event.EntityTeleportToEvent;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidType;

@Mixin(Entity.class)
public class MixinEntity
{
	@WrapMethod(method = "teleportTo(DDD)V")
	private void minsenchantments$teleportTo(double pX, double pY, double pZ, Operation<Void> original)
	{
		Entity entity = (Entity) (Object) this;
		if(MinecraftForge.EVENT_BUS.post(new EntityTeleportToEvent(entity)))
		{
			return;
		}
		original.call(pX, pY, pZ);
	}
	
	@WrapMethod(method = "onAddedToWorld", remap = false)
	private void minsenchantments$onAddedToWorld(Operation<Void> original)
	{
		Entity entity = (Entity) (Object) this;
		original.call();
		MinecraftForge.EVENT_BUS.post(new EntityAddedToWorldEvent(entity));
	}
	
	@WrapOperation(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
	private boolean minsenchantments$hurt(Entity instance, DamageSource pSource, float pAmount, Operation<Boolean> original)
	{
		if(MEUtil.hasEnchantmentData(instance, MEnchantments.SOUL_FIRE_ASPECT.get()))
		{
			pAmount *= 2.0F;
		}
		return original.call(instance, pSource, pAmount);
	}
	
	@WrapOperation(method = "updateFluidHeightAndDoFluidPushing(Ljava/util/function/Predicate;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isPushedByFluid(Lnet/minecraftforge/fluids/FluidType;)Z"), remap = false)
	private boolean minsenchantments$isPushedByFluid(Entity instance, FluidType fluidType, Operation<Boolean> original)
	{
		if(instance instanceof LivingEntity living)
		{
			if(EnchantmentHelper.getEnchantmentLevel(MEnchantments.UPSTREAM.get(), living) > 0)
			{
				return false;
			}
		}
		return original.call(instance, fluidType);
	}
}
