package sasalib;

import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sasalib.model.SimpleIconLoader;

/**
 * Created by Noto on 2015/05/10.
 */
@SideOnly(Side.CLIENT)
public class SasaClientProxy extends SasaServerProxy
{
    @Override
    public void load(FMLInitializationEvent event)
    {

    }

    @Override
    public void preLoad(FMLPreInitializationEvent event)
    {
        ModelLoaderRegistry.registerLoader(new SimpleIconLoader());
    }
}
