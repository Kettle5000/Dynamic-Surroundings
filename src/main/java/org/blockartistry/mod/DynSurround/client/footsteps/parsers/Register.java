package org.blockartistry.mod.DynSurround.client.footsteps.parsers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Map;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IRegistration;
import org.blockartistry.mod.DynSurround.client.footsteps.util.property.simple.ConfigProperty;

@SideOnly(Side.CLIENT)
public final class Register {
   private Register() {
   }

   public static void setup(ConfigProperty props, IRegistration registration) {
      Map<String, String> properties = props.getAllProperties();

      for(Map.Entry<String, String> entry : properties.entrySet()) {
         try {
            registration.register((String)entry.getKey(), (String)entry.getValue());
         } catch (Exception e) {
            ModLog.info("Error making registration " + (String)entry.getKey() + ": " + e.getMessage());
         }
      }

   }
}
