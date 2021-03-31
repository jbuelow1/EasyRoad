package com.jbuelow.mc.easyroad.store;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Road {

    private final UUID uuid;

    private ArrayList<RoadSegment> segments;

    private String name;

    private Road(UUID uuid) {
        this.uuid = uuid;
    }

    public Road() {
        uuid = UUID.randomUUID();
    }

    public Configuration saveToConfig(Configuration conf) {
        conf.set("uuid", uuid.toString());
        conf.set("name", name);

        conf.createSection("segments");

        for (RoadSegment rs : segments) {
            conf.set("segments." + rs.getSegId() + ".a", rs.getPoint1());
            conf.set("segments." + rs.getSegId() + ".b", rs.getPoint2());
        }

        return conf;
    }

    public static Road fromConfiguration(Configuration conf) {
        Road r = new Road(UUID.fromString(conf.get("uuid").toString()));
        r.name = conf.get("name").toString();

        for (Object segkey: conf.getList("segments")) {
            String s = (String) segkey;

            Location p1 = conf.getLocation("segments." + s + ".a");
            Location p2 = conf.getLocation("segments." + s + ".b");

            RoadSegment seg = new RoadSegment() {
                @Override
                public Location getPoint1() {
                    return p1;
                }

                @Override
                public Location getPoint2() {
                    return p2;
                }

                @Override
                public Road getRoad() {
                    return r;
                }

                @Override
                public String getSegId() {
                    return s;
                }
            };

            r.segments.add(seg);
        }
        return r;
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
}
