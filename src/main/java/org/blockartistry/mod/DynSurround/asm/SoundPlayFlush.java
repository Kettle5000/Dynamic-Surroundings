package org.blockartistry.mod.DynSurround.asm;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class SoundPlayFlush extends Transmorgrifier {
   public SoundPlayFlush() {
      super("net.minecraft.client.audio.SoundManager");
   }

   public String name() {
      return "SoundManager playSound flush";
   }

   public boolean transmorgrify(ClassNode cn) {
      String[] names = new String[]{"playSound", "playSound"};
      String sig = "(Lnet/minecraft/client/audio/ISound;)V";
      MethodNode m = this.findMethod(cn, "(Lnet/minecraft/client/audio/ISound;)V", names);
      if (m != null) {
         this.logMethod(Transformer.log(), m, "Found!");
         String owner = "org/blockartistry/mod/DynSurround/client/sound/SoundManager";
         String targetName = "flushSound";
         String sig1 = "()V";
         InsnList list = new InsnList();
         list.add(new MethodInsnNode(184, "org/blockartistry/mod/DynSurround/client/sound/SoundManager", "flushSound", "()V", false));
         Iterator<?> iterator = m.instructions.iterator();

         while(iterator.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
            if (insn instanceof MethodInsnNode) {
               MethodInsnNode mn = (MethodInsnNode)insn;
               if (mn.owner.equals("net/minecraft/client/audio/SoundManager$SoundSystemStarterThread") && mn.name.equals("play")) {
                  m.instructions.insert(insn, list);
                  return true;
               }
            }
         }

         return false;
      } else {
         Transformer.log().error("Unable to locate method {}{}", new Object[]{names[0], "(Lnet/minecraft/client/audio/ISound;)V"});
         Transformer.log().info("Unable to patch [{}]!", new Object[]{this.getClassName()});
         return false;
      }
   }
}
