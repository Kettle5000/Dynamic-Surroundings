package org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum EventType {
   WALK((EventType)null),
   WANDER((EventType)null),
   SWIM((EventType)null),
   RUN(WALK),
   JUMP(WANDER),
   LAND(RUN),
   CLIMB(WALK),
   CLIMB_RUN(RUN),
   DOWN(WALK),
   DOWN_RUN(RUN),
   UP(WALK),
   UP_RUN(RUN);

   private final EventType destination;
   private final String jsonName;

   private EventType(EventType dest) {
      this.destination = dest;
      this.jsonName = this.name().toLowerCase();
   }

   public String jsonName() {
      return this.jsonName;
   }

   public boolean canTransition() {
      return this.destination != null;
   }

   public EventType getTransitionDestination() {
      return this.destination;
   }
}
