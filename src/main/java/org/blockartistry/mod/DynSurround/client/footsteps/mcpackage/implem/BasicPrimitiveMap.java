package org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.LinkedHashMap;
import java.util.Map;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IPrimitiveMap;

@SideOnly(Side.CLIENT)
public class BasicPrimitiveMap implements IPrimitiveMap {
   private final Map<String, String> primitiveMap = new LinkedHashMap();

   public String getPrimitiveMap(String primitive) {
      return (String)this.primitiveMap.get(primitive);
   }

   public String getPrimitiveMapSubstrate(String primitive, String substrate) {
      return (String)this.primitiveMap.get(primitive + "@" + substrate);
   }

   public void register(String key, String value) {
      this.primitiveMap.put(key, value);
   }
}
