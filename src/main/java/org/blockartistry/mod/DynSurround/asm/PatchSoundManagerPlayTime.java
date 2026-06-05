package org.blockartistry.mod.DynSurround.asm;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class PatchSoundManagerPlayTime extends Transmorgrifier {
   public PatchSoundManagerPlayTime() {
      super("net.minecraft.client.audio.SoundManager");
   }

   public String name() {
      return "SoundManager playTime";
   }

   public boolean transmorgrify(ClassNode cn) {
      String[] names = new String[]{"playSound", "playSound"};
      String sig = "(Lnet/minecraft/client/audio/ISound;)V";
      MethodNode m = this.findMethod(cn, "(Lnet/minecraft/client/audio/ISound;)V", names);
      if (m != null) {
         this.logMethod(Transformer.log(), m, "Found!");

         for(int i = 0; i < m.instructions.size(); ++i) {
            AbstractInsnNode node = m.instructions.get(i);
            if (node instanceof IntInsnNode) {
               IntInsnNode intNode = (IntInsnNode)node;
               if (intNode.operand == 20) {
                  m.instructions.set(node, new IntInsnNode(16, 0));
                  return true;
               }
            }
         }
      } else {
         Transformer.log().error("Unable to locate method {}{}", new Object[]{names[0], "(Lnet/minecraft/client/audio/ISound;)V"});
      }

      Transformer.log().info("Unable to patch [{}]!", new Object[]{this.getClassName()});
      return false;
   }
}
