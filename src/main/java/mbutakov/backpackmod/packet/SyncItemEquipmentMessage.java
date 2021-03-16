package mbutakov.backpackmod.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import mbutakov.backpackmod.core.Core;
import mbutakov.backpackmod.ieep.PlayerEntityIEEP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class SyncItemEquipmentMessage implements IMessage {

	public int entityID;
	public byte slotID;
	public ItemStack item;

	public SyncItemEquipmentMessage() {}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeByte(slotID);
		if (item != null)
			ByteBufUtils.writeItemStack(buf, item);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		slotID = buf.readByte();
		if (buf.readableBytes() > 2)
			item = ByteBufUtils.readItemStack(buf);
	}

	public static class Handler implements IMessageHandler<SyncItemEquipmentMessage, IMessage> {

		@Override
		public IMessage onMessage(SyncItemEquipmentMessage packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(SyncItemEquipmentMessage packet) {
			Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(packet.entityID);
			if (entity instanceof EntityPlayer) {
				PlayerEntityIEEP specialPlayer = PlayerEntityIEEP.get((EntityPlayer)entity);
				if (specialPlayer == null) return;
				specialPlayer.getInventory().setInventorySlotContents(packet.slotID, packet.item);
			}
		}

		private void act(EntityPlayerMP player, SyncItemEquipmentMessage packet) {
			SyncItemEquipmentMessage message = new SyncItemEquipmentMessage();
			message.entityID = packet.entityID;
			message.slotID = packet.slotID;
			message.item = packet.item;
			Core.netHandler.sendToDimension(message, player.dimension);
		}

	}

}
