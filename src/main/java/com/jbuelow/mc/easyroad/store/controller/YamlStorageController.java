package com.jbuelow.mc.easyroad.store.controller;

import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.store.Road;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
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
        for (File f : new File(easyRoad.getDataFolder(), "roads").listFiles()) {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
            UUID uuid = UUID.fromString(conf.getString("uuid"));
            roadconfigs.put(uuid, conf);
        }
        easyRoad.getLogger().log(Level.INFO, "Loaded " + roadconfigs.size() + " roads from yaml datastores.");
    }

    @Override
    public void save() throws IOException {
        for (Map.Entry<UUID, YamlConfiguration> e : roadconfigs.entrySet()) {
            e.getValue().save(new File(easyRoad.getDataFolder(), "roads/" + e.getKey().toString() + ".yml"));
        }
    }

    @Override
    public Road getRoadByUUID(UUID uuid) {
        return Road.fromConfiguration(roadconfigs.get(uuid));
    }

    @Override
    public ArrayList<Road> getRoadList() {
        ArrayList<Road> roads = new ArrayList<>();
        for (Map.Entry<UUID, YamlConfiguration> e : roadconfigs.entrySet()) {
            roads.add(Road.fromConfiguration(e.getValue()));
        }
        return roads;
    }

    @Override
    public HashMap<UUID, Road> getRoadMap() {
        HashMap<UUID, Road> roadmap = new HashMap<>();
        for (Map.Entry<UUID, YamlConfiguration> e : roadconfigs.entrySet()) {
            roadmap.put(e.getKey(), Road.fromConfiguration(e.getValue()));
        }
        return roadmap;
    }

    @Override
    public void addRoad(Road road) {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(new File(easyRoad.getDataFolder(), "roads/" + road.getUUID().toString() + ".yml"));

        roadconfigs.put(road.getUUID(), (YamlConfiguration) road.saveToConfig(conf));
    }
}
