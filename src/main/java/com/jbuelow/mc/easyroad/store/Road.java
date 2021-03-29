package com.jbuelow.mc.easyroad.store;

import org.bukkit.configuration.Configuration;

import java.util.UUID;

public class Road {

    private final UUID uuid;
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

        return conf;
    }

    public static Road fromConfiguration(Configuration conf) {
        Road r = new Road(UUID.fromString(conf.get("uuid").toString()));
        r.name = conf.get("name").toString();

        return r;
    }

    public UUID getUUID() {
        return uuid;
    }
}
