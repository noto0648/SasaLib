package sasalib.asm;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

import java.util.Arrays;

/**
 * Created by Noto on 2015/05/09.
 */
public class SasaLibAsmCore extends DummyModContainer
{
    public SasaLibAsmCore()
    {
        super(new ModMetadata());

        ModMetadata meta = super.getMetadata();
        meta.modId = "sasaLibAsm";
        meta.name = "Sasa Lib Asm Core";
        meta.version = "1.0";
        meta.authorList = Arrays.asList(new String[]{"Noto"});
        //meta.description = "A tutorial access transformer mod.";
        //meta.url = "http://...";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController lc)
    {
        bus.register(this);
        return true;
    }
}
