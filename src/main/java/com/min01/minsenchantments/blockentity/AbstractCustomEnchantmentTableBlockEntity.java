package com.min01.minsenchantments.blockentity;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractCustomEnchantmentTableBlockEntity extends BlockEntity implements Nameable
{
	private Component name;

	public AbstractCustomEnchantmentTableBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pState) 
	{
		super(pType, pPos, pState);
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) 
	{
		super.saveAdditional(pTag);
		if(this.hasCustomName())
		{
			pTag.putString("CustomName", Component.Serializer.toJson(this.name));
		}
	}

	@Override
	public void load(CompoundTag pTag) 
	{
		super.load(pTag);
		if(pTag.contains("CustomName", 8)) 
		{
			this.name = Component.Serializer.fromJson(pTag.getString("CustomName"));
		}
	}

	@Override
	public Component getName()
	{
		return (Component) (this.name != null ? this.name : Component.translatable("container.enchant"));
	}

	public void setCustomName(@Nullable Component pComponent)
	{
		this.name = pComponent;
	}

	@Nullable
	public Component getCustomName()
	{
		return this.name;
	}
}