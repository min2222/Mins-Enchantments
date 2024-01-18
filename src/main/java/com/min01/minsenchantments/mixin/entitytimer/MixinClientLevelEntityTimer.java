package com.min01.minsenchantments.mixin.entitytimer;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.misc.IEntityTicker;
import com.min01.minsenchantments.util.EnchantmentUtil;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.storage.WritableLevelData;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevelEntityTimer extends Level
{
	@Shadow
	@Final EntityTickList tickingEntities;
	
	protected MixinClientLevelEntityTimer(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, Supplier<ProfilerFiller> p_270692_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) 
	{
		super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
	}

	@SuppressWarnings("deprecation")
	@Inject(at = @At("HEAD"), method = "tickNonPassenger", cancellable = true)
	private void tickNonPassenger(Entity p_104640_, CallbackInfo ci) 
	{
		if(EnchantmentUtil.isNotReplay())
		{
			ci.cancel();
			if(!EnchantmentUtil.CLIENT_TIMER_MAP.isEmpty())
			{
				if(EnchantmentUtil.CLIENT_TIMER_MAP.containsKey(p_104640_.getUUID()))
				{
					int j = EnchantmentUtil.CLIENT_TIMER_MAP.get(p_104640_.getUUID()).advanceTimeEntity(Util.getMillis());
					for(int k = 0; k < Math.min(10, j); ++k)
					{
						p_104640_.setOldPosAndRot();
						++p_104640_.tickCount;
						this.getProfiler().push(() ->
						{
							return BuiltInRegistries.ENTITY_TYPE.getKey(p_104640_.getType()).toString();
						});
						if (p_104640_.canUpdate())
							p_104640_.tick();
						this.getProfiler().pop();

						for(Entity entity : p_104640_.getPassengers())
						{
							this.tickPassenger(p_104640_, entity);
						}
					}	
				}
				else if(!EnchantmentUtil.CLIENT_TIMER_MAP.containsKey(p_104640_.getUUID()))
				{
					int j = ((IEntityTicker)Minecraft.getInstance()).getAdvanceTime();
					for(int k = 0; k < Math.min(10, j); ++k)
					{
						p_104640_.setOldPosAndRot();
						++p_104640_.tickCount;
						this.getProfiler().push(() ->
						{
							return BuiltInRegistries.ENTITY_TYPE.getKey(p_104640_.getType()).toString();
						});
						if (p_104640_.canUpdate())
							p_104640_.tick();
						this.getProfiler().pop();

						for(Entity entity : p_104640_.getPassengers())
						{
							this.tickPassenger(p_104640_, entity);
						}
					}
				}
			}
			else
			{
				int j = ((IEntityTicker)Minecraft.getInstance()).getAdvanceTime();
				for(int k = 0; k < Math.min(10, j); ++k)
				{
					p_104640_.setOldPosAndRot();
					++p_104640_.tickCount;
					this.getProfiler().push(() ->
					{
						return BuiltInRegistries.ENTITY_TYPE.getKey(p_104640_.getType()).toString();
					});
					if (p_104640_.canUpdate())
						p_104640_.tick();
					this.getProfiler().pop();

					for(Entity entity : p_104640_.getPassengers())
					{
						this.tickPassenger(p_104640_, entity);
					}
				}
			}
		}
	}
	
	@Shadow
	private void tickPassenger(Entity p_104642_, Entity p_104643_) 
	{
		   
	}
}
