package sasalib.utility;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import sasalib.inventory.InventoryDummy;

/**
 * Created by Noto on 2015/05/07.
 */
public class InventoryUtil
{
    public static boolean insertItemStack(ItemStack input, IInventory inventory, int[] slots)
    {
        ItemStack stack = null;
        boolean success = false;
        if(input.isStackable())
        {
            int index = 0;
            while(input.stackSize > 0 && index < slots.length)
            {
                stack = inventory.getStackInSlot(slots[index]);
                if(ItemUtil.equalsIgnoreSize(stack, input))
                {
                    int size = stack.stackSize + input.stackSize;
                    if(size <= input.getMaxStackSize())
                    {
                        stack.stackSize = size;
                        input.stackSize = 0;
                        success = true;
                    }
                    else if(stack.stackSize < input.getMaxStackSize())
                    {
                        stack.stackSize = input.getMaxStackSize();
                        input.stackSize -= input.getMaxStackSize() - stack.stackSize;
                        success = true;
                    }
                }
                index++;

            }
        }

        if(input.stackSize > 0)
        {
            for(int i = 0; i < slots.length; i++)
            {
                stack = inventory.getStackInSlot(slots[i]);
                if(stack == null)
                {
                    inventory.setInventorySlotContents(slots[i], input.copy());
                    input.stackSize = 0;
                    success = true;
                    break;
                }
            }
        }

        return input.stackSize <= 0;
    }

    public static boolean insertItemStack(ItemStack input, IInventory inventory, int firstSlot, int lastSlot)
    {
        int[] slots = new int[Math.abs(lastSlot - firstSlot) + 1];
        for(int i = 0; i < slots.length; i++)
        {
            slots[i] = firstSlot + i;
        }
        return insertItemStack(input, inventory, slots);
    }

    public static boolean canInsertItemStack(ItemStack input, IInventory inventory, int firstSlot, int lastSlot)
    {
        int[] slots = new int[Math.abs(lastSlot - firstSlot) + 1];
        for(int i = 0; i < slots.length; i++)
        {
            slots[i] = firstSlot + i;
        }
        return canInsertItemStack(input, inventory, slots);
    }


    public static boolean canInsertItemStack(ItemStack input, IInventory inventory, int[] slots)
    {
        InventoryDummy dummy = new InventoryDummy(inventory);
        ItemStack stack = input.copy();
        insertItemStack(stack, dummy, slots);
        return stack.stackSize <= 0;
    }

}
