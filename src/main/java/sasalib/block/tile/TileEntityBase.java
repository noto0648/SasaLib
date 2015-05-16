package sasalib.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import sasalib.helper.WorldHelper;
import sasalib.inventory.ContainerBase;
import sasalib.packet.PacketDispatcher;
import sasalib.packet.SasaPacketBase;
import sasalib.packet.SasaTilePacket;

/**
 * Created by Noto on 2015/05/10.
 */
public class TileEntityBase extends TileEntity
{
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.getPos(), 1, compound);
    }

    public void sendGuiNetworkData(ContainerBase containerBase, ICrafting crafter)
    {
        if(crafter instanceof EntityPlayer)
        {
            SasaPacketBase pkt = getTileUpdatePacket();
            if(pkt != null)
            {
                PacketDispatcher.INSTANCE.sendToPlayer(pkt, (EntityPlayer)crafter);
            }
        }
    }

    public void receivePacket(EntityPlayer player, SasaTilePacket pkt, boolean isRemote)
    {
        int packetID = pkt.getByte();
        if(packetID == 0)
        {
            receiveTilePacket(pkt);
        }
    }

    protected void receiveTilePacket(SasaTilePacket pkt)
    {

    }

    protected SasaTilePacket getTileUpdatePacket()
    {
        SasaTilePacket packet = new SasaTilePacket();
        packet.addByte((byte)0);
        return packet;
    }

    protected WorldHelper getWorldHelper()
    {
        return WorldHelper.from(worldObj);
    }
}
