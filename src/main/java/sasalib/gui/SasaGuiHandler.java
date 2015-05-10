package sasalib.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sasalib.helper.WorldHelper;

/**
 * Created by Noto on 2015/05/10.
 */
public class SasaGuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        WorldHelper wh = WorldHelper.from(world);
        TileEntity te = wh.getTileEntity(x, y, z);
        if(te != null && te instanceof IGuiTile)
        {
            return ((IGuiTile) te).getServerGuiElement(player, world, wh.toPos(x, y, z));
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        WorldHelper wh = WorldHelper.from(world);
        TileEntity te = wh.getTileEntity(x, y, z);
        if(te != null && te instanceof IGuiTile)
        {
            return ((IGuiTile) te).getClientGuiElement(player, world, wh.toPos(x, y, z));
        }
        return null;
    }
}
