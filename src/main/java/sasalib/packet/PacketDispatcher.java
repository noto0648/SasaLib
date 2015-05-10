package sasalib.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sasalib.event.SasaLibLoadEvent;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Noto on 2015/05/10.
 */
@ChannelHandler.Sharable
public class PacketDispatcher extends MessageToMessageCodec<FMLProxyPacket, SasaPacketBase>
{
    public static final PacketDispatcher INSTANCE = new PacketDispatcher();
    private LinkedList<Class<? extends SasaPacketBase>> packets = new LinkedList<Class<? extends SasaPacketBase>>();
    private EnumMap<Side, FMLEmbeddedChannel> channels;

    @SubscribeEvent
    public void initialize(SasaLibLoadEvent event)
    {
        channels = NetworkRegistry.INSTANCE.newChannel("SasaLib", this);

        registerPacket(SasaPacket.class);
    }

    public void registerPacket(Class<? extends SasaPacket> cls)
    {
        packets.add(cls);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, SasaPacketBase msg, List<Object> out) throws Exception
    {
        PacketBuffer buf = (PacketBuffer)Unpooled.buffer();
        buf.writeByte(packets.indexOf(msg.getClass()));
        msg.toByteBuf(ctx, buf);
        FMLProxyPacket localFMLProxyPacket = new FMLProxyPacket((PacketBuffer)buf.copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(localFMLProxyPacket);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception
    {
        ByteBuf buf = msg.payload();
        int packetId = buf.readByte();
        Class pktCls = packets.get(packetId);
        SasaPacketBase pkt = (SasaPacketBase)pktCls.newInstance();
        pkt.fromByteBuf(ctx, buf);
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            pkt.receiveClient(getClientPlayer());
        }
        else if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        {
            INetHandler localINetHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
            EntityPlayer ep = ((NetHandlerPlayServer)localINetHandler).playerEntity;
            pkt.receiveServer(ep);
        }
    }

    @SideOnly(Side.CLIENT)
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    public void sendToServer(SasaPacketBase packet)
    {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeOutbound(packet);
    }

    public void sendToPlayer(SasaPacketBase packet, EntityPlayer player)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeOutbound(packet);
    }

    public void sendToAll(SasaPacketBase packet)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeOutbound(packet);
    }

    public Packet toMCPacket(SasaPacketBase packet)
    {
        return ((FMLEmbeddedChannel)channels.get(FMLCommonHandler.instance().getEffectiveSide())).generatePacketFrom(packet);
    }

    public void sendToAllAround(SasaPacketBase packet, World world, int x, int y, int z)
    {
        (INSTANCE.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        (INSTANCE.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), x, y, z, 192.0D));
        (INSTANCE.channels.get(Side.SERVER)).writeAndFlush(packet);
    }

    public void sendToDimension(SasaPacketBase packet, int dimension)
    {
        (INSTANCE.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        (INSTANCE.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(Integer.valueOf(dimension));
        (INSTANCE.channels.get(Side.SERVER)).writeAndFlush(packet);
    }
}
