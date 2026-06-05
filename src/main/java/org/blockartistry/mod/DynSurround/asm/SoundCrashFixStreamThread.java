package org.blockartistry.mod.DynSurround.asm;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class SoundCrashFixStreamThread extends Transmorgrifier {
   public SoundCrashFixStreamThread() {
      super("paulscode.sound.StreamThread");
   }

   public String name() {
      return "removeSource";
   }

   public boolean transmorgrify(ClassNode cn) {
      MethodNode m = this.findMethod(cn, "()V", "run");
      if (m != null) {
         Iterator<?> iterator = m.instructions.iterator();

         while(iterator.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
            if (insn instanceof MethodInsnNode && ((MethodInsnNode)insn).owner.equals("java/util/ListIterator") && ((MethodInsnNode)insn).name.equals("next")) {
               insn = insn.getNext().getNext();
               LocalVariableNode var = this.findLocalVariable(m, "src");
               if (var != null) {
                  m.instructions.insert(insn, new VarInsnNode(58, var.index));
                  m.instructions.insert(insn, new MethodInsnNode(184, "org/blockartistry/mod/DynSurround/client/sound/fix/SoundFixMethods", "removeSource", "(Lpaulscode/sound/Source;)Lpaulscode/sound/Source;", false));
                  m.instructions.insert(insn, new VarInsnNode(25, var.index));
                  return true;
               }
            }
         }
      }

      return false;
   }
}
