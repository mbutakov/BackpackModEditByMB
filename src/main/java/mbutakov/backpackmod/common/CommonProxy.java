package mbutakov.backpackmod.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mbutakov.backpackmod.core.Core;
import mbutakov.backpackmod.event.EventsForge;
import mbutakov.backpackmod.inv.ContainerCustomPlayer;
import mbutakov.backpackmod.packet.MessageOpenGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void preInit() {
	}

	public void Init() {
		EventsForge ce = new EventsForge();
		MinecraftForge.EVENT_BUS.register(ce);
		FMLCommonHandler.instance().bus().register(ce);
		
	}

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

	public void load(FMLInitializationEvent event) {
	}

	public void preload(FMLPreInitializationEvent event) {
	}

	public boolean isSideClient() {
		return false;
	}
	
	private static Container getContainer(final int id, final EntityPlayer player) {
		switch (id) {
		case 0:return new ContainerCustomPlayer(player);
		default:return null;
		}
	}

	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Container container = getContainer(id, player);
			if (container == null) return;
			playerMP.closeContainer();
			playerMP.getNextWindowId();
			int windowId = playerMP.currentWindowId;

			MessageOpenGui message = new MessageOpenGui();
			message.windowID = windowId;
			message.id = id;
			Core.netHandler.sendTo(message, playerMP);
			player.openContainer = container;
			playerMP.openContainer.windowId = windowId;
			playerMP.openContainer.addCraftingToCrafters(playerMP);
		}
	}
	
	
}
