package org.blockartistry.mod.DynSurround.client.footsteps.util.property.simple;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.blockartistry.mod.DynSurround.util.JsonUtils;

@SideOnly(Side.CLIENT)
public class ConfigProperty {
   private Map<String, String> properties = new HashMap();

   public String getString(String name) {
      if (!this.properties.containsKey(name)) {
         throw new PropertyMissingException();
      } else {
         return (String)this.properties.get(name);
      }
   }

   public boolean getBoolean(String name) {
      if (!this.properties.containsKey(name)) {
         throw new PropertyMissingException();
      } else {
         try {
            return Boolean.parseBoolean((String)this.properties.get(name));
         } catch (NumberFormatException var3) {
            throw new PropertyTypeException();
         }
      }
   }

   public int getInteger(String name) {
      if (!this.properties.containsKey(name)) {
         throw new PropertyMissingException();
      } else {
         try {
            return Integer.parseInt((String)this.properties.get(name));
         } catch (NumberFormatException var3) {
            throw new PropertyTypeException();
         }
      }
   }

   public float getFloat(String name) {
      if (!this.properties.containsKey(name)) {
         throw new PropertyMissingException();
      } else {
         try {
            return Float.parseFloat((String)this.properties.get(name));
         } catch (NumberFormatException var3) {
            throw new PropertyTypeException();
         }
      }
   }

   public long getLong(String name) {
      if (!this.properties.containsKey(name)) {
         throw new PropertyMissingException();
      } else {
         try {
            return Long.parseLong((String)this.properties.get(name));
         } catch (NumberFormatException var3) {
            throw new PropertyTypeException();
         }
      }
   }

   public double getDouble(String name) {
      if (!this.properties.containsKey(name)) {
         throw new PropertyMissingException();
      } else {
         try {
            return Double.parseDouble((String)this.properties.get(name));
         } catch (NumberFormatException var3) {
            throw new PropertyTypeException();
         }
      }
   }

   public void setProperty(String name, Object o) {
      this.properties.put(name, o.toString());
   }

   public Map<String, String> getAllProperties() {
      return this.properties;
   }

   public static ConfigProperty fromStream(InputStream stream) {
      ConfigProperty props = new ConfigProperty();
      loadStream(props, stream);
      return props;
   }

   public static boolean loadStream(ConfigProperty properties, InputStream stream) {
      if (stream == null) {
         return false;
      } else {
         properties.properties = (Map)JsonUtils.load(stream, properties.properties.getClass());
         return true;
      }
   }
}
