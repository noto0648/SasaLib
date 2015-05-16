package sasalib.helper;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import sasalib.utility.ItemUtil;

import java.util.List;
import java.util.UUID;

/**
 * Created by Noto on 2015/05/06.
 */
public class WorldHelper extends BlockAccessHelper
{
    private World world;

    public final boolean isRemote;
    public final WorldProvider provider;

    protected WorldHelper(World blockAccess)
    {
        super(blockAccess);
        this.world = blockAccess;
        this.isRemote = this.world.isRemote;
        this.provider = this.world.provider;
    }

    public static WorldHelper from(World world)
    {
        return new WorldHelper(world);
    }

    public World getWorld()
    {
        return world;
    }

    public boolean setBlockState(BlockPos pos, IBlockState newState, int flags)
    {
        return world.setBlockState(pos, newState, flags);
    }

    public boolean setBlockState(int x, int y, int z, IBlockState newState, int flags)
    {
        return setBlockState(toPos(x, y, z), newState, flags);
    }

    public boolean setBlockState(int x, int y, int z, IBlockState newState)
    {
        return setBlockState(toPos(x, y, z), newState, 3);
    }

    public boolean setBlockState(BlockPos pos, IBlockState newState)
    {
        return setBlockState(pos, newState, 3);
    }

    public boolean setBlockState(BlockPos pos, IBlockState newState, EnumWorldFlag... flags)
    {
        return setBlockState(pos, newState, getFlagValue(flags));
    }

    public boolean setBlockState(int x, int y, int z, IBlockState newState, EnumWorldFlag... flags)
    {
        return setBlockState(toPos(x, y, z), newState, flags);
    }

    public boolean setBlock(BlockPos pos, Block block, int flags)
    {
        if(block == null)
            return false;
        return setBlockState(pos, block.getDefaultState(), flags);
    }

    public boolean setBlock(int x, int y, int z, Block block, int flags)
    {
        return setBlock(toPos(x, y, z), block, flags);
    }

    public boolean setBlock(BlockPos pos, Block block)
    {
        return setBlock(pos, block, 3);
    }

    public boolean setBlock(int x, int y, int z, Block block)
    {
        return setBlock(toPos(x, y, z), block, 3);
    }

    public boolean setBlock(BlockPos pos, Block block, EnumWorldFlag... flags)
    {
        return setBlock(pos, block, getFlagValue(flags));
    }

    public boolean setBlock(int x, int y, int z, Block block, EnumWorldFlag... flags)
    {
        return setBlock(toPos(x, y, z), block, flags);
    }

    public boolean setMetadata(BlockPos pos, int metadata, int flags)
    {
        TileEntity te = getTileEntity(pos);
        IBlockState state = getBlock(pos).getStateFromMeta(metadata);
        boolean result = setBlockState(pos, state, flags);
        result = setBlockState(pos, state, flags);

        if(te != null)
            te.validate();
        world.setTileEntity(pos, te);

        return result && getMetadata(pos) == metadata;
    }

    public boolean setMetadata(int x, int y, int z, int metadata, int flags)
    {
        return setMetadata(toPos(x, y, z), metadata, flags);
    }

    public boolean setMetadata(BlockPos pos, int metadata)
    {
        return setMetadata(pos, metadata, 3);
    }

    public boolean setMetadata(int x, int y, int z, int metadata)
    {
        return setMetadata(toPos(x, y, z), metadata, 3);
    }

    public boolean setMetadata(BlockPos pos, int metadata, EnumWorldFlag... flags)
    {
        return setMetadata(pos, metadata, getFlagValue(flags));
    }

    public boolean setMetadata(int x, int y, int z, int metadata, EnumWorldFlag... flags)
    {
        return setMetadata(toPos(x, y, z), metadata, getFlagValue(flags));
    }

    @Deprecated
    public boolean setBlockAndMetadata(BlockPos pos, Block block,int metadata, int flags)
    {
        IBlockState state = block.getStateFromMeta(metadata);
        boolean result = setBlockState(pos, state, flags);
        return result && getMetadata(pos) == metadata;
    }

    @Deprecated
    public boolean setBlockAndMetadata(int x, int y, int z, Block block,int metadata, int flags)
    {
        return setBlockAndMetadata(toPos(x, y, z), block, metadata, flags);
    }

    @Deprecated
    public boolean setBlockAndMetadata(BlockPos pos, Block block, int metadata)
    {
        return setBlockAndMetadata(pos, block, metadata, 3);
    }

    @Deprecated
    public boolean setBlockAndMetadata(int x, int y, int z, Block block, int metadata)
    {
        return setBlockAndMetadata(toPos(x, y, z), block, metadata, 3);
    }

    @Deprecated
    public boolean setBlockAndMetadata(BlockPos pos, Block block, int metadata, EnumWorldFlag... flags)
    {
        return setBlockAndMetadata(pos, block, metadata, getFlagValue(flags));
    }

    @Deprecated
    public boolean setBlockAndMetadata(int x, int y, int z, Block block, int metadata, EnumWorldFlag... flags)
    {
        return setBlockAndMetadata(toPos(x, y, z), block, metadata, getFlagValue(flags));
    }

    public boolean fillBlockState(IBlockState state, BlockPos from, BlockPos to, boolean ignoreError, int flags)
    {
        int minX = Math.min(from.getX(), to.getX());
        int minY = Math.min(from.getY(), to.getY());
        int minZ = Math.min(from.getZ(), to.getZ());
        int maxX = Math.max(from.getX(), to.getX());
        int maxY = Math.max(from.getY(), to.getY());
        int maxZ = Math.max(from.getZ(), to.getZ());
        boolean success = false;
        boolean result = true;
        for(int z = minZ; z <= maxZ; z++)
        {
            for(int y = minY; y <= maxY; y++)
            {
                for(int x = minX; x <= maxX; x++)
                {
                    success = setBlockState(toPos(x, y, z), state, flags);
                    if(!ignoreError && !success)
                        return false;
                    if(!success)
                        result = false;
                }
            }
        }
        return result;
    }

    public boolean fillBlockState(IBlockState state, BlockPos from, BlockPos to, boolean ignoreError, EnumWorldFlag... flags)
    {
        return fillBlockState(state, from, to, ignoreError, getFlagValue(flags));
    }

    public boolean fillBlockState(IBlockState state, BlockPos from, BlockPos to, boolean ignoreError)
    {
        return fillBlockState(state, from, to, ignoreError, 3);
    }

    public boolean fillBlockState(IBlockState state, BlockPos from, BlockPos to)
    {
        return fillBlockState(state, from, to, true, 3);
    }

    public boolean fillBlockState(IBlockState state, int x1, int y1, int z1, int x2, int y2, int z2, boolean ignoreError, int flags)
    {
        return fillBlockState(state, toPos(x1, y1, z1), toPos(x2, y2, z2), ignoreError, flags);
    }

    public boolean fillBlockState(IBlockState state, int x1, int y1, int z1, int x2, int y2, int z2, boolean ignoreError, EnumWorldFlag... flags)
    {
        return fillBlockState(state, toPos(x1, y1, z1), toPos(x2, y2, z2), ignoreError, flags);
    }

    public boolean fillBlockState(IBlockState state, int x1, int y1, int z1, int x2, int y2, int z2, boolean ignoreError)
    {
        return fillBlockState(state, toPos(x1, y1, z1), toPos(x2, y2, z2), ignoreError);
    }

    public boolean fillBlockState(IBlockState state, int x1, int y1, int z1, int x2, int y2, int z2)
    {
        return fillBlockState(state, toPos(x1, y1, z1), toPos(x2, y2, z2));
    }

    public boolean isBlockLoaded(BlockPos pos, boolean allowEmpty)
    {
        return world.isBlockLoaded(pos, allowEmpty);
    }

    public boolean isBlockLoaded(int x, int y, int z, boolean allowEmpty)
    {
        return isBlockLoaded(toPos(x, y, z), allowEmpty);
    }

    public boolean isBlockLoaded(BlockPos pos)
    {
        return isBlockLoaded(pos, true);
    }

    public boolean isBlockLoaded(int x, int y, int z)
    {
        return isBlockLoaded(toPos(x, y, z), true);
    }

    public Chunk getChunkFromBlockCoords(BlockPos pos)
    {
        return world.getChunkFromBlockCoords(pos);
    }

    public Chunk getChunkFromBlockCoords(int x, int y, int z)
    {
        return getChunkFromBlockCoords(toPos(x, y, z));
    }

    public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ)
    {
        return world.getChunkFromChunkCoords(chunkX, chunkZ);
    }

    public boolean setBlockToAir(BlockPos pos)
    {
        return world.setBlockToAir(pos);
    }

    public boolean setBlockToAir(int x, int y, int z)
    {
        return setBlockToAir(toPos(x, y, z));
    }

    public boolean destroyBlock(BlockPos pos, boolean dropBlock)
    {
        return world.destroyBlock(pos, dropBlock);
    }

    public boolean destroyBlock(int x, int y, int z, boolean dropBlock)
    {
        return destroyBlock(toPos(x, y, z), dropBlock);
    }

    public void markBlockForUpdate(BlockPos pos)
    {
        world.markBlockForUpdate(pos);
    }

    public void markBlockForUpdate(int x, int y, int z)
    {
        markBlockForUpdate(toPos(x, y, z));
    }

    public void markBlocksDirtyVertical(int x1, int z1, int x2, int z2)
    {
        world.markBlocksDirtyVertical(x1, z1, x2, z2);
    }

    public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax)
    {
        world.markBlockRangeForRenderUpdate(rangeMin, rangeMax);
    }

    public void markBlockRangeForRenderUpdate(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        world.markBlockRangeForRenderUpdate(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType)
    {
        world.notifyNeighborsOfStateChange(pos, blockType);
    }

    public void notifyNeighborsOfStateChange(int x, int y, int z, Block blockType)
    {
        notifyNeighborsOfStateChange(toPos(x, y, z), blockType);
    }

    public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide)
    {
        world.notifyNeighborsOfStateExcept(pos, blockType, skipSide);
    }

    public void notifyNeighborsOfStateExcept(int x, int y, int z, Block blockType, EnumFacing skipSide)
    {
        notifyNeighborsOfStateExcept(toPos(x, y, z), blockType, skipSide);
    }

    public void notifyBlockOfStateChange(BlockPos pos, final Block blockIn)
    {
        world.notifyBlockOfStateChange(pos, blockIn);
    }

    public void notifyBlockOfStateChange(int x, int y, int z, final Block blockIn)
    {
        notifyBlockOfStateChange(toPos(x, y, z), blockIn);
    }

    public boolean canSeeSky(BlockPos pos)
    {
        return world.canSeeSky(pos);
    }

    public boolean canSeeSky(int x, int y, int z)
    {
        return canSeeSky(toPos(x, y, z));
    }

    public boolean canBlockSeeSky(BlockPos pos)
    {
        return world.canBlockSeeSky(pos);
    }

    public boolean canBlockSeeSky(int x, int y, int z)
    {
        return canBlockSeeSky(toPos(x, y, z));
    }

    public int getLight(BlockPos pos)
    {
        return world.getLight(pos);
    }

    public int getLight(int x, int y, int z)
    {
        return getLight(toPos(x, y, z));
    }

    public int getLight(BlockPos pos, boolean checkNeighbors)
    {
        return world.getLight(pos, checkNeighbors);
    }

    public int getLight(int x, int y, int z, boolean checkNeighbors)
    {
        return getLight(toPos(x, y, z), checkNeighbors);
    }

    public BlockPos getHorizon(BlockPos pos)
    {
        return getHorizon(pos);
    }

    public BlockPos getHorizon(int x, int y, int z)
    {
        return getHorizon(toPos(x, y, z));
    }

    public int getChunksLowestHorizon(int x, int z)
    {
        return world.getChunksLowestHorizon(x, z);
    }

    public int getLightFor(EnumSkyBlock type, BlockPos pos)
    {
        return world.getLightFor(type, pos);
    }

    public int getLightFor(EnumSkyBlock type, int x, int y, int z)
    {
        return getLightFor(type, toPos(x, y, z));
    }

    public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue)
    {
        world.setLightFor(type, pos, lightValue);
    }

    public void setLightFor(EnumSkyBlock type, int x, int y, int z, int lightValue)
    {
        setLightFor(type, toPos(x, y, z), lightValue);
    }

    public void notifyLightSet(BlockPos pos)
    {
        world.notifyLightSet(pos);
    }

    public void notifyLightSet(int x, int y, int z)
    {
        notifyLightSet(toPos(x, y, z));
    }

    public float getLightBrightness(BlockPos pos)
    {
        return world.getLightBrightness(pos);
    }

    public float getLightBrightness(int x, int y, int z)
    {
        return getLightBrightness(toPos(x, y, z));
    }

    public boolean isDaytime()
    {
        return world.isDaytime();
    }

    public MovingObjectPosition rayTraceBlocks(Vec3 vec1, Vec3 vec2)
    {
        return world.rayTraceBlocks(vec1, vec2);
    }

    public MovingObjectPosition rayTraceBlocks(Vec3 vec1, Vec3 vec2, boolean stopOnLiquid)
    {
        return world.rayTraceBlocks(vec1, vec2, stopOnLiquid);
    }

    public MovingObjectPosition rayTraceBlocks(Vec3 vec1, Vec3 vec2, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock)
    {
        return world.rayTraceBlocks(vec1, vec2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
    }

    public void playSoundAtEntity(Entity entity, String sound, float volume, float pitch)
    {
        world.playSoundAtEntity(entity, sound, volume, pitch);
    }

    public void playSoundToNearExcept(EntityPlayer player, String sound, float volume, float pitch)
    {
        world.playSoundToNearExcept(player, sound, volume, pitch);
    }

    public void playSoundEffect(double x, double y, double z, String soundName, float volume, float pitch)
    {
        world.playSoundEffect(x, y, z, soundName, volume, pitch);
    }

    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int ... p_175688_14_)
    {
        world.spawnParticle(particleType, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175688_14_);
    }

    public boolean spawnEntityInWorld(Entity targetEntity)
    {
        return world.spawnEntityInWorld(targetEntity);
    }

    public void removeEntity(Entity targetEntity)
    {
        world.removeEntity(targetEntity);
    }

    public Entity getEntityByID(int id)
    {
        return world.getEntityByID(id);
    }

    public int countEntities(Class entityType)
    {
        return world.countEntities(entityType);
    }

    public WorldType getWorldType()
    {
        return world.getWorldType();
    }

    public boolean isSidePowered(BlockPos pos, EnumFacing side)
    {
        return world.isSidePowered(pos, side);
    }

    public boolean isSidePowered(int x, int y, int z, EnumFacing side)
    {
        return isSidePowered(toPos(x, y, z), side);
    }

    public int getRedstonePower(BlockPos pos, EnumFacing facing)
    {
        return world.getRedstonePower(pos, facing);
    }

    public int getRedstonePower(int x, int y, int z, EnumFacing facing)
    {
        return getRedstonePower(toPos(x, y, z), facing);
    }

    public boolean isBlockPowered(BlockPos pos)
    {
        return world.isBlockPowered(pos);
    }

    public boolean isBlockPowered(int x, int y, int z)
    {
        return isBlockPowered(toPos(x, y, z));
    }

    public int isBlockIndirectlyGettingPowered(BlockPos pos)
    {
        return world.isBlockIndirectlyGettingPowered(pos);
    }

    public int isBlockIndirectlyGettingPowered(int x, int y, int z)
    {
        return isBlockIndirectlyGettingPowered(toPos(x, y, z));
    }

    public EntityPlayer getPlayerEntityByName(String name)
    {
        return world.getPlayerEntityByName(name);
    }

    public EntityPlayer getPlayerEntityByUUID(UUID uuid)
    {
        return world.getPlayerEntityByUUID(uuid);
    }

    public EntityPlayer getPlayerEntityByUUID(String uuid)
    {
        UUID uid = UUID.fromString(uuid);
        if(uuid == null)
            return null;
        return  getPlayerEntityByUUID(uid);
    }

    public long getSeed()
    {
        return world.getSeed();
    }

    public long getTotalWorldTime()
    {
        return world.getTotalWorldTime();
    }

    public long getWorldTime()
    {
        return world.getWorldTime();
    }

    public void setWorldTime(long time)
    {
        world.setWorldTime(time);
    }

    public BlockPos getSpawnPoint()
    {
        return world.getSpawnPoint();
    }

    public void setSpawnPoint(BlockPos pos)
    {
        world.setSpawnPoint(pos);
    }

    public void setSpawnPoint(int x, int y, int z)
    {
        setSpawnPoint(toPos(x, y, z));
    }

    public GameRules getGameRules()
    {
        return world.getGameRules();
    }

    public boolean isRaining()
    {
        return world.isRaining();
    }

    public boolean isThundering()
    {
        return world.isThundering();
    }

    public void playAuxSFX(int soundType, BlockPos pos, int soundData)
    {
        world.playAuxSFX(soundType, pos, soundData);
    }

    public void playAuxSFX(int soundType, int x, int y, int z, int soundData)
    {
        playAuxSFX(soundData, toPos(x, y, z), soundData);
    }

    public void playAuxSFXAtEntity(EntityPlayer player, int soundType, BlockPos pos, int soundData)
    {
        world.playAuxSFXAtEntity(player, soundType, pos, soundData);
    }

    public void playAuxSFXAtEntity(EntityPlayer player, int soundType, int x, int y, int z, int soundData)
    {
        playAuxSFXAtEntity(player, soundType, toPos(x, y, z), soundData);
    }

    public int getHeight()
    {
        return world.getHeight();
    }

    public int getActualHeight()
    {
        return world.getActualHeight();
    }

    public Scoreboard getScoreboard()
    {
        return world.getScoreboard();
    }

    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
    {
        return world.isSideSolid(pos, side, _default);
    }

    public boolean isSideSolid(int x, int y, int z, EnumFacing side, boolean _default)
    {
        return isSideSolid(toPos(x, y, z), side, _default);
    }

    public WorldBorder getWorldBorder()
    {
        return world.getWorldBorder();
    }

    public int getBlockLightOpacity(BlockPos pos)
    {
        return world.getBlockLightOpacity(pos);
    }

    public int getBlockLightOpacity(int x, int y, int z)
    {
        return getBlockLightOpacity(toPos(x, y, z));
    }

    public boolean isAnyLiquid(AxisAlignedBB aabb)
    {
        return world.isAnyLiquid(aabb);
    }

    public List getEntitiesWithinAABB(Class entityCls, AxisAlignedBB aabb)
    {
        return world.getEntitiesWithinAABB(entityCls, aabb);
    }

    public List getEntitiesWithinAABB(Class entityCls, AxisAlignedBB aabb, Predicate filter)
    {
        return world.getEntitiesWithinAABB(entityCls, aabb, filter);
    }

    public Entity findNearestEntityWithinAABB(Class entityCls, AxisAlignedBB aabb, Entity closestTo)
    {
        return world.findNearestEntityWithinAABB(entityCls, aabb, closestTo);
    }

    public List getPlayers(Class playerType, Predicate filter)
    {
        return world.getPlayers(playerType, filter);
    }

    public int getDimensionID()
    {
        return provider.getDimensionId();
    }

    public void dropItem(ItemStack stack, BlockPos pos)
    {
        dropItem(stack, pos.getX(), pos.getY(), pos.getZ());
    }

    public void dropItem(ItemStack stack, int x, int y, int z)
    {
        ItemUtil.dropItem(stack, world, x, y, z);
    }

    private int getFlagValue(EnumWorldFlag[] flags)
    {
        int f = 0;
        for(int i = 0; i < flags.length; i++)
            f += flags[i].getValue();
        return f;
    }
}
