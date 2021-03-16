package mbutakov.backpackmod.ieep;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class PlayerEntityIEEP implements IExtendedEntityProperties {

	private static final String ID = "RpgEntityIEEP";
	private EntityPlayer player;
	public static EnumEquipmentPart[] eep = new EnumEquipmentPart[]{
			EnumEquipmentPart.BACKPACK,
	};
	private InventoryBasic inventory = new InventoryBasic(ID, false, eep.length);
	public ItemStack[] oldItemEquipment = new ItemStack[eep.length];
	public ItemStack[] oldItemArmor = new ItemStack[4];

	public static String reg(final EntityPlayer player) {
		return player.registerExtendedProperties(PlayerEntityIEEP.ID, new PlayerEntityIEEP());
	}

	public static PlayerEntityIEEP get(final EntityPlayer player) {
		return (PlayerEntityIEEP)player.getExtendedProperties(PlayerEntityIEEP.ID);
	}

	public InventoryBasic getInventory() {
		return inventory;
	}

	public void clearInventory() {
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			inventory.setInventorySlotContents(i, null);
		}
	}

	public void clearInventory(Item item, int metadata) {
		if (item == null && metadata <= -1) clearInventory();
		else {
			for (int i = 0; i < inventory.getSizeInventory(); ++i) {
				ItemStack slot = inventory.getStackInSlot(i);
				if ((item == null || (slot != null && slot.getItem() == item)) && (metadata <= -1 || slot.getItemDamage() == metadata))
					inventory.setInventorySlotContents(i, null);
			}
		}
	}

	@Override
	public void init(Entity entity, World world) {
		if (entity instanceof EntityPlayer) player = (EntityPlayer)entity;
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		if (compound.hasKey(ID, NBT.TAG_COMPOUND)) {
			NBTTagCompound nbt = compound.getCompoundTag(ID);
			NBTTagList nbttaglist = nbt.getTagList("Items", 10);
			inventory = new InventoryBasic(ID, false, inventory.getSizeInventory());
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound1.getByte("Slot") & 255;

				if (j >= 0 && j < inventory.getSizeInventory()) {
					inventory.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}

		}
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				inventory.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		compound.setTag(ID, nbt);
	}

}
