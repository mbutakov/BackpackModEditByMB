package mbutakov.backpackmod.items;

import org.lwjgl.opengl.GL11;

import mbutakov.backpackmod.core.mbResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderItem {


  
   public static class schoolRucksack implements IItemRenderer {

      public boolean handleRenderType(ItemStack item, ItemRenderType type) {
         return true;
      }

      public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
         return true;
      }

      public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
         GL11.glPushMatrix();
         Minecraft.getMinecraft().renderEngine.bindTexture(mbResourceLocation.schoolRucksackTexture);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(1.5F, 1.5F, 1.5F);
         GL11.glTranslatef(0.0F, -0.4F, 0.0F);
         mbResourceLocation.schoolRucksack.renderAll();
         GL11.glPopMatrix();
      }
   }

  
   public static class mediumRucksack implements IItemRenderer {

      public boolean handleRenderType(ItemStack item, ItemRenderType type) {
         return true;
      }

      public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
         return true;
      }

      public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
         GL11.glPushMatrix();
         Minecraft.getMinecraft().renderEngine.bindTexture(mbResourceLocation.mediumRucksackTexture);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(1.5F, 1.5F, 1.5F);
         GL11.glTranslatef(0.0F, -0.4F, 0.0F);
         mbResourceLocation.mediumRucksack.renderAll();
         GL11.glPopMatrix();
      }
   }

   
   public static class militaryRucksack implements IItemRenderer {

      public boolean handleRenderType(ItemStack item, ItemRenderType type) {
         return true;
      }

      public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
         return true;
      }

      public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
         GL11.glPushMatrix();
         Minecraft.getMinecraft().renderEngine.bindTexture(mbResourceLocation.militaryRucksackTexture);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(1.5F, 1.5F, 1.5F);
         GL11.glTranslatef(0.0F, -0.4F, 0.1F);
         mbResourceLocation.militaryRucksack.renderAll();
         GL11.glPopMatrix();
      }
   }
}
