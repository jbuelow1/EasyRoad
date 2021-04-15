package com.jbuelow.mc.easyroad;

import com.jbuelow.mc.easyroad.command.HandleCommandRoot;
import com.jbuelow.mc.easyroad.command.HandleTETSCOMMAD;
import com.jbuelow.mc.easyroad.event.EventPlayerInteractListener;
import com.jbuelow.mc.easyroad.render.DynmapRenderer;
import com.jbuelow.mc.easyroad.render.Renderer;
import com.jbuelow.mc.easyroad.store.AnonymousRoadSegment;
import com.jbuelow.mc.easyroad.store.Road;
import com.jbuelow.mc.easyroad.store.RoadSegment;
import com.jbuelow.mc.easyroad.store.RoadStyle;
import com.jbuelow.mc.easyroad.store.controller.StorageController;
import com.jbuelow.mc.easyroad.store.controller.YamlStorageController;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

import java.util.HashMap;
import java.util.logging.Level;

public final class EasyRoad extends JavaPlugin {

    private DynmapAPI dapi = null;
    private HashMap<Player, Session> activeSessions;
    public StorageController storageController;
    public Renderer renderer = new DynmapRenderer(this);

    public EasyRoad() {
        this.activeSessions = new HashMap<>();
        ConfigurationSerialization.registerClass(Road.class);
        //ConfigurationSerialization.registerClass(RoadSegment.class);
        ConfigurationSerialization.registerClass(AnonymousRoadSegment.class);
        ConfigurationSerialization.registerClass(RoadStyle.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        //register commands
        getCommand("easyroad").setExecutor(new HandleCommandRoot(this));

        getCommand("testroad").setExecutor(new HandleTETSCOMMAD(this));

        //register event listeners
        getServer().getPluginManager().registerEvents(new EventPlayerInteractListener(this), this);

        //load dynmap api
        dapi = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        if (dapi == null) {
            getLogger().log(Level.SEVERE, "Error: dynmap is not installed on this server! EasyRoad requires dynmap to function. You can download dynmap here: https://www.spigotmc.org/resources/dynmap.274/");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }

        storageController = new YamlStorageController(this);
        storageController.load();

        renderer.applyRoadList(storageController.getRoadList());
        renderer.rerenderAll();

        getLogger().log(Level.INFO, "Started EasyRoad");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().log(Level.INFO, "Saving road database...");
        storageController.save();
        getLogger().log(Level.INFO, "Done!");
    }

    public HashMap<Player, Session> getActiveSessions() {
        return activeSessions;
    }

    public DynmapAPI getDapi() {
        return dapi;
    }
}
