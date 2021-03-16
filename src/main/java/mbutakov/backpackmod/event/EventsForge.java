package mbutakov.backpackmod.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import mbutakov.backpackmod.core.Core;
import mbutakov.backpackmod.ieep.PlayerEntityIEEP;
import mbutakov.backpackmod.packet.SyncItemEquipmentMessage;
import mbutakov.backpackmod.packet.SyncPlayerEntityMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EventsForge {

	
	@SubscribeEvent
	public void addEntityConstructing(EntityEvent.EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			if (PlayerEntityIEEP.get(player) == null) PlayerEntityIEEP.reg(player);
		}
	}

	@SubscribeEvent
	public void cloneRpgEntityIEEP(PlayerEvent.Clone event) {
		if (event.original.isDead) return;
		PlayerEntityIEEP originalRpgEntityIEEP = PlayerEntityIEEP.get(event.original);
		if (originalRpgEntityIEEP == null) return;
		PlayerEntityIEEP newRpgEntityIEEP = PlayerEntityIEEP.get(event.entityPlayer);
		if (newRpgEntityIEEP == null) return;
		NBTTagCompound nbt = new NBTTagCompound();
		newRpgEntityIEEP.saveNBTData(nbt);
		originalRpgEntityIEEP.loadNBTData(nbt);
	}

	@SubscribeEvent
	public void syncRpgEntityIEEP(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)event.entity;
			PlayerEntityIEEP specialPlayer = PlayerEntityIEEP.get(player);
			NBTTagCompound nbt = new NBTTagCompound();
			if (specialPlayer != null) {
				specialPlayer.saveNBTData(nbt);
				SyncPlayerEntityMessage message = new SyncPlayerEntityMessage();
				message.nbt = nbt;
				message.entityID = player.getEntityId();
				Core.netHandler.sendToAllAround(message, new NetworkRegistry.TargetPoint(
						player.dimension, player.posX, player.posY, player.posZ, 256));
			}
		}
	}

	@SubscribeEvent
	public void playerStartTracking(PlayerEvent.StartTracking event) {
		if (event.entityPlayer instanceof EntityPlayerMP && event.target instanceof EntityPlayerMP) {
			PlayerEntityIEEP specialPlayer = PlayerEntityIEEP.get((EntityPlayerMP)event.target);
			if (specialPlayer != null) {
				NBTTagCompound nbt = new NBTTagCompound();
				specialPlayer.saveNBTData(nbt);
				SyncPlayerEntityMessage message = new SyncPlayerEntityMessage();
				message.nbt = nbt;
				message.entityID = event.target.getEntityId();
				Core.netHandler.sendTo(message, (EntityPlayerMP)event.entityPlayer);
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void playerDropsEvent(PlayerDropsEvent event) {
		event.entityPlayer.captureDrops = true;
		PlayerEntityIEEP ieep = PlayerEntityIEEP.get(event.entityPlayer);
		if (ieep != null) {
			for (int i = 0; i < ieep.getInventory().getSizeInventory(); ++i) {
				event.entityPlayer.func_146097_a(ieep.getInventory().getStackInSlot(i), true, false);
				ieep.getInventory().setInventorySlotContents(i, null);
			}
		}
		event.entityPlayer.captureDrops = false;
	}

	@SubscribeEvent
	public void updatePlayer(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			int i = 0;
			ItemStack slot;

			PlayerEntityIEEP ieep = PlayerEntityIEEP.get(player);
			if (ieep != null) {
				for (i = 0; i < ieep.getInventory().getSizeInventory(); ++i) {
					slot = ieep.getInventory().getStackInSlot(i);
					if (ieep.oldItemEquipment[i] != slot) {

						checkItemEquipment(player, ieep.oldItemEquipment[i], slot, i);
						ieep.oldItemEquipment[i] = slot;
					}
				}
			}

			for (i = 0; i < player.inventory.armorInventory.length; ++i) {
				slot = player.inventory.armorInventory[i];
				if (ieep.oldItemArmor[i] != slot) {
					checkItemArmor(player, ieep.oldItemArmor[i], slot, i);
					ieep.oldItemArmor[i] = slot;
				}
			}

		}
	}

	private static void checkItemEquipment(EntityPlayer player, ItemStack old, ItemStack current, int slotID) {
		if (player.worldObj.isRemote) return;

		SyncItemEquipmentMessage message = new SyncItemEquipmentMessage();
		message.entityID = player.getEntityId();
		message.slotID = (byte)slotID;
		message.item = current;
		Core.netHandler.sendToAllAround(message, new NetworkRegistry.TargetPoint(
				player.dimension, player.posX, player.posY, player.posZ, 256));
	}

	private static void checkItemArmor(EntityPlayer player, ItemStack old, ItemStack current, int slotID) {
		if (player.worldObj.isRemote) return;

	}
	
	@SubscribeEvent
	public void onPlayerUpdateEvent2(LivingUpdateEvent event) {
		if (!event.entity.worldObj.isRemote && event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			PlayerEntityIEEP ieep = PlayerEntityIEEP.get(player);
		if (ieep != null) {
			Item equip;
			ItemStack slotbackpack =  ieep.getInventory().getStackInSlot(0);
			if (slotbackpack != null) {
				equip = slotbackpack.getItem();
				int i;
				if (equip == Core.schoolRucksack) {
					for (i = 18; i < 36; ++i) {
						player.dropPlayerItemWithRandomChoice(player.inventory.getStackInSlot(i), true);
						player.inventory.setInventorySlotContents(i, (ItemStack) null);
					}
				} else if (equip == Core.mediumRucksack) {
					for (i = 27; i < 36; ++i) {
						player.dropPlayerItemWithRandomChoice(player.inventory.getStackInSlot(i), true);
						player.inventory.setInventorySlotContents(i, (ItemStack) null);
					}
				} else if (equip == Core.militaryRucksack) {
					for (i = 37; i < 37; ++i) {
						player.dropPlayerItemWithRandomChoice(player.inventory.getStackInSlot(i), true);
						player.inventory.setInventorySlotContents(i, (ItemStack) null);
					}
				}
			} else {
				for (int var6 = 9; var6 < 36; ++var6) {
					player.dropPlayerItemWithRandomChoice(player.inventory.getStackInSlot(var6), true);
					player.inventory.setInventorySlotContents(var6, (ItemStack) null);
				}
		}
	}
		}
}
	
	
	   @SubscribeEvent
	   public void onPlayerUpdateEvent(LivingUpdateEvent event) {
	      if(event.entityLiving instanceof EntityPlayer) {
	         EntityPlayer player = (EntityPlayer)event.entityLiving;
	         ItemStack slotbackpack;
	         Item equip;
				PlayerEntityIEEP ieep = PlayerEntityIEEP.get(player);
				if (ieep != null) {
	        	 slotbackpack = ieep.getInventory().getStackInSlot(0);
	        	 if(slotbackpack == null ) {
	        		 if(player.inventory.hasItem(Core.militaryRucksack) || player.inventory.hasItem(Core.mediumRucksack) || player.inventory.hasItem(Core.schoolRucksack)) {
	        			 for(int slot = 0; slot < 36; ++slot) {
	        				 if(player.inventory.getStackInSlot(slot) != null) {
	        					 if(player.inventory.getStackInSlot(slot).getItem() == Core.militaryRucksack) {
	        						 player.inventory.setInventorySlotContents(slot, null);
	        						 ieep.getInventory().setInventorySlotContents(0,new ItemStack(Core.militaryRucksack));
	        						 return;
	        					 }
	        				 }
	        			 }
	        			 for(int slot = 0; slot < 36; ++slot) {
	        				 if(player.inventory.getStackInSlot(slot) != null) {
	        					 if(player.inventory.getStackInSlot(slot).getItem() == Core.mediumRucksack) {
	        						 player.inventory.setInventorySlotContents(slot, null);
	        						 ieep.getInventory().setInventorySlotContents(0,new ItemStack(Core.mediumRucksack));
	        						 return;
	        					 }
	        				 }
	        			 }
	        			 for(int slot = 0; slot < 36; ++slot) {
	        				 if(player.inventory.getStackInSlot(slot) != null) {
	        					 if(player.inventory.getStackInSlot(slot).getItem() == Core.schoolRucksack) {
	        						 player.inventory.setInventorySlotContents(slot, null);
	        						 ieep.getInventory().setInventorySlotContents(0,new ItemStack(Core.schoolRucksack));
	        						 return;
	        					 }
	        				 }
	        			 }
	        		 }
	        	 }  		      	 
				}
	      }
	}

}
