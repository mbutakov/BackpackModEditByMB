package mbutakov.backpackmod.inv;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import mbutakov.backpackmod.client.ClientProxy;
import mbutakov.backpackmod.core.Core;
import mbutakov.backpackmod.ieep.PlayerEntityIEEP;

public class GuiCustomPlayerInventory extends GuiContainer {

	   private float xSize_lo;
	   private float ySize_lo;
	   private EntityPlayer player;


	   public GuiCustomPlayerInventory(EntityPlayer player) {
	      super(new ContainerCustomPlayer(player));
	      this.player = player;
	   }

	   public void drawScreen(int mouseX, int mouseY, float f) {
	      super.drawScreen(mouseX, mouseY, f);
	      this.xSize_lo = (float)mouseX;
	      this.ySize_lo = (float)mouseY;
	   }

	   protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	      super.mc.getTextureManager().bindTexture(ClientProxy.inventoryTexture);
	      this.drawTexturedModalRect(super.guiLeft, super.guiTop, 0, 0, super.xSize, super.ySize);
	      GuiInventory.func_147046_a(super.guiLeft + 88, super.guiTop + 75, 30, (float)(super.guiLeft + 51) - this.xSize_lo, (float)(super.guiTop + 25) - this.ySize_lo, super.mc.thePlayer);
	      super.mc.getTextureManager().bindTexture(ClientProxy.slotsTexture);
	      byte x = 0;
			PlayerEntityIEEP ieep = PlayerEntityIEEP.get(player);
			if (ieep != null) {
				ItemStack slot = ieep.getInventory().getStackInSlot(0);
				if(slot != null) {
					if(slot.getItem() == Core.schoolRucksack) {
						x = 1;
					}
					if(slot.getItem() == Core.mediumRucksack) {
						x = 2;
					}
					if(slot.getItem() == Core.militaryRucksack) {
						x = 3;
					}
				}
			}
			this.drawTexturedModalRect(super.guiLeft + 7, super.guiTop + 83, 0, 0, 162, 18 * x);
	   }
	}
