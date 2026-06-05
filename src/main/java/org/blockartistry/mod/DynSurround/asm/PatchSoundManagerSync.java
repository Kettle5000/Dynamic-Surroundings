package org.blockartistry.mod.DynSurround.asm;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class PatchSoundManagerSync extends Transmorgrifier {
   public PatchSoundManagerSync() {
      super("net.minecraft.client.audio.SoundManager");
   }

   public String name() {
      return "SoundManager synchronization";
   }

   public boolean transmorgrify(ClassNode cn) {
      for(MethodNode m : cn.methods) {
         if (!m.name.startsWith("<") && (m.access & 1) != 0 && (m.access & 32) == 0) {
            this.logMethod(Transformer.log(), m, "Synchronized!");
            m.access |= 32;
         }
      }

      return true;
   }
}
