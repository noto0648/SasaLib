package sasalib.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import sasalib.block.tile.TileEntityBase;

/**
 * Created by Noto on 2015/05/10.
 */
public class SasaTilePacket extends SasaPacket
{
    public SasaTilePacket() {}

    public SasaTilePacket(TileEntityBase tileEntity)
    {
        addBlockPos(tileEntity.getPos());
    }

    @Override
    protected void receive(EntityPlayer player, boolean isRemote)
    {
        TileEntity te = player.worldObj.getTileEntity(getBlockPos());
        if(te instanceof TileEntityBase)
        {
            ((TileEntityBase) te).receivePacket(player, this, isRemote);
        }
    }
}
