package org.blockartistry.mod.DynSurround.asm;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PatchSoundManager extends Transmorgrifier {
   public PatchSoundManager() {
      super("net.minecraft.client.audio.SoundManager");
   }

   public String name() {
      return "Sound Caching";
   }

   public boolean transmorgrify(ClassNode cn) {
      String[] names = new String[]{"getURLForSoundResource", "getURLForSoundResource"};
      String sig = "(Lnet/minecraft/util/ResourceLocation;)Ljava/net/URL;";
      MethodNode m = this.findMethod(cn, "(Lnet/minecraft/util/ResourceLocation;)Ljava/net/URL;", names);
      if (m != null) {
         this.logMethod(Transformer.log(), m, "Found!");
         InsnList list = new InsnList();
         list.add(new VarInsnNode(25, 0));
         String owner = "org/blockartistry/mod/DynSurround/client/sound/cache/SoundCache";
         String targetName = "getURLForSoundResource";
         String sig1 = "(Lnet/minecraft/util/ResourceLocation;)Ljava/net/URL;";
         list.add(new MethodInsnNode(184, "org/blockartistry/mod/DynSurround/client/sound/cache/SoundCache", "getURLForSoundResource", "(Lnet/minecraft/util/ResourceLocation;)Ljava/net/URL;", false));
         list.add(new InsnNode(176));
         m.instructions.insert(m.instructions.getFirst(), list);
         return true;
      } else {
         Transformer.log().error("Unable to locate method {}{}", new Object[]{names[0], "(Lnet/minecraft/util/ResourceLocation;)Ljava/net/URL;"});
         Transformer.log().info("Unable to patch [{}]!", new Object[]{this.getClassName()});
         return false;
      }
   }
}
