package mbutakov.backpackmod.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mbutakov.backpackmod.core.Core;
import mbutakov.backpackmod.core.mbResourceLocation;
import mbutakov.backpackmod.ieep.PlayerEntityIEEP;
import mbutakov.backpackmod.packet.MessageOpenGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class PlayerRenderHandler {

   public static mbResourceLocation backpackRender = new mbResourceLocation();


	@SubscribeEvent
	public void openGuinventory(GuiOpenEvent event)  {
		if (event.gui instanceof GuiInventory) {
			if (!Minecraft.getMinecraft().playerController.isInCreativeMode()) {
				MessageOpenGui message = new MessageOpenGui();
				message.id = 0;
				Core.netHandler.sendToServer(message);
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderArmomForPlayer(RenderPlayerEvent.Specials.Post event) {
		PlayerEntityIEEP ieep = PlayerEntityIEEP.get(event.entityPlayer);
		if (ieep == null) return;

		for (int i = 0; i < ieep.getInventory().getSizeInventory(); ++i) {
			ItemStack slot = ieep.getInventory().getStackInSlot(i);
			if (slot == null) continue;
	         if(slot.getItem() == Core.schoolRucksack) {
	            GL11.glPushMatrix();
	            Minecraft.getMinecraft().renderEngine.bindTexture(mbResourceLocation.schoolRucksackTexture);
	            event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);
	            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
	            GL11.glTranslatef(0.0F, -0.75F, 0.0F);
	            mbResourceLocation.schoolRucksack.renderAll();
	            GL11.glPopMatrix();
	         }
	         if(slot.getItem() == Core.mediumRucksack) {
		            GL11.glPushMatrix();
		            Minecraft.getMinecraft().renderEngine.bindTexture(mbResourceLocation.mediumRucksackTexture);
		            event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);
		            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		            GL11.glTranslatef(0.0F, -0.75F, 0.0F);
		            mbResourceLocation.mediumRucksack.renderAll();
		            GL11.glPopMatrix();
		         }
	         if(slot.getItem() == Core.militaryRucksack) {
		            GL11.glPushMatrix();
		            Minecraft.getMinecraft().renderEngine.bindTexture(mbResourceLocation.militaryRucksackTexture);
		            event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);
		            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		            GL11.glTranslatef(0.0F, -0.75F, 0.0F);
		            mbResourceLocation.militaryRucksack.renderAll();
		            GL11.glPopMatrix();
		         }
		}
	}

	   @SideOnly(Side.CLIENT)
	   @SubscribeEvent
	   public void event(RenderGameOverlayEvent e)
	   {
		    String s = MathHelper.ceiling_double_int(((EntityPlayer)Minecraft.getMinecraft().thePlayer).posX) + ", " + MathHelper.ceiling_double_int(((EntityPlayer)Minecraft.getMinecraft().thePlayer).posZ);
			if (e.type == ElementType.ALL) {
				Minecraft.getMinecraft().fontRenderer.drawString(s, e.resolution.getScaledWidth() - Minecraft.getMinecraft().fontRenderer.getStringWidth(s), 4, 0xFFFFFF);
			}
	   }

}
