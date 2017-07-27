package de.ellpeck.wopper;

import de.ellpeck.wopper.proxy.IProxy;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Wopper.MOD_ID, name = Wopper.NAME, version = Wopper.VERSION)
public class Wopper{

    public static final String MOD_ID = "wopper";
    public static final String NAME = "Wopper";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(clientSide = "de.ellpeck.wopper.proxy.ClientProxy",serverSide = "de.ellpeck.wopper.proxy.ServerProxy")
    public static IProxy proxy;

    public static boolean canBeDeactivated;
    public static int wopperSpeed;
    public static int extractAmount;
    public static int pickupAmount;

    public static Block blockWopper;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        canBeDeactivated = config.getBoolean("canBeDeactivated", Configuration.CATEGORY_GENERAL, true, "If the wopper can be deactivated using redstone");
        wopperSpeed = config.getInt("speed", Configuration.CATEGORY_GENERAL, 10, 1, 1000, "The amount of ticks that have to pass before the wopper does a movement action again");
        extractAmount = config.getInt("extractAmount", Configuration.CATEGORY_GENERAL, 1, 1, 64, "The amount of items the wopper extracts from an inventory above it every cycle");
        pickupAmount = config.getInt("pickupAmount", Configuration.CATEGORY_GENERAL, 1, 1, 64, "The amount of items the wopper picks up from the world every cycle");

        if(config.hasChanged()){
            config.save();
        }

        blockWopper = new BlockWopper("wopper");
        GameRegistry.registerTileEntity(TileEntityWopper.class, MOD_ID+":wopper");

        proxy.preInit();
    }
}
