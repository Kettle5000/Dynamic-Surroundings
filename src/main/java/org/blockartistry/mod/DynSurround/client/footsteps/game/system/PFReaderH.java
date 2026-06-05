package org.blockartistry.mod.DynSurround.client.footsteps.game.system;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.EventType;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem.NormalVariator;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IGenerator;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IIsolator;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.ISolver;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IVariator;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IVariatorSettable;
import org.blockartistry.mod.DynSurround.util.MyUtils;

@SideOnly(Side.CLIENT)
public class PFReaderH implements IGenerator, IVariatorSettable {
   protected final IIsolator mod;
   protected NormalVariator VAR;
   protected float dmwBase;
   protected float dwmYChange;
   protected double yPosition;
   protected boolean isFlying;
   protected float fallDistance;
   protected float lastReference;
   protected boolean isImmobile;
   protected long timeImmobile;
   protected boolean isRightFoot;
   protected double xMovec;
   protected double zMovec;
   protected boolean scalStat;
   private boolean stepThisFrame;
   private boolean isMessyFoliage;
   private long brushesTime;

   public PFReaderH(IIsolator isolator) {
      this.mod = isolator;
      this.VAR = new NormalVariator();
   }

   public void setVariator(IVariator variator) {
      if (variator instanceof NormalVariator) {
         this.VAR = (NormalVariator)variator;
      }

   }

   public void generateFootsteps(EntityPlayer ply) {
      this.simulateFootsteps(ply);
      this.simulateAirborne(ply);
      this.simulateBrushes(ply);
   }

   protected boolean stoppedImmobile(float reference) {
      float diff = this.lastReference - reference;
      this.lastReference = reference;
      if (!this.isImmobile && diff == 0.0F) {
         this.timeImmobile = MyUtils.currentTimeMillis();
         this.isImmobile = true;
      } else if (this.isImmobile && diff != 0.0F) {
         this.isImmobile = false;
         return MyUtils.currentTimeMillis() - this.timeImmobile > (long)this.VAR.IMMOBILE_DURATION;
      }

      return false;
   }

   protected void simulateFootsteps(EntityPlayer ply) {
      float distanceReference = ply.distanceWalkedOnStepModified;
      this.stepThisFrame = false;
      if (this.dmwBase > distanceReference) {
         this.dmwBase = 0.0F;
         this.dwmYChange = 0.0F;
      }

      double movX = ply.motionX;
      double movZ = ply.motionZ;
      double scal = movX * this.xMovec + movZ * this.zMovec;
      if (this.scalStat != scal < (double)0.001F) {
         this.scalStat = !this.scalStat;
         if (this.scalStat && this.VAR.PLAY_WANDER && !this.mod.getSolver().hasSpecialStoppingConditions(ply)) {
            this.mod.getSolver().playAssociation(ply, this.mod.getSolver().findAssociationForPlayer(ply, (double)0.0F, this.isRightFoot), EventType.WANDER);
         }
      }

      this.xMovec = movX;
      this.zMovec = movZ;
      if (ply.onGround || ply.isInWater() || ply.isOnLadder()) {
         EventType event = null;
         float dwm = distanceReference - this.dmwBase;
         boolean immobile = this.stoppedImmobile(distanceReference);
         if (immobile && !ply.isOnLadder()) {
            dwm = 0.0F;
            this.dmwBase = distanceReference;
         }

         float distance = 0.0F;
         double verticalOffsetAsMinus = (double)0.0F;
         if (ply.isOnLadder() && !ply.onGround) {
            distance = this.VAR.DISTANCE_LADDER;
         } else if (!ply.isInWater() && Math.abs(this.yPosition - ply.posY) > 0.4) {
            if (this.yPosition < ply.posY) {
               distance = this.VAR.DISTANCE_STAIR;
               event = this.speedDisambiguator(ply, EventType.UP, EventType.UP_RUN);
            } else if (!ply.isSneaking()) {
               distance = -1.0F;
               verticalOffsetAsMinus = (double)0.0F;
               event = this.speedDisambiguator(ply, EventType.DOWN, EventType.DOWN_RUN);
            }

            this.dwmYChange = distanceReference;
         } else {
            distance = this.VAR.DISTANCE_HUMAN;
         }

         if (event == null) {
            event = this.speedDisambiguator(ply, EventType.WALK, EventType.RUN);
         }

         distance = this.reevaluateDistance(event, distance);
         if (dwm > distance) {
            this.produceStep(ply, event, verticalOffsetAsMinus);
            this.stepped(ply, event);
            this.dmwBase = distanceReference;
         }
      }

      if (ply.onGround) {
         this.yPosition = ply.posY;
      }

   }

   protected void produceStep(EntityPlayer ply, EventType event) {
      this.produceStep(ply, event, (double)0.0F);
   }

   protected void produceStep(EntityPlayer ply, EventType event, double verticalOffsetAsMinus) {
      if (!this.mod.getSolver().playSpecialStoppingConditions(ply)) {
         if (event == null) {
            event = this.speedDisambiguator(ply, EventType.WALK, EventType.RUN);
         }

         this.mod.getSolver().playAssociation(ply, this.mod.getSolver().findAssociationForPlayer(ply, verticalOffsetAsMinus, this.isRightFoot), event);
         this.isRightFoot = !this.isRightFoot;
      }

      this.stepThisFrame = true;
   }

   protected void stepped(EntityPlayer ply, EventType event) {
   }

   protected float reevaluateDistance(EventType event, float distance) {
      return distance;
   }

   protected void simulateAirborne(EntityPlayer ply) {
      if ((ply.onGround || ply.isOnLadder()) == this.isFlying) {
         this.isFlying = !this.isFlying;
         this.simulateJumpingLanding(ply);
      }

      if (this.isFlying) {
         this.fallDistance = ply.fallDistance;
      }

   }

   protected void simulateJumpingLanding(EntityPlayer ply) {
      if (!this.mod.getSolver().hasSpecialStoppingConditions(ply)) {
         boolean isJumping = ply.isJumping;
         if (this.isFlying && isJumping) {
            if (this.VAR.EVENT_ON_JUMP) {
               double speed = ply.motionX * ply.motionX + ply.motionZ * ply.motionZ;
               if (speed < (double)this.VAR.SPEED_TO_JUMP_AS_MULTIFOOT) {
                  this.playMultifoot(ply, 0.4, EventType.JUMP);
               } else {
                  this.playSinglefoot(ply, 0.4, EventType.JUMP, this.isRightFoot);
               }
            }
         } else if (!this.isFlying) {
            if (this.fallDistance > this.VAR.LAND_HARD_DISTANCE_MIN) {
               this.playMultifoot(ply, (double)0.0F, EventType.LAND);
            } else if (!this.stepThisFrame && !ply.isSneaking()) {
               this.playSinglefoot(ply, (double)0.0F, this.speedDisambiguator(ply, EventType.CLIMB, EventType.CLIMB_RUN), this.isRightFoot);
               this.isRightFoot = !this.isRightFoot;
            }
         }

      }
   }

   protected EventType speedDisambiguator(EntityPlayer ply, EventType walk, EventType run) {
      double velocity = ply.motionX * ply.motionX + ply.motionZ * ply.motionZ;
      return velocity > (double)this.VAR.SPEED_TO_RUN ? run : walk;
   }

   private void simulateBrushes(EntityPlayer ply) {
      if (this.brushesTime <= MyUtils.currentTimeMillis()) {
         this.brushesTime = MyUtils.currentTimeMillis() + 100L;
         if ((ply.motionX != (double)0.0F || ply.motionZ != (double)0.0F) && !ply.isSneaking()) {
            int yy = MathHelper.floor_double(ply.posY - 0.1 - ply.getYOffset() - (ply.onGround ? (double)0.0F : (double)0.25F));
            Association assos = this.mod.getSolver().findAssociationForBlock(MathHelper.floor_double(ply.posX), yy, MathHelper.floor_double(ply.posZ), "find_messy_foliage");
            if (assos != null) {
               if (!this.isMessyFoliage) {
                  this.isMessyFoliage = true;
                  this.mod.getSolver().playAssociation(ply, assos, EventType.WALK);
               }
            } else {
               this.isMessyFoliage = false;
            }

         }
      }
   }

   protected void playSinglefoot(EntityPlayer ply, double verticalOffsetAsMinus, EventType eventType, boolean foot) {
      Association assos = this.mod.getSolver().findAssociationForPlayer(ply, verticalOffsetAsMinus, this.isRightFoot);
      this.mod.getSolver().playAssociation(ply, assos, eventType);
   }

   protected void playMultifoot(EntityPlayer ply, double verticalOffsetAsMinus, EventType eventType) {
      ISolver s = this.mod.getSolver();
      Association leftFoot = s.findAssociationForPlayer(ply, verticalOffsetAsMinus, false);
      Association rightFoot = s.findAssociationForPlayer(ply, verticalOffsetAsMinus, true);
      s.playAssociation(ply, leftFoot, eventType);
      s.playAssociation(ply, rightFoot, eventType);
   }

   protected float scalex(float number, float min, float max) {
      return MathHelper.clamp_float((number - min) / (max - min), 0.0F, 1.0F);
   }
}
