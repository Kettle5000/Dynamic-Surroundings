package org.blockartistry.mod.DynSurround;

import org.apache.logging.log4j.Logger;

public final class ModLog {
   public static boolean DEBUGGING = false;
   private static Logger logger;

   private ModLog() {
   }

   public static Logger getLogger() {
      return logger;
   }

   public static void setLogger(Logger log) {
      logger = log;
   }

   public static void info(String msg, Object... parms) {
      if (logger != null) {
         logger.info(String.format(msg, parms));
      }

   }

   public static void warn(String msg, Object... parms) {
      if (logger != null) {
         logger.warn(String.format(msg, parms));
      }

   }

   public static void debug(String msg, Object... parms) {
      if (logger != null && DEBUGGING) {
         logger.info(String.format(msg, parms));
      }

   }

   public static void error(String msg, Throwable e) {
      if (logger != null) {
         logger.error(msg);
      }

      e.printStackTrace();
   }

   public static void catching(Throwable t) {
      if (logger != null) {
         logger.catching(t);
         t.printStackTrace();
      }

   }
}
