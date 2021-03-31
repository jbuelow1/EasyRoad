package com.jbuelow.mc.easyroad.store;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class Road {

    private final UUID uuid;

    private ArrayList<RoadSegment> segments;

    private String name;

    private Road(UUID uuid) {
        this.uuid = uuid;
        segments = new ArrayList<>();
        name = "Unnamed Road";
    }

    public Road() {
        this(UUID.randomUUID());
    }

    public Configuration saveToConfig(Configuration conf) {
        conf.set("uuid", uuid.toString());
        conf.set("name", name);

        ArrayList<HashMap<String, Object>> segconf = new ArrayList<>();

        for (RoadSegment rs : segments) {
            HashMap<String, Object> segmap = new HashMap<>();
            segmap.put("uuid", rs.getSegId());
            segmap.put("p1", rs.getPoint1());
            segmap.put("p2", rs.getPoint2());
            segconf.add(segmap);
        }

        conf.set("segments", segconf);

        return conf;
    }

    public static Road fromConfiguration(Configuration conf) {
        Road r = new Road(UUID.fromString(conf.get("uuid").toString()));
        r.name = conf.get("name").toString();

        for (Object segconf: conf.getList("segments")) {
            String s = (String) ((HashMap<String, ?>) segconf).get("uuid");

            Location p1 = (Location) ((HashMap<String, ?>) segconf).get("p1");
            Location p2 = (Location) ((HashMap<String, ?>) segconf).get("p2");

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
