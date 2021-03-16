package mbutakov.backpackmod.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import mbutakov.backpackmod.common.CommonProxy;
import mbutakov.backpackmod.core.Core;
import mbutakov.backpackmod.inv.GuiCustomPlayerInventory;
import mbutakov.backpackmod.items.RenderItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	public static final ResourceLocation inventoryTexture = new ResourceLocation("hcs2k13:textures/gui/inventory.png");
	public static final ResourceLocation slotsTexture = new ResourceLocation("hcs2k13:textures/gui/slots.png");

	
	public void PreInit() {
		super.preInit();
		Minecraft mc = Minecraft.getMinecraft();
	}

	public void Init() {
		super.Init();
		Minecraft mc = FMLClientHandler.instance().getClient();
		final PlayerRenderHandler cevents = new PlayerRenderHandler();
		MinecraftForge.EVENT_BUS.register(cevents);
		FMLCommonHandler.instance().bus().register(cevents);
		MinecraftForgeClient.registerItemRenderer(Core.schoolRucksack, new RenderItem.schoolRucksack());
		MinecraftForgeClient.registerItemRenderer(Core.mediumRucksack, new RenderItem.mediumRucksack());
		MinecraftForgeClient.registerItemRenderer(Core.militaryRucksack, new RenderItem.militaryRucksack());
	}
	
	private static GuiScreen getGui(final int id, final EntityPlayer player) {
		switch (id) {
		case 0:return new GuiCustomPlayerInventory(player);
		default:return null;
		}
	}

	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player);
		else if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			GuiScreen gui = getGui(id, player);
			if (gui == null) return;
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
	}
	
}
