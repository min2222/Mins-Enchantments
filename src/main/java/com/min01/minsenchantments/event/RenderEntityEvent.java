package com.min01.minsenchantments.event;

import org.jetbrains.annotations.ApiStatus;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public abstract class RenderEntityEvent<T extends Entity> extends Event
{
    private final Entity entity;
    private final EntityRenderer<T> renderer;
    private final float partialTick;
    private final PoseStack poseStack;
    private final MultiBufferSource multiBufferSource;
    private final int packedLight;

    @ApiStatus.Internal
    protected RenderEntityEvent(Entity entity, EntityRenderer<T> renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight)
    {
        this.entity = entity;
        this.renderer = renderer;
        this.partialTick = partialTick;
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
        this.packedLight = packedLight;
    }

    public Entity getEntity()
    {
        return entity;
    }

    public EntityRenderer<T> getRenderer()
    {
        return renderer;
    }

    public float getPartialTick()
    {
        return partialTick;
    }

    public PoseStack getPoseStack()
    {
        return poseStack;
    }

    public MultiBufferSource getMultiBufferSource()
    {
        return multiBufferSource;
    }
    
    public int getPackedLight()
    {
        return packedLight;
    }
    
    @Cancelable
    public static class Pre<T extends Entity> extends RenderEntityEvent<T>
    {
        @ApiStatus.Internal
        public Pre(Entity entity, EntityRenderer<T> renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight)
        {
            super(entity, renderer, partialTick, poseStack, multiBufferSource, packedLight);
        }
    }
    
    public static class Post<T extends Entity> extends RenderEntityEvent<T>
    {
        @ApiStatus.Internal
        public Post(Entity entity, EntityRenderer<T> renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight)
        {
            super(entity, renderer, partialTick, poseStack, multiBufferSource, packedLight);
        }
    }
}
