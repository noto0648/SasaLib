package sasalib;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sasalib.event.SasaLibLoadEvent;
import sasalib.event.SasaLibPreLoadEvent;
import sasalib.gui.SasaGuiHandler;
import sasalib.packet.PacketDispatcher;
import sasalib.registry.RegistryManager;

/**
 * Created by Noto on 2015/05/06.
 */
@Mod(modid = SasaLib.MOD_ID, name="Sasa Lib", version = "1.8")
public class SasaLib
{
    public static final String MOD_ID = "SasaLib";
    public static final Logger LOGGER = LogManager.getLogger("SasaLib");

    @SidedProxy(serverSide = "sasalib.SasaServerProxy", clientSide = "sasalib.SasaClientProxy")
    public static SasaServerProxy proxy;

    @Mod.Instance(MOD_ID)
    public static SasaLib instance;

    private void registerEvent()
    {
        MinecraftForge.EVENT_BUS.register(RegistryManager.INSTANCE);
        MinecraftForge.EVENT_BUS.register(PacketDispatcher.INSTANCE);
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new SasaGuiHandler());
        MinecraftForge.EVENT_BUS.post(new SasaLibLoadEvent(this));
    }

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event)
    {
        registerEvent();
        MinecraftForge.EVENT_BUS.post(new SasaLibPreLoadEvent(this));
    }

}
