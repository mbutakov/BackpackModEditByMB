package mbutakov.backpackmod.inv;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mbutakov.backpackmod.core.Core;
import mbutakov.backpackmod.ieep.PlayerEntityIEEP;



public class ContainerCustomPlayer extends Container {
	  /** The crafting matrix inventory. */
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
    public IInventory craftResult = new InventoryCraftResult();
    /** Determines if inventory manipulation should be handled. */
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
    private static final String __OBFID = "CL_00001754";

    public ContainerCustomPlayer(EntityPlayer player)
    {
    	
        this.thePlayer = player;
        int i;
        int j;

        
        for (i = 0; i < 4; ++i)
        {
            final int k = i;
            this.addSlotToContainer(new Slot(player.inventory, player.inventory.getSizeInventory() - 1 - i, 44, 8 + i * 18)
            {
                private static final String __OBFID = "CL_00001755";
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
                 * in the case of armor slots)
                 */
                public int getSlotStackLimit()
                {
                    return 1;
                }
                /**
                 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
                 */
                public boolean isItemValid(ItemStack p_75214_1_)
                {
                    if (p_75214_1_ == null) return false;
                    return p_75214_1_.getItem().isValidArmor(p_75214_1_, k, thePlayer);
                }
                /**
                 * Returns the icon index on items.png that is used as background image of the slot.
                 */
                @SideOnly(Side.CLIENT)
                public IIcon getBackgroundIconIndex()
                {
                    return ItemArmor.func_94602_b(k);
                }
            });
        }

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
        PlayerEntityIEEP ieep = PlayerEntityIEEP.get(player);
		if (ieep != null) {
			for (i = 0; i < ieep.getInventory().getSizeInventory(); ++i) {
				addSlotToContainer(new SlotBackpack(ieep.getInventory(), i, 116, 26, player));
			}
		}

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }



    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return true;
    }

    
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (index < 4 && index != 40)
			{
				// try to place in player inventory / action bar
				if (!this.mergeItemStack(itemstack1, 4, 39, false))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}else {
				if (itemstack1.getItem() instanceof ItemArmor)
				{
					int type = ((ItemArmor) itemstack1.getItem()).armorType;
					if(player.getEquipmentInSlot(0) == null || player.getEquipmentInSlot(1) == null || player.getEquipmentInSlot(2) == null || player.getEquipmentInSlot(3) == null ){
						if (!this.mergeItemStack(itemstack1, 0 + type, 0 + type + 1, true) && slot.getStack() == null)
						{
							return null;
						}
					}
				}
			}
			
			
			
			if (itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize) return null;	
		}
		
		return itemstack;
	}

    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        Slot slot = (Slot)super.inventorySlots.get(40);
        if(slot != null && slot.getHasStack()) {
           Item var6 = slot.getStack().getItem();
           ItemStack itemstack1;
           int var7;
           
           if(var6 == Core.schoolRucksack) {
              for(var7 = 18; var7 < 36; ++var7) {
                 itemstack1 = player.inventory.getStackInSlotOnClosing(var7);
                 if(itemstack1 != null) {
                    player.dropPlayerItemWithRandomChoice(itemstack1, false);
                 }
              }
           } else if(var6 == Core.mediumRucksack) {
              for(var7 = 27; var7 < 36; ++var7) {
                 itemstack1 = player.inventory.getStackInSlotOnClosing(var7);
                 if(itemstack1 != null) {
                    player.dropPlayerItemWithRandomChoice(itemstack1, false);
                 }
              }
           
        } else if(var6 == Core.militaryRucksack) {
            for(var7 = 36; var7 < 36; ++var7) {
               itemstack1 = player.inventory.getStackInSlotOnClosing(var7);
               if(itemstack1 != null) {
                  player.dropPlayerItemWithRandomChoice(itemstack1, false);
               }
            }
        }else {
            for(var7 = 9; var7 < 36; ++var7) {
                itemstack1 = player.inventory.getStackInSlotOnClosing(var7);
                if(itemstack1 != null) {
                   player.dropPlayerItemWithRandomChoice(itemstack1, false);
                }
             }
        }
        }
     }

     public ItemStack slotClick(int num, int var1, int typeClick, EntityPlayer player) {
    	   Slot slot = (Slot)super.inventorySlots.get(40);
    	      if(slot != null && slot.getHasStack()) {
    	         Item var6 = slot.getStack().getItem();
    	         ItemStack itemstack1;
    	         int var7;
    	         if(var6 == Core.schoolRucksack) {
    	            for(var7 = 18; var7 < 34; ++var7) {
    	               itemstack1 = player.inventory.getStackInSlotOnClosing(var7);
    	               if(itemstack1 != null) {
    	            	   return null;
    	               }
    	            }
    	         } else if(var6 == Core.mediumRucksack) {
    	            for(var7 = 27; var7 < 34; ++var7) {
    	               itemstack1 = player.inventory.getStackInSlotOnClosing(var7);
    	               if(itemstack1 != null) {
    	            	   return null;
    	               }
    	            }
    	         } else if(var6 == Core.militaryRucksack) {
    	        	   for(var7 = 34; var7 < 34; ++var7) {
    		               itemstack1 = player.inventory.getStackInSlotOnClosing(var7);
    		               if(itemstack1 != null) {
    		            	   return null;
    		               }
    		            }
    	         }
    	      } else {
    	         for(int i = 9; i < 34; ++i) {
    	            ItemStack itemstack = player.inventory.getStackInSlotOnClosing(i);
    	            if(itemstack != null) {
    	               return null;
    	            }
    	         }
    	      }
          return super.slotClick(num, var1, typeClick, player);
       }
  

    public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_)
    {
        return p_94530_2_.inventory != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
    }
	
	}