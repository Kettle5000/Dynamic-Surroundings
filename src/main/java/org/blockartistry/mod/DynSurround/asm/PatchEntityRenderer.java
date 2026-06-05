package org.blockartistry.mod.DynSurround.asm;

import org.blockartistry.mod.DynSurround.ModOptions;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PatchEntityRenderer extends Transmorgrifier {
   public PatchEntityRenderer() {
      super("net.minecraft.client.renderer.EntityRenderer");
   }

   public String name() {
      return "addRainParticles";
   }

   public boolean isEnabled() {
      return !ModOptions.disableWeatherEffects;
   }

   public boolean transmorgrify(ClassNode cn) {
      boolean failed = false;
      String[] names1 = new String[]{"addRainParticles", "addRainParticles"};
      String sigs1 = "()V";
      String[] names2 = new String[]{"renderRainSnow", "renderRainSnow"};
      String sigs2 = "(F)V";
      MethodNode m = this.findMethod(cn, "()V", names1);
      if (m != null) {
         this.logMethod(Transformer.log(), m, "Found!");
         m.localVariables = null;
         InsnList list = new InsnList();
         list.add(new VarInsnNode(25, 0));
         String owner = "org/blockartistry/mod/DynSurround/client/RenderWeather";
         String targetName = "addRainParticles";
         String sig = "(Lnet/minecraft/client/renderer/EntityRenderer;)V";
         list.add(new MethodInsnNode(184, "org/blockartistry/mod/DynSurround/client/RenderWeather", "addRainParticles", "(Lnet/minecraft/client/renderer/EntityRenderer;)V", false));
         list.add(new InsnNode(177));
         m.instructions = list;
      } else {
         failed = true;
      }

      m = this.findMethod(cn, "(F)V", names2);
      if (m != null) {
         this.logMethod(Transformer.log(), m, "Found!");
         InsnList list = new InsnList();
         list.add(new VarInsnNode(25, 0));
         list.add(new VarInsnNode(23, 1));
         String owner = "org/blockartistry/mod/DynSurround/client/RenderWeather";
         String targetName = "renderRainSnow";
         String sig = "(Lnet/minecraft/client/renderer/EntityRenderer;F)V";
         list.add(new MethodInsnNode(184, "org/blockartistry/mod/DynSurround/client/RenderWeather", "renderRainSnow", "(Lnet/minecraft/client/renderer/EntityRenderer;F)V", false));
         list.add(new InsnNode(177));
         m.instructions = list;
      } else {
         failed = true;
      }

      return !failed;
   }
}
