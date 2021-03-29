package com.jbuelow.mc.easyroad;

import com.jbuelow.mc.easyroad.command.HandleCommandRoot;
import com.jbuelow.mc.easyroad.event.EventPlayerInteractListener;
import com.jbuelow.mc.easyroad.store.controller.StorageController;
import com.jbuelow.mc.easyroad.store.controller.YamlStorageController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

import java.util.HashMap;
import java.util.logging.Level;

public final class EasyRoad extends JavaPlugin {

    private DynmapAPI dapi = null;
    private HashMap<Player, Session> activeSessions;
    private StorageController storageController;

    public EasyRoad() {
        this.activeSessions = new HashMap<>();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        //register commands
        getCommand("easyroad").setExecutor(new HandleCommandRoot(this));

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

        getLogger().log(Level.INFO, "Started EasyRoad");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public HashMap<Player, Session> getActiveSessions() {
        return activeSessions;
    }

    public DynmapAPI getDapi() {
        return dapi;
    }
}
