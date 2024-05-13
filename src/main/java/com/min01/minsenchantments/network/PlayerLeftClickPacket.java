package com.min01.minsenchantments.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.minsenchantments.api.IMinsEnchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

public class PlayerLeftClickPacket 
{
	public UUID playerUUID;
	public ItemStack stack;
	public InteractionHand hand;
	public BlockPos pos;
	public Direction direction;
	
	public PlayerLeftClickPacket(Entity entity, ItemStack stack, InteractionHand hand, BlockPos pos, Direction direction) 
	{
		this.playerUUID = entity.getUUID();
		this.stack = stack;
		this.hand = hand;
		this.pos = pos;
		if(direction != null)
		{
			this.direction = direction;
		}
	}

	public PlayerLeftClickPacket(FriendlyByteBuf buf)
	{
		this.playerUUID = buf.readUUID();
		this.stack = buf.readItem();
		this.hand = InteractionHand.values()[buf.readInt()];
		this.pos = buf.readBlockPos();
		this.direction = Direction.values()[buf.readInt()];
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeItemStack(this.stack, false);
		buf.writeInt(this.hand.ordinal());
		buf.writeBlockPos(this.pos);
		buf.writeInt(this.direction.ordinal());
	}
	
	public static class Handler 
	{
		public static boolean onMessage(PlayerLeftClickPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				ForgeRegistries.ENCHANTMENTS.forEach(t -> 
				{
					if(t instanceof IMinsEnchantment enchantment)
					{
						for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels())
						{
							Entity entity = level.getEntity(message.playerUUID);
							if(entity instanceof Player player)
							{
								enchantment.onPlayerLeftClickEmpty(player, message.stack, message.hand, message.pos, message.direction);
							}
						}
					}
				});
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
