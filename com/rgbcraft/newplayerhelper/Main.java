package com.rgbcraft.newplayerhelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.BaseMod;
import net.minecraftforge.common.MinecraftForge;

@Mod(name="NewPlayerHelper", version="1.0", modid="NewPlayerHelper")
public class Main extends BaseMod{
	
	public static Logger nphLog = Logger.getLogger("NewPlayerHelper");
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	

	@Override
	public void load() {
	}
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		nphLog.setParent(FMLLog.getLogger());
		nphLog.info("New Player Helper v1.0 initialized");		
		if (new File("./world/playerdata/NewPlayerHelper").mkdirs()) {
			nphLog.info("Created new data directory");
		};
	}
	
	
	
	@Init
	public void init(FMLInitializationEvent event) {
		System.out.println("RegisterPlayerTracker");
		GameRegistry.registerPlayerTracker(new PlayerTracker());
	}
	
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
