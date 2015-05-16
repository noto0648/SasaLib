package sasalib.world;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sasalib.helper.WorldHelper;

import java.util.List;

/**
 * Created by Noto on 2015/05/10.
 */
public class SchematicObject
{
    public int width, height, length;

    public String material = "Alpha";

    protected byte[] blocks;
    protected byte[] metadata;

    protected NBTTagList tileEntities;
    protected NBTTagList entities;

    private boolean wrote = false;

    public SchematicObject(int w, int h, int l)
    {
        width = w;
        height = h;
        length = l;
        blocks = new byte[width * height * length];
        metadata = new byte[width * height * length];
        tileEntities = new NBTTagList();
    }

    public void write(World world, BlockPos from, BlockPos to)
    {
        WorldHelper wh = WorldHelper.from(world);
        int minX = Math.min(from.getX(), to.getX());
        int minY = Math.min(from.getY(), to.getY());
        int minZ = Math.min(from.getZ(), to.getZ());
        int maxX = Math.min(from.getX(), to.getX());
        int maxY = Math.max(from.getY(), to.getY());
        int maxZ = Math.max(from.getZ(), to.getZ());
        tileEntities = new NBTTagList();
        for(int z = 0; z < length; z++)
        {
            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    int _x = x + minX;
                    int _y = y + minY;
                    int _z = z + minZ;
                    int index = toIndex(_x, _y, _z);
                    blocks[index] = (byte)wh.getBlockID(_x, _y, _z);
                    metadata[index] = (byte)wh.getMetadata(_x, _y, _z);

                    TileEntity te = wh.getTileEntity(_x, _y, _z);
                    if(te != null)
                    {
                        NBTTagCompound compound = new NBTTagCompound();
                        te.writeToNBT(compound);
                        compound.setInteger("x", x);
                        compound.setInteger("y", y);
                        compound.setInteger("z", z);
                        tileEntities.appendTag(compound);
                    }
                }
            }
        }

        List<Entity> entityList = wh.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.fromBounds(minX, minY, minZ, maxX, maxY, maxZ));
        entities = new NBTTagList();

        for(int i = 0; i < entityList.size(); i++)
        {
            NBTTagCompound compound = new NBTTagCompound();
            entityList.get(i).writeToNBT(compound);
            NBTTagList pos = compound.getTagList("Pos", 6);
            pos.set(0, new NBTTagDouble(((NBTTagDouble) pos.get(0)).getDouble() - minX));
            pos.set(1, new NBTTagDouble(((NBTTagDouble)pos.get(1)).getDouble() - minY));
            pos.set(2, new NBTTagDouble(((NBTTagDouble)pos.get(2)).getDouble() - minZ));
            entities.appendTag(entities);
        }
        wrote = true;
    }

    public NBTTagCompound saveToNBT()
    {
        if(!wrote)
            return null;

        NBTTagCompound tag = new NBTTagCompound();
        tag.setShort("Width", (short)width);
        tag.setShort("Height", (short)height);
        tag.setShort("Length", (short)length);
        tag.setString("Material", material);
        tag.setByteArray("Blocks", blocks);
        tag.setByteArray("Data", metadata);
        tag.setTag("Entities", entities);
        tag.setTag("TileEntities", tileEntities);
        return tag;
    }
    
    private int toIndex(int x, int y, int z)
    {
        return x + (z * width) + (y * length * width);
    }
}
