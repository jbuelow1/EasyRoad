package com.jbuelow.mc.easyroad.render;

import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.store.Road;
import com.jbuelow.mc.easyroad.store.RoadSegment;
import org.bukkit.Location;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.PolyLineMarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DynmapRenderer implements Renderer {

    private final EasyRoad easyRoad;

    private ArrayList<Road> roadList;
    private HashMap<RoadSegment, PolyLineMarker> renderedMarkers;

    public DynmapRenderer(EasyRoad easyRoad) {
        this.easyRoad = easyRoad;
        roadList = new ArrayList<>();
        renderedMarkers = new HashMap<>();
    }

    @Override
    public void renderRoad(Road road) {
        for (RoadSegment segment : road.getSegments()) {
            renderSegment(segment);
        }
    }

    @Override
    public void renderSegment(RoadSegment segment) {
        MarkerSet markerset = easyRoad.getDapi().getMarkerAPI().getMarkerSet("easyroads");

        if (markerset == null) {
            markerset = easyRoad.getDapi().getMarkerAPI().createMarkerSet("easyroads", "EasyRoad Roads", easyRoad.getDapi().getMarkerAPI().getMarkerIcons(), false);
        }

        Location p1 = segment.getPoint1();
        Location p2 = segment.getPoint2();

        PolyLineMarker marker = markerset.createPolyLineMarker(segment.getSegId(), segment.getRoad().getName(), false, segment.getPoint1().getWorld().getName(),
                new double[]{p1.getX(), p2.getX()}, new double[]{p1.getY(), p2.getY()}, new double[]{p1.getZ(), p2.getZ()}, false);

        //marker.setLineStyle(segment.getRoad().); TODO

        renderedMarkers.put(segment, marker);
    }

    @Override
    public void rerenderAll() {
        wipeDynmap();
        for (Road road : roadList) {
            renderRoad(road);
        }
    }

    @Override
    public void applyRoadList(ArrayList<Road> roads) {
        this.roadList = roads;
    }

    @Override
    public void reset() {
        wipeDynmap();
        roadList = new ArrayList<>();
        renderedMarkers = new HashMap<>();
    }

    private void wipeDynmap() {
        for (Map.Entry<RoadSegment, PolyLineMarker> e : renderedMarkers.entrySet()) {
            e.getValue().deleteMarker();
            renderedMarkers.remove(e.getKey());
        }
    }
}
