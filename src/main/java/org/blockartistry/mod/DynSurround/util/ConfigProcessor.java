package org.blockartistry.mod.DynSurround.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.commons.lang3.StringUtils;
import org.blockartistry.mod.DynSurround.ModLog;

public final class ConfigProcessor {
   public static void process(Configuration config, Class<?> clazz) {
      process(config, clazz, (Object)null);
   }

   public static void process(Configuration config, Class<?> clazz, Object parameters) {
      for(Field field : clazz.getFields()) {
         Parameter annotation = (Parameter)field.getAnnotation(Parameter.class);
         if (annotation != null) {
            String category = annotation.category();
            String property = annotation.property();
            String comment = field.getAnnotation(Comment.class) != null ? ((Comment)field.getAnnotation(Comment.class)).value() : "";

            try {
               Object defaultValue = field.get(parameters);
               if (defaultValue instanceof Boolean) {
                  field.set(parameters, config.getBoolean(property, category, Boolean.valueOf(annotation.defaultValue()), comment));
               } else if (defaultValue instanceof Integer) {
                  int minInt = Integer.MIN_VALUE;
                  int maxInt = Integer.MAX_VALUE;
                  MinMaxInt mmi = (MinMaxInt)field.getAnnotation(MinMaxInt.class);
                  if (mmi != null) {
                     minInt = mmi.min();
                     maxInt = mmi.max();
                  }

                  field.set(parameters, config.getInt(property, category, Integer.valueOf(annotation.defaultValue()), minInt, maxInt, comment));
               } else if (defaultValue instanceof Float) {
                  float minFloat = Float.MIN_VALUE;
                  float maxFloat = Float.MAX_VALUE;
                  MinMaxFloat mmf = (MinMaxFloat)field.getAnnotation(MinMaxFloat.class);
                  if (mmf != null) {
                     minFloat = mmf.min();
                     maxFloat = mmf.max();
                  }

                  field.set(parameters, config.getFloat(property, category, Float.valueOf(annotation.defaultValue()), minFloat, maxFloat, comment));
               } else if (defaultValue instanceof String) {
                  field.set(parameters, config.getString(property, category, annotation.defaultValue(), comment));
               } else if (defaultValue instanceof String[]) {
                  field.set(parameters, config.getStringList(property, category, StringUtils.split(annotation.defaultValue(), ','), comment));
               }

               Property prop = config.getCategory(category).get(property);
               if (field.getAnnotation(RestartRequired.class) != null) {
                  RestartRequired restart = (RestartRequired)field.getAnnotation(RestartRequired.class);
                  prop.setRequiresMcRestart(restart.server());
                  prop.setRequiresWorldRestart(restart.world());
               } else {
                  prop.setRequiresMcRestart(false);
                  prop.setRequiresWorldRestart(false);
               }

               prop.setShowInGui(field.getAnnotation(Hidden.class) == null);
            } catch (Throwable t) {
               ModLog.error("Unable to parse configuration", t);
            }
         }
      }

   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface Comment {
      String value() default "";
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface Hidden {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface MinMaxFloat {
      float min() default Float.MIN_VALUE;

      float max() default Float.MAX_VALUE;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface MinMaxInt {
      int min() default Integer.MIN_VALUE;

      int max() default Integer.MAX_VALUE;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface Parameter {
      String category();

      String property();

      String defaultValue();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface RestartRequired {
      boolean world() default true;

      boolean server() default true;
   }
}
