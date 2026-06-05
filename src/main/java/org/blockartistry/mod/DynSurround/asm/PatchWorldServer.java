package org.blockartistry.mod.DynSurround.asm;

import org.blockartistry.mod.DynSurround.ModOptions;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PatchWorldServer extends Transmorgrifier {
   public PatchWorldServer() {
      super("net.minecraft.world.WorldServer");
   }

   public String name() {
      return "resetRainAndThunder";
   }

   public boolean isEnabled() {
      return !ModOptions.disableWeatherEffects;
   }

   public boolean transmorgrify(ClassNode cn) {
      String[] names = new String[]{"resetRainAndThunder", "resetRainAndThunder"};
      String sigs = "()V";
      MethodNode m = this.findMethod(cn, "()V", names);
      if (m != null) {
         this.logMethod(Transformer.log(), m, "Found!");
         m.localVariables = null;
         InsnList list = new InsnList();
         list.add(new VarInsnNode(25, 0));
         String owner = "org/blockartistry/mod/DynSurround/server/PlayerSleepHandler";
         String targetName = "resetRainAndThunder";
         String sig = "(Lnet/minecraft/world/WorldServer;)V";
         list.add(new MethodInsnNode(184, "org/blockartistry/mod/DynSurround/server/PlayerSleepHandler", "resetRainAndThunder", "(Lnet/minecraft/world/WorldServer;)V", false));
         list.add(new InsnNode(177));
         m.instructions = list;
         return true;
      } else {
         Transformer.log().info("Unable to patch [net.minecraft.world.WorldServer]!");
         return false;
      }
   }
}
