package sasalib.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sasalib.helper.WorldHelper;

/**
 * Created by Noto on 2015/05/10.
 */
public class WorldExporter
{
    private WorldHelper world;

    public WorldExporter(World world)
    {
        this.world = WorldHelper.from(world);
    }

    public NBTTagCompound toSchematic(BlockPos from, BlockPos to)
    {
        NBTTagCompound root = new NBTTagCompound();

        int minX = Math.min(from.getX(), to.getX());
        int minY = Math.min(from.getY(), to.getY());
        int minZ = Math.min(from.getZ(), to.getZ());
        int maxX = Math.max(from.getX(), to.getX());
        int maxY = Math.max(from.getY(), to.getY());
        int maxZ = Math.max(from.getZ(), to.getZ());

        int width = Math.abs(maxX - minX) + 1;
        int height = Math.abs(maxX - minX) + 1;
        int length = Math.abs(maxX - minX) + 1;

        root.setShort("Width", (short)width);
        root.setShort("Height", (short)width);
        root.setShort("Length", (short)width);
        root.setString("Materials", "Alpha");

        byte[] blocks;

        return root;
    }


}
