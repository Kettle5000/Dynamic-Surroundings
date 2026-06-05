package org.blockartistry.mod.DynSurround.asm;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PatchSoundManagerClampPitch extends Transmorgrifier {
   public PatchSoundManagerClampPitch() {
      super("net.minecraft.client.audio.SoundManager");
   }

   public String name() {
      return "SoundManager getNormalizedPitch";
   }

   public boolean transmorgrify(ClassNode cn) {
      String[] names = new String[]{"getNormalizedPitch", "getNormalizedPitch"};
      String sig = "(Lnet/minecraft/client/audio/ISound;Lnet/minecraft/client/audio/SoundPoolEntry;)F";
      MethodNode m = this.findMethod(cn, "(Lnet/minecraft/client/audio/ISound;Lnet/minecraft/client/audio/SoundPoolEntry;)F", names);
      if (m != null) {
         this.logMethod(Transformer.log(), m, "Found!");
         String owner = "org/blockartistry/mod/DynSurround/client/sound/SoundManager";
         String targetName = "getNormalizedPitch";
         InsnList list = new InsnList();
         list.add(new VarInsnNode(25, 1));
         list.add(new VarInsnNode(25, 2));
         list.add(new MethodInsnNode(184, "org/blockartistry/mod/DynSurround/client/sound/SoundManager", "getNormalizedPitch", "(Lnet/minecraft/client/audio/ISound;Lnet/minecraft/client/audio/SoundPoolEntry;)F", false));
         list.add(new InsnNode(174));
         m.instructions = list;
         return true;
      } else {
         Transformer.log().error("Unable to locate method {}{}", new Object[]{names[0], "(Lnet/minecraft/client/audio/ISound;Lnet/minecraft/client/audio/SoundPoolEntry;)F"});
         Transformer.log().info("Unable to patch [{}]!", new Object[]{this.getClassName()});
         return false;
      }
   }
}
