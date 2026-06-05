package org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IVariator;
import org.blockartistry.mod.DynSurround.client.footsteps.util.property.simple.ConfigProperty;

@SideOnly(Side.CLIENT)
public class NormalVariator implements IVariator {
   public int IMMOBILE_DURATION = 200;
   public boolean EVENT_ON_JUMP = true;
   public float LAND_HARD_DISTANCE_MIN = 0.9F;
   public float SPEED_TO_JUMP_AS_MULTIFOOT = 0.005F;
   public float SPEED_TO_RUN = 0.022F;
   public float DISTANCE_HUMAN = 0.95F;
   public float DISTANCE_STAIR = 0.61749995F;
   public float DISTANCE_LADDER = 0.5F;
   public boolean PLAY_WANDER = true;

   public void loadConfig(ConfigProperty config) {
      Set<String> keysFromConfig = config.getAllProperties().keySet();
      Set<String> keys = new HashSet();

      for(String key : keysFromConfig) {
         keys.add(key.toUpperCase());
      }

      Field[] fields = NormalVariator.class.getDeclaredFields();

      for(Field field : fields) {
         try {
            String fieldName = field.getName();
            if (keys.contains(fieldName)) {
               String lowercaseField = fieldName.toLowerCase();
               if (field.getType() == Float.TYPE) {
                  field.setFloat(this, config.getFloat(lowercaseField));
               } else if (field.getType() == Integer.TYPE) {
                  field.setInt(this, config.getInteger(lowercaseField));
               } else if (field.getType() == Boolean.TYPE) {
                  field.setBoolean(this, config.getBoolean(lowercaseField));
               }
            }
         } catch (Throwable e) {
            ModLog.info("Incompatible type: " + e.getClass().getName() + ": " + field.getName());
         }
      }

   }
}
