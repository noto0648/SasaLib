package sasalib.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sasalib.SasaLib;

/**
 * Created by Noto on 2015/05/10.
 */
public class GuiUtil
{
    public static void openTileGui(EntityPlayer player, World world, int x, int y, int z)
    {
        player.openGui(SasaLib.instance, 0, world, x, y, z);
    }

    public static void openTileGui(EntityPlayer player, World world, BlockPos pos)
    {
        player.openGui(SasaLib.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void openTileGui(EntityPlayer player, World world, double x, double y, double z)
    {
        player.openGui(SasaLib.instance, 0, world, (int)x, (int)y, (int)z);
    }

}
