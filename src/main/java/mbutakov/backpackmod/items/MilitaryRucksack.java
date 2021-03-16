package mbutakov.backpackmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MilitaryRucksack extends Item {

   public MilitaryRucksack() {
      this.setCreativeTab(CreativeTabs.tabCombat);
      this.setMaxStackSize(1);
      this.setUnlocalizedName("militaryRucksack");
      this.setTextureName("woa:null");
   }
}
