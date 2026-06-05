package org.blockartistry.mod.DynSurround.client.footsteps.engine.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.EnumMap;
import java.util.Map;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IOptions;

@SideOnly(Side.CLIENT)
public class ConfigOptions implements IOptions {
   private final Map<IOptions.Option, Object> map = new EnumMap(IOptions.Option.class);

   public Map<IOptions.Option, Object> getMap() {
      return this.map;
   }

   public boolean hasOption(IOptions.Option option) {
      return this.map.containsKey(option);
   }

   public Object getOption(IOptions.Option option) {
      return this.map.get(option);
   }
}
