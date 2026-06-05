package org.blockartistry.mod.DynSurround.asm;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public class SoundCrashFixSource extends Transmorgrifier {
   public SoundCrashFixSource() {
      super("paulscode.sound.Source");
   }

   public String name() {
      return "Add removed field";
   }

   public boolean transmorgrify(ClassNode cn) {
      cn.fields.add(new FieldNode(1, "removed", "Z", (String)null, (Object)null));
      return true;
   }
}
