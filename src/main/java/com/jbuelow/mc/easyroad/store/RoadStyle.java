package com.jbuelow.mc.easyroad.store;

import org.bukkit.Color;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class RoadStyle implements ConfigurationSerializable {

    private Color color;
    private double opacity;
    private int weight;

    public RoadStyle(Color color, double opacity, int weight) {
        this.color = color;
        this.opacity = opacity;
        this.weight = weight;
    }

    public RoadStyle() {
        this(Color.RED, 0.8, 3);
    }

    public Color getColor() {
        return color;
    }

    public double getOpacity() {
        return opacity;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("color", color);
        map.put("opacity", opacity);
        map.put("weight", weight);
        return map;
    }

    public static RoadStyle deserialize(Map<String, Object> map) {
        return new RoadStyle((Color) map.get("color"), (double) map.get("opacity"), (int) map.get("weight"));
    }
}
