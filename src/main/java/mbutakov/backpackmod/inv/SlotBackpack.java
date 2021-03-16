package mbutakov.backpackmod.inv;

import mbutakov.backpackmod.core.Core;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBackpack extends Slot {

	public final EntityPlayer player;

	public SlotBackpack(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition, EntityPlayer player) {
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
		this.player = player;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() == Core.schoolRucksack ? true
				: (stack.getItem() == Core.mediumRucksack ? true : stack.getItem() == Core.militaryRucksack);
	}

}
