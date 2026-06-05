package org.blockartistry.mod.DynSurround.asm;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class SoundCrashFixLibrary extends Transmorgrifier {
   public SoundCrashFixLibrary() {
      super("paulscode.sound.Library");
   }

   public String name() {
      return "removeSource";
   }

   public boolean transmorgrify(ClassNode cn) {
      MethodNode m = this.findMethod(cn, "(Ljava/lang/String;)V", "removeSource");
      if (m != null) {
         Iterator<?> iterator = m.instructions.iterator();

         while(iterator.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
            if (insn instanceof MethodInsnNode && ((MethodInsnNode)insn).owner.equals("paulscode/sound/Source") && ((MethodInsnNode)insn).name.equals("cleanup")) {
               m.instructions.insertBefore(insn, new MethodInsnNode(184, "org/blockartistry/mod/DynSurround/client/sound/fix/SoundFixMethods", "cleanupSource", "(Lpaulscode/sound/Source;)V", false));
               m.instructions.remove(insn);
               return true;
            }
         }
      }

      return false;
   }
}
