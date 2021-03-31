package com.jbuelow.mc.easyroad.store;

import org.bukkit.Location;

import java.util.UUID;

public abstract class RoadSegment {

    public abstract Location getPoint1();
    public abstract Location getPoint2();
    public abstract Road getRoad();
    public abstract String getSegId();

    public String getID() {
        return getRoad().getUUID().toString() + "#" + getSegId();
    }
}
