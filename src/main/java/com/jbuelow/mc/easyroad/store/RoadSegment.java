package com.jbuelow.mc.easyroad.store;

import org.bukkit.Location;

public abstract class RoadSegment {
    public abstract Location getPoint1();
    public abstract Location getPoint2();
    public abstract Road getRoad();
}
