package com.min01.minsenchantments.api.context;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.min01.minsenchantments.enchantment.MEnchantments;

import net.minecraft.world.item.ItemStack;

public class CooldownContext
{
	public static final Deque<ItemStack> STACK = new ArrayDeque<>();

    public static void push(ItemStack stack)
    {
    	ItemStack toPush = ItemStack.EMPTY;
    	if(stack != null && !stack.isEmpty())
    	{
        	int level = stack.getEnchantmentLevel(MEnchantments.MASTER_TOUCH.get());
        	if(level > 0)
        	{
        		toPush = stack.copy();
        	}
    	}
		STACK.push(toPush);
    }

    public static Optional<ItemStack> peek()
    {
        ItemStack stack = STACK.peek();
        if(stack != null && !stack.isEmpty())
        {
        	return Optional.of(stack);
        }
        return Optional.empty();
    }

    public static void pop()
    {
    	//it sometimes throw java.util.NoSuchElementException;
    	try
    	{
            if(!STACK.isEmpty())
            {
                STACK.pop();
            }
    	}
    	catch(NoSuchElementException e)
    	{
    		e.printStackTrace();
    	}
    }
}
