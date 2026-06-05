package org.blockartistry.mod.DynSurround.client.footsteps.parsers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JsonParseException extends Exception {
   private static final long serialVersionUID = 4586255498544473275L;

   public JsonParseException(String message) {
      super(message);
   }
}
