package sasalib.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sasalib.utility.ItemUtil;

/**
 * Created by Noto on 2015/05/10.
 */
public class SlotFilterable extends Slot
{
    private ItemStack[] filterItems;
    private boolean useWhiteList;

    public SlotFilterable(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public SlotFilterable setFilter(ItemStack... stacks)
    {
        filterItems = stacks;
        return this;
    }

    public SlotFilterable setWhiteList()
    {
        useWhiteList = true;
        return this;
    }

    public SlotFilterable setBlackList()
    {
        useWhiteList = false;
        return this;
    }

    public boolean isItemValid(ItemStack stack)
    {
        if(useWhiteList)
        {
            for(int i = 0; i < filterItems.length; i++)
            {
                if(ItemUtil.equalsSimple(stack, filterItems[i]))
                {
                    return true;
                }
            }
        }
        else
        {
            boolean success = true;
            for(int i = 0; i < filterItems.length; i++)
            {
                if(ItemUtil.equalsSimple(stack, filterItems[i]))
                {
                    success = false;
                    return false;
                }
            }
            return success;
        }
        return false;
    }


}
