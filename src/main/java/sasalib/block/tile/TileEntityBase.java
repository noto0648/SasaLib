package sasalib.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import sasalib.inventory.ContainerBase;
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

    public void sendGuiNetworkData(ContainerBase containerBase, ICrafting crafter) {}
    public void receivePacket(EntityPlayer player, SasaTilePacket pkt, boolean isRemote) {}
}
