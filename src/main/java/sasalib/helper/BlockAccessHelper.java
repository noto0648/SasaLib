package sasalib.helper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Created by Noto on 2015/05/06.
 */
public class BlockAccessHelper implements IBlockAccess
{
    protected IBlockAccess access;

    protected BlockAccessHelper(IBlockAccess blockAccess)
    {
        this.access = blockAccess;
    }

    public static BlockAccessHelper from(IBlockAccess iBlockAccess)
    {
        return new BlockAccessHelper(iBlockAccess);
    }

    public BlockPos toPos(int x, int y, int z)
    {
        return new BlockPos(x, y, z);
    }

    public boolean isValidPos(BlockPos pos)
    {
        return isValidPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public boolean isValidPos(int x, int y, int z)
    {
        return x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000 && y >= 0 && y < 256;
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos)
    {
        return access.getTileEntity(pos);
    }

    public TileEntity getTileEntity(int x, int y, int z)
    {
        return getTileEntity(toPos(x, y, z));
    }

    public <T extends TileEntity> T getTileEntity(Class<T> classOfT, BlockPos pos)
    {
        TileEntity te = getTileEntity(pos);
        if(te == null || !(te != null && classOfT.isInstance(te)))
        {
            return null;
        }
        return (T)getTileEntity(pos);
    }

    public <T extends TileEntity> T getTileEntity(Class<T> classOfT, int x, int y, int z)
    {
        return getTileEntity(classOfT, toPos(x, y, z));
    }

    @Override
    public int getCombinedLight(BlockPos pos, int baseLightValue)
    {
        return access.getCombinedLight(pos, baseLightValue);
    }

    public int getCombinedLight(int x, int y, int z, int baseLightValue)
    {
        return getCombinedLight(toPos(x, y, z), baseLightValue);
    }

    @Override
    public IBlockState getBlockState(BlockPos pos)
    {
        if(pos == null)
            pos = toPos(0, 0, 0);

        return access.getBlockState(pos);
    }

    public IBlockState getBlockState(int x, int y, int z)
    {
        return getBlockState(toPos(x, y, z));
    }

    @Override
    public boolean isAirBlock(BlockPos pos)
    {
        return access.isAirBlock(pos);
    }

    public boolean isAirBlock(int x, int y, int z)
    {
        return isAirBlock(toPos(x, y, z));
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(BlockPos pos)
    {
        return access.getBiomeGenForCoords(pos);
    }

    public BiomeGenBase getBiomeGenForCoords(int x, int z)
    {
        return access.getBiomeGenForCoords(toPos(x, 0, z));
    }

    @Override
    public boolean extendedLevelsInChunkCache()
    {
        return access.extendedLevelsInChunkCache();
    }

    @Override
    public int getStrongPower(BlockPos pos, EnumFacing direction)
    {
        return access.getStrongPower(pos, direction);
    }

    public int getStrongPower(int x, int y, int z, EnumFacing direction)
    {
        return access.getStrongPower(toPos(x, y, z), direction);
    }

    @Override
    public WorldType getWorldType()
    {
        return access.getWorldType();
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean defaultValue)
    {
        return access.isSideSolid(pos, side, defaultValue);
    }

    public boolean isSideSolid(int x, int y, int z, EnumFacing side, boolean defaultValue)
    {
        return access.isSideSolid(toPos(x, y, z), side, defaultValue);
    }

    public Block getBlock(BlockPos pos)
    {
        return getBlockState(pos).getBlock();
    }

    public Block getBlock(int x, int y, int z)
    {
        return getBlock(toPos(x, y, z));
    }

    public int getBlockID(BlockPos pos)
    {
        return Block.getIdFromBlock(getBlock(pos));
    }

    public int getBlockID(int x, int y, int z)
    {
        return getBlockID(toPos(x, y, z));
    }

    public int getMetadata(BlockPos blockPos)
    {
        IBlockState state = getBlockState(blockPos);
        if(state.getPropertyNames().isEmpty())
            return 0;

        return state.getBlock().getMetaFromState(state);
    }

    public int getMetadata(int x, int y, int z)
    {
        return getMetadata(toPos(x, y, z));
    }
}
