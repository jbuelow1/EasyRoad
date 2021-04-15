package com.jbuelow.mc.easyroad.store;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnonymousRoadSegment implements ConfigurationSerializable {

    protected Location point1;
    protected Location point2;
    protected UUID segId;

    public AnonymousRoadSegment(Location p1, Location p2) {
        point1 = p1;
        point2 = p2;
        segId = UUID.randomUUID();
    }

    public AnonymousRoadSegment(RoadSegment roadSegment) {
        point1 = roadSegment.getPoint1();
        point2 = roadSegment.getPoint2();
        segId = roadSegment.getSegId();
    }

    public Location getPoint1(){
        return point1;
    }

    public Location getPoint2(){
        return point2;
    }

    public UUID getSegId(){
        return segId;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", getSegId().toString());
        map.put("p1", getPoint1());
        map.put("p2", getPoint2());
        return map;
    }

    public static AnonymousRoadSegment deserialize(Map<String, Object> map) {
        Location p1 = (Location) map.get("p1");
        Location p2 = (Location) map.get("p2");
        UUID uuid = UUID.fromString((String) map.get("uuid"));

        AnonymousRoadSegment ars = new AnonymousRoadSegment(p1, p2);
        ars.segId = uuid;
        return ars;
    }
}
