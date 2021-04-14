package com.jbuelow.mc.easyroad.store.controller;

import com.jbuelow.mc.easyroad.store.Road;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface StorageController {

    boolean isInitialized();
    void init();
    void load();
    void save();
    Road getRoadByUUID(UUID uuid) throws NoSuchElementException;
    ArrayList<Road> getRoadList();
    HashMap<UUID, Road> getRoadMap();
    void addRoad(Road road);
    void removeRoadByUuid(UUID uuid);
}
