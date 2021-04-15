package com.jbuelow.mc.easyroad.store.controller;

import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.store.Road;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;

public class YamlStorageController implements StorageController {

    private final EasyRoad easyRoad;

    HashMap<UUID, YamlConfiguration> roadconfigs;

    public YamlStorageController(EasyRoad easyRoad) {
        this.easyRoad = easyRoad;
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
        roadconfigs = new HashMap<>();

        if (!easyRoad.getDataFolder().exists()) easyRoad.getDataFolder().mkdir();
        if (!new File(easyRoad.getDataFolder(), "roads").exists()) new File(easyRoad.getDataFolder(), "roads").mkdir();

        for (File f : new File(easyRoad.getDataFolder(), "roads").listFiles()) {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
            UUID uuid = UUID.fromString(((Road) conf.get("roaddata")).getUUID().toString());
            roadconfigs.put(uuid, conf);
        }

        easyRoad.getLogger().log(Level.INFO, "Loaded " + roadconfigs.size() + " roads from yaml datastores.");
    }

    @Override
    public void save() {
        for (Map.Entry<UUID, YamlConfiguration> e : roadconfigs.entrySet()) {
            try {
                e.getValue().save(new File(easyRoad.getDataFolder(), "roads/" + e.getKey().toString() + ".yml"));
            } catch (IOException ioException) {
                easyRoad.getLogger().log(Level.WARNING, "Failed to save file for road with UUID: " + e.getKey().toString(), ioException);
            }
        }
    }

    @Override
    public Road getRoadByUUID(UUID uuid) {
        return (Road) roadconfigs.get(uuid).get("roaddata");
    }

    @Override
    public ArrayList<Road> getRoadList() {
        ArrayList<Road> roads = new ArrayList<>();
        for (Map.Entry<UUID, YamlConfiguration> e : roadconfigs.entrySet()) {
            roads.add((Road) e.getValue().get("roaddata"));
        }
        return roads;
    }

    @Override
    public HashMap<UUID, Road> getRoadMap() {
        HashMap<UUID, Road> roadmap = new HashMap<>();
        for (Map.Entry<UUID, YamlConfiguration> e : roadconfigs.entrySet()) {
            roadmap.put(e.getKey(), (Road) e.getValue().get("roaddata"));
        }
        return roadmap;
    }

    @Override
    public void addRoad(Road road) {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(new File(easyRoad.getDataFolder(), "roads/" + road.getUUID().toString() + ".yml"));
        conf.set("roaddata", road);
        roadconfigs.put(road.getUUID(), conf);
    }
}
