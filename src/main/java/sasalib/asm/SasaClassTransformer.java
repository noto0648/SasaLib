package sasalib.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import sasalib.SasaLib;


/**
 * Created by Noto on 2015/05/09.
 */
public class SasaClassTransformer implements IClassTransformer, Opcodes
{
    private static final String TARGET_CLASS_NAME2 = "net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer";

    public SasaClassTransformer() {}

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        if (FMLLaunchHandler.side().isClient() && transformedName.equals(TARGET_CLASS_NAME2))
        {
            return transFormMethod(bytes);
        }
        return bytes;
    }

    private byte[] transFormMethod(byte[] bytes)
    {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(cnode, 0);

        String targetMethodName = "renderByItem";
        String targetMethoddesc = "(Lnet/minecraft/item/ItemStack;)V";


        MethodNode mnode = null;
        for (MethodNode curMnode : cnode.methods)
        {
            if (targetMethodName.equals(curMnode.name) && targetMethoddesc.equals(curMnode.desc))
            {
                mnode = curMnode;
                break;
            }
        }
        if(mnode != null)
        {
            SasaLib.LOGGER.info("TileEntityItemStackRenderer transform start");
            InsnList overrideList = new InsnList();
            overrideList.add(new VarInsnNode(ALOAD, 0));
            overrideList.add(new VarInsnNode(ALOAD, 1));
            overrideList.add(new MethodInsnNode(INVOKESTATIC, "sasalib/render/TileRenderAdvanced", "renderByItem", "(Lnet/minecraft/client/renderer/tileentity/TileEntityItemStackRenderer;Lnet/minecraft/item/ItemStack;)Z", false));
            Label l1 = new Label();
            overrideList.add(new JumpInsnNode(IFEQ, new LabelNode(l1)));
            Label l2 = new Label();
            overrideList.add(new LabelNode(l2));
            overrideList.add(new InsnNode(RETURN));
            overrideList.add(new LabelNode(l1));

            mnode.instructions.insert(mnode.instructions.get(0), overrideList);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cnode.accept(cw);
            bytes = cw.toByteArray();
            SasaLib.LOGGER.info("TileEntityItemStackRenderer transform finished");
        }
        return bytes;
    }

}
