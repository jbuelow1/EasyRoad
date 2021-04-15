package com.jbuelow.mc.easyroad.store;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class RoadSegment extends AnonymousRoadSegment {

    private Road road;

    public RoadSegment(Location p1, Location p2, Road road) {
        super(p1, p2);
        this.road = road;
    }

    public Road getRoad() {
        return road;
    }

    public String getID() {
        return getRoad().getUUID().toString() + "#" + getSegId().toString();
    }

    public static RoadSegment attachRoad(AnonymousRoadSegment ars, Road road) {
        RoadSegment roadSeg = new RoadSegment(ars.getPoint1(), ars.getPoint2(), road);
        roadSeg.segId = ars.getSegId();
        return roadSeg;
    }
    
}
