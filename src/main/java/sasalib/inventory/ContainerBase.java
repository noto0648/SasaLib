package sasalib.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import sasalib.block.tile.TileEntityBase;

/**
 * Created by Noto on 2015/05/10.
 */
public abstract class ContainerBase extends Container
{
    protected TileEntityBase tileEntity;

    public ContainerBase()
    {

    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        if(tileEntity != null)
        {
            for(int i = 0; i < this.crafters.size(); i++)
            {
                tileEntity.sendGuiNetworkData(this, (ICrafting)this.crafters.get(i));
            }
        }
    }
}
