package sasalib.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Noto on 2015/05/10.
 */
public abstract class SasaPacketBase
{
    public abstract void toByteBuf(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf);

    public abstract void fromByteBuf(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf);

    public abstract void receiveClient(EntityPlayer paramEntityPlayer);

    public abstract void receiveServer(EntityPlayer paramEntityPlayer);
}
