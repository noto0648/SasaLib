package sasalib.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by Noto on 2015/05/10.
 */
public class SasaPacket extends SasaPacketBase
{
    private ByteArrayOutputStream outputStream;
    private DataOutputStream writer;
    public DataInputStream input;

    public SasaPacket()
    {
        outputStream = new ByteArrayOutputStream();
        writer = new DataOutputStream(outputStream);
    }

    @Override
    public void toByteBuf(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
    {
        paramByteBuf.writeBytes(outputStream.toByteArray());
    }

    @Override
    public void fromByteBuf(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
    {
        input = new DataInputStream(new ByteArrayInputStream(paramByteBuf.array()));
        try
        {
            input.skipBytes(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveClient(EntityPlayer paramEntityPlayer)
    {
        receive(paramEntityPlayer, true);
    }

    @Override
    public void receiveServer(EntityPlayer paramEntityPlayer)
    {
        receive(paramEntityPlayer, false);
    }

    protected void receive(EntityPlayer player, boolean isRemote)
    {

    }

    public void addInt(int b)
    {
        try
        {
            writer.writeInt(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addByte(byte b)
    {
        try
        {
            writer.writeByte(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addShort(short b)
    {
        try
        {
            writer.writeShort(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addLong(long b)
    {
        try
        {
            writer.writeLong(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addFloat(float b)
    {
        try
        {
            writer.writeFloat(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addDouble(double b)
    {
        try
        {
            writer.writeDouble(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addBool(boolean b)
    {
        try
        {
            writer.writeBoolean(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addString(String b)
    {
        try
        {
            writer.writeUTF(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getInt()
    {
        try
        {
            return input.readInt();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public byte getByte()
    {
        try
        {
            return input.readByte();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public short getShort()
    {
        try
        {
            return input.readShort();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public long getLong()
    {
        try
        {
            return input.readLong();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public float getFloat()
    {
        try
        {
            return input.readFloat();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0F;
    }


    public double getDouble()
    {
        try
        {
            return input.readDouble();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0D;
    }

    public boolean getBool()
    {
        try
        {
            return input.readBoolean();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public String getString()
    {
        try
        {
            return input.readUTF();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void addByteArray(byte[] array)
    {
        try
        {
            addShort((short) array.length);
            writer.write(array);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public byte[] getByteArray()
    {
        try
        {
            int l = getShort();
            byte[] r = new byte[l];
            input.readFully(r);
            return r;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public void addNBTTag(NBTTagCompound compound)
    {
        try
        {
            CompressedStreamTools.writeCompressed(compound, writer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public NBTTagCompound getNBTTag()
    {
        try
        {
            return CompressedStreamTools.func_152456_a(input, new NBTSizeTracker(Long.MAX_VALUE));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void addFluidStack(FluidStack b)
    {
        if(b == null)
        {
            addByte((byte) 1);
            return;
        }
        addByte((byte)0);
        addNBTTag(b.writeToNBT(new NBTTagCompound()));
    }

    public FluidStack getFluidStack()
    {
        byte b = getByte();
        if(b == 0)
        {
            return FluidStack.loadFluidStackFromNBT(getNBTTag());
        }
        return null;
    }

    public void addItemStack(ItemStack b)
    {
        if(b == null)
        {
            addByte((byte) 1);
            return;
        }
        addByte((byte)0);
        addNBTTag(b.writeToNBT(new NBTTagCompound()));
    }

    public ItemStack getItemStack()
    {
        byte b = getByte();
        if(b == 0)
        {
            return ItemStack.loadItemStackFromNBT(getNBTTag());
        }
        return null;
    }

}
