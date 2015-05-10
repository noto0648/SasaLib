package sasalib.registry;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sasalib.event.SasaLibLoadEvent;

/**
 * Created by Noto on 2015/05/09.
 */
public class RegistryManager
{
    public static final RegistryManager INSTANCE = new RegistryManager();

    private RegistryManager() {}

    @SubscribeEvent
    public void initialize(SasaLibLoadEvent event)
    {
        GameRegistry.registerFuelHandler(FuelRegistry.INSTANCE.getFuelHandler());
    }

}
