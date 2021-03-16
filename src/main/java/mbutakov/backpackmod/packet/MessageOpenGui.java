package mbutakov.backpackmod.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import mbutakov.backpackmod.core.Core;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessageOpenGui implements IMessage {

	public int windowID/*, id*/;
	public byte id;

	public MessageOpenGui() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(windowID);
		//buf.writeInt(id);
		buf.writeByte(id);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		windowID = buf.readInt();
		//id = buf.readInt();
		id = buf.readByte();
	}

	public static class Handler implements IMessageHandler<MessageOpenGui, IMessage> {

		@Override
		public IMessage onMessage(MessageOpenGui packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(MessageOpenGui packet) {
			Minecraft mc = Minecraft.getMinecraft();
			Core.proxy.openGui(packet.id, mc.thePlayer);
			mc.thePlayer.openContainer.windowId = packet.windowID;
		}

		private void act(EntityPlayerMP player, MessageOpenGui packet) {
			Core.proxy.openGui(packet.id, player);
		}

	}

}
