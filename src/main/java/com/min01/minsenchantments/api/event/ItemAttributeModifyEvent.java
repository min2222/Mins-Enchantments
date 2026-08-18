package com.min01.minsenchantments.api.event;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class ItemAttributeModifyEvent extends Event
{
    private final ItemStack stack;
    private final Attribute attribute;
    private final Collection<AttributeModifier> modifiers;
    
    private Collection<AttributeModifier> newModifiers = Collections.emptyList();
    
    public ItemAttributeModifyEvent(ItemStack stack, Attribute attribute, Collection<AttributeModifier> modifiers) 
    {
    	this.stack = stack;
    	this.attribute = attribute;
    	this.modifiers = modifiers;
	}
    
    public ItemStack getStack() 
    {
		return this.stack;
	}
    
    public Attribute getAttribute()
    {
		return this.attribute;
	}
    
    public Collection<AttributeModifier> getModifiers() 
    {
		return this.modifiers;
	}
    
    public void setNewModifiers(Collection<AttributeModifier> newModifiers) 
    {
		this.newModifiers = newModifiers;
	}
    
    public Collection<AttributeModifier> getNewModifiers() 
    {
		return this.newModifiers;
	}
}
