package org.blockartistry.mod.DynSurround.client.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PotionHUD extends Gui implements GuiHUDHandler.IGuiOverlay {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");

   public void doRender(RenderGameOverlayEvent event) {
      if (!event.isCancelable() && event.type == ElementType.EXPERIENCE) {
         int TEXT_POTION_NAME = (int)(255.0F * ModOptions.potionHudTransparency) << 24 | 16777215;
         int TEXT_DURATION = (int)(255.0F * ModOptions.potionHudTransparency) << 24 | 8355711;
         int TEXT_DURATION_LOW = (int)(255.0F * ModOptions.potionHudTransparency) << 24 | 16711680;
         float GUITOP = (float)ModOptions.potionHudTopOffset;
         float GUILEFT = (float)ModOptions.potionHudLeftOffset;
         float SCALE = ModOptions.potionHudScale;
         Minecraft mc = Minecraft.getMinecraft();
         FontRenderer font = mc.fontRenderer;
         EntityPlayer player = Minecraft.getMinecraft().thePlayer;
         int guiLeft = 2;
         int guiTop = 2;
         Collection<PotionEffect> collection = player.getActivePotionEffects();
         if (!collection.isEmpty()) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, ModOptions.potionHudTransparency);
            GL11.glDisable(2896);
            GL11.glTranslatef(GUILEFT, GUITOP, 0.0F);
            GL11.glScalef(SCALE, SCALE, SCALE);
            int k = 33;
            if (collection.size() > 7) {
               k = 198 / (collection.size() - 1);
            }

            for(PotionEffect potioneffect : collection) {
               int potionId = potioneffect.getPotionID();
               if (potionId >= 0 && potionId < Potion.potionTypes.length) {
                  Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                  if (potion != null) {
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, ModOptions.potionHudTransparency);
                     mc.getTextureManager().bindTexture(TEXTURE);
                     this.drawTexturedModalRect(2, guiTop, 0, 166, 140, 32);
                     if (potion.hasStatusIcon()) {
                        int l = potion.getStatusIconIndex();
                        this.drawTexturedModalRect(8, guiTop + 7, 0 + l % 8 * 18, 198 + l / 8 * 18, 18, 18);
                     }

                     try {
                        potion.renderInventoryEffect(2, guiTop, potioneffect, mc);
                     } catch (Exception var21) {
                     }

                     if (potion.shouldRenderInvText(potioneffect)) {
                        String s1 = I18n.format(potion.getName(), new Object[0]);
                        if (potioneffect.getAmplifier() == 1) {
                           s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
                        } else if (potioneffect.getAmplifier() == 2) {
                           s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
                        } else if (potioneffect.getAmplifier() == 3) {
                           s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
                        }

                        font.drawStringWithShadow(s1, 30, guiTop + 6, TEXT_POTION_NAME);
                        String s = Potion.getDurationString(potioneffect);
                        font.drawStringWithShadow(s, 30, guiTop + 6 + 10, potioneffect.getDuration() <= 200 ? TEXT_DURATION_LOW : TEXT_DURATION);
                     }
                  }
               }

               guiTop += k;
            }

            GL11.glPopMatrix();
         }

      }
   }
}
