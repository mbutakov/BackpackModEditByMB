package mbutakov.backpackmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SchoolRucksack extends Item {

   public SchoolRucksack() {
      this.setCreativeTab(CreativeTabs.tabCombat);
      this.setMaxStackSize(1);
      this.setUnlocalizedName("schoolRucksack");
      this.setTextureName("woa:null");
   }
}
