package org.blockartistry.mod.DynSurround.asm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public abstract class Transmorgrifier {
   private final String className;

   public Transmorgrifier(@Nonnull String className) {
      this.className = className;
   }

   public int classWriterFlags() {
      return 3;
   }

   @Nonnull
   public abstract String name();

   @Nonnull
   public String getClassName() {
      return this.className;
   }

   public boolean isEnabled() {
      return true;
   }

   public boolean matches(@Nonnull String name) {
      return this.className.equals(name);
   }

   public abstract boolean transmorgrify(ClassNode var1);

   @Nullable
   protected MethodNode findMethod(@Nonnull ClassNode cn, @Nonnull String signature, @Nonnull String name) {
      for(MethodNode m : cn.methods) {
         if (m.name.equals(name) && m.desc.equals(signature)) {
            return m;
         }
      }

      return null;
   }

   @Nullable
   protected MethodNode findMethod(@Nonnull ClassNode cn, @Nonnull String signature, @Nonnull String... names) {
      for(int i = 0; i < names.length; ++i) {
         MethodNode m = this.findMethod(cn, signature, names[i]);
         if (m != null) {
            return m;
         }
      }

      return null;
   }

   @Nullable
   protected MethodNode findCTOR(@Nonnull ClassNode cn, @Nonnull String signature) {
      return this.findMethod(cn, signature, "<init>");
   }

   @Nullable
   protected LocalVariableNode findLocalVariable(@Nonnull MethodNode m, @Nonnull String name) {
      for(LocalVariableNode v : m.localVariables) {
         if (v.name.equals(name)) {
            return v;
         }
      }

      return null;
   }

   protected void logMethod(@Nonnull Logger log, @Nonnull MethodNode node, @Nonnull String message) {
      String text = String.format("%s%s: %s", node.name, node.desc, message);
      log.info(text);
   }
}
