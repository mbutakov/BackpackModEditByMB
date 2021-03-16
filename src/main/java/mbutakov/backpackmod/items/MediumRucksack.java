package mbutakov.backpackmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MediumRucksack extends Item {

   public MediumRucksack() {
      this.setCreativeTab(CreativeTabs.tabCombat);
      this.setMaxStackSize(1);
      this.setUnlocalizedName("mediumRucksack");
      this.setTextureName("woa:null");
   }
}
