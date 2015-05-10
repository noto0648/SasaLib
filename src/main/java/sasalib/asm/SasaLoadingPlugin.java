package sasalib.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by Noto on 2015/05/09.
 */
public class SasaLoadingPlugin implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] { "sasalib.asm.SasaClassTransformer"};
    }

    @Override
    public String getModContainerClass()
    {
        return "sasalib.asm.SasaLibAsmCore";
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
