package org.blockartistry.mod.DynSurround.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Property;
import org.apache.commons.lang3.StringUtils;

@SideOnly(Side.CLIENT)
public class MyConfigElement<T> extends ConfigElement<T> {
   protected String label;

   public MyConfigElement(ConfigCategory ctgy) {
      this((ConfigCategory)ctgy, (String)null);
   }

   public MyConfigElement(ConfigCategory ctgy, String label) {
      super(ctgy);
      this.label = label;
   }

   public MyConfigElement(Property prop) {
      this((Property)prop, (String)null);
   }

   public MyConfigElement(Property prop, String label) {
      super(prop);
      this.label = label;
   }

   public String getName() {
      return StringUtils.isEmpty(this.label) ? super.getName() : this.label;
   }
}
