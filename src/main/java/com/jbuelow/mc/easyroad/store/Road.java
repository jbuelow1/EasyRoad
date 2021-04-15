package com.jbuelow.mc.easyroad.store;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class Road implements ConfigurationSerializable {

    private final UUID uuid;
    private String name;
    private RoadStyle style;

    private ArrayList<RoadSegment> segments;

    private Road(UUID uuid) {
        this.uuid = uuid;
        segments = new ArrayList<>();
        name = "Unnamed Road";
        style = new RoadStyle();
    }

    public Road() {
        this(UUID.randomUUID());
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<RoadSegment> getSegments() {
        return segments;
    }

    public RoadStyle getStyle() {
        return style;
    }

    public void setStyle(RoadStyle style) {
        this.style = style;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", uuid.toString());
        map.put("name", name);
        map.put("style", style);
        List<AnonymousRoadSegment> anonSegs = new ArrayList<>();
        for (RoadSegment seg : segments) anonSegs.add(new AnonymousRoadSegment(seg));
        map.put("segments", anonSegs);
        return map;
    }

    public static Road deserialize(Map<String, Object> map) {
        UUID uuid = UUID.fromString((String) map.get("uuid"));
        Road road = new Road(uuid);
        road.name = (String) map.get("name");
        road.style = (RoadStyle) map.get("style");
        List<AnonymousRoadSegment> anonList = (List<AnonymousRoadSegment>) map.get("segments");
        for (AnonymousRoadSegment anonSeg : anonList) {
            road.segments.add(RoadSegment.attachRoad(anonSeg, road));
        }
        return road;
    }
}
