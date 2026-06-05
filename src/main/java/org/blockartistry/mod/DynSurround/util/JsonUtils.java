package org.blockartistry.mod.DynSurround.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonUtils {
   public static <T> T load(File file, Class<T> clazz) throws Exception {
      InputStream stream = null;

      Object var3;
      try {
         stream = new FileInputStream(file);
         if (stream == null) {
            return (T)clazz.newInstance();
         }

         var3 = load(stream, clazz);
      } finally {
         try {
            if (stream != null) {
               stream.close();
            }
         } catch (Throwable var11) {
         }

      }

      return (T)var3;
   }

   public static <T> T load(String modId, Class<T> clazz) throws Exception {
      String fileName = modId.replaceAll("[^a-zA-Z0-9.-]", "_");
      InputStream stream = null;

      Object var4;
      try {
         stream = clazz.getResourceAsStream("/assets/dsurround/data/" + fileName + ".json");
         if (stream == null) {
            return (T)clazz.newInstance();
         }

         var4 = load(stream, clazz);
      } finally {
         try {
            if (stream != null) {
               stream.close();
            }
         } catch (Throwable var12) {
         }

      }

      return (T)var4;
   }

   public static <T> T load(InputStream stream, Class<T> clazz) {
      InputStreamReader reader = null;
      JsonReader reader2 = null;

      Object var4;
      try {
         reader = new InputStreamReader(stream);
         reader2 = new JsonReader(reader);
         var4 = (new Gson()).fromJson(reader, clazz);
      } finally {
         try {
            if (reader2 != null) {
               reader2.close();
            }

            if (reader != null) {
               reader.close();
            }
         } catch (Exception var11) {
         }

      }

      return (T)var4;
   }
}
