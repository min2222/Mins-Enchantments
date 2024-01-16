package com.min01.minsenchantments.network;

import java.util.function.Supplier;

import com.min01.minsenchantments.misc.ClientEventHandlerForge;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.network.NetworkEvent;

public class CellScaleSyncPacket 
{
	private final int id;
	private final float scale;
	
	public CellScaleSyncPacket(int id, float scale) 
	{
		this.id = id;
		this.scale = scale;
	}

	public CellScaleSyncPacket(FriendlyByteBuf buf)
	{
		this.id = buf.readInt();
		this.scale = buf.readFloat();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.id);
		buf.writeFloat(this.scale);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(CellScaleSyncPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Minecraft mc = Minecraft.getInstance();
				Entity entity = mc.level.getEntity(message.id);
				if(entity != null && entity instanceof Projectile proj)
				{
					ClientEventHandlerForge.SCALE_MAP.put(proj, message.scale);
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
