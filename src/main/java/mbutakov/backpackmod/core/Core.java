package mbutakov.backpackmod.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import mbutakov.backpackmod.common.CommonProxy;
import mbutakov.backpackmod.items.MediumRucksack;
import mbutakov.backpackmod.items.MilitaryRucksack;
import mbutakov.backpackmod.items.SchoolRucksack;
import mbutakov.backpackmod.packet.MessageOpenGui;
import mbutakov.backpackmod.packet.SyncItemEquipmentMessage;
import mbutakov.backpackmod.packet.SyncPlayerEntityMessage;
import net.minecraft.item.Item;

@Mod(modid = "backpackmodmb", name = "backpackmodmb", version = "1.0")
public class Core {

	public static Item schoolRucksack;
	public static Item mediumRucksack;
	public static Item militaryRucksack;
	public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel("backpackmodmb");
	public static String owner = "Owner";
	@Instance("backpackmodmb")
	public static Core instance;
	@SidedProxy(clientSide = "mbutakov.backpackmod.client.ClientProxy", serverSide = "mbutakov.backpackmod.common.CommonProxy")
	public static CommonProxy proxy;
	@EventHandler
	public void PreInit(FMLPreInitializationEvent e) {
		proxy.preInit();
		netHandler.registerMessage(MessageOpenGui.Handler.class, MessageOpenGui.class, 0, Side.CLIENT);
		netHandler.registerMessage(MessageOpenGui.Handler.class, MessageOpenGui.class, 0, Side.SERVER);
		netHandler.registerMessage(SyncPlayerEntityMessage.Handler.class, SyncPlayerEntityMessage.class, 1, Side.CLIENT);
		netHandler.registerMessage(SyncItemEquipmentMessage.Handler.class, SyncItemEquipmentMessage.class, 2, Side.CLIENT);
		schoolRucksack = new SchoolRucksack();
		mediumRucksack = new MediumRucksack();
		militaryRucksack = new MilitaryRucksack();
		GameRegistry.registerItem(schoolRucksack, schoolRucksack.getUnlocalizedName());
		GameRegistry.registerItem(mediumRucksack, mediumRucksack.getUnlocalizedName());
		GameRegistry.registerItem(militaryRucksack, militaryRucksack.getUnlocalizedName());
		LanguageRegistry.addName(schoolRucksack, "Chzech backpack");
		LanguageRegistry.addName(mediumRucksack, "Survival backpack");
		LanguageRegistry.addName(militaryRucksack, "Coyote backpack");

	}

	@EventHandler
	public void Init(FMLInitializationEvent e) {
		proxy.Init();
	}

}
