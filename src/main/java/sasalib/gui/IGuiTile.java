package sasalib.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Noto on 2015/05/10.
 */
public interface IGuiTile
{
    Object getServerGuiElement(EntityPlayer player, World world, BlockPos pos);

    @SideOnly(Side.CLIENT)
    Object getClientGuiElement(EntityPlayer player, World world, BlockPos pos);
}
