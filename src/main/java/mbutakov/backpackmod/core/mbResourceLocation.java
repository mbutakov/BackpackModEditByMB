package mbutakov.backpackmod.core;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class mbResourceLocation {

	   public static final IModelCustom schoolRucksack = AdvancedModelLoader.loadModel(new ResourceLocation("daymru".toLowerCase(), "models/items/schoolRucksack.obj"));
	   public static final ResourceLocation schoolRucksackTexture = new ResourceLocation("daymru".toLowerCase(), "textures/models/items/schoolRucksack.png");
	   public static final IModelCustom mediumRucksack = AdvancedModelLoader.loadModel(new ResourceLocation("daymru".toLowerCase(), "models/items/mediumRucksack.obj"));
	   public static final ResourceLocation mediumRucksackTexture = new ResourceLocation("daymru".toLowerCase(), "textures/models/items/mediumRucksack.png");
	   public static final IModelCustom militaryRucksack = AdvancedModelLoader.loadModel(new ResourceLocation("daymru".toLowerCase(), "models/items/militaryRucksack.obj"));
	   public static final ResourceLocation militaryRucksackTexture = new ResourceLocation("daymru".toLowerCase(), "textures/models/items/militaryRucksack.png");


	   
}
