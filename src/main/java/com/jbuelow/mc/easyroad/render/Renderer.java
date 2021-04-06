package com.jbuelow.mc.easyroad.render;

import com.jbuelow.mc.easyroad.store.Road;
import com.jbuelow.mc.easyroad.store.RoadSegment;
import org.dynmap.markers.PolyLineMarker;

import java.util.ArrayList;
import java.util.HashMap;

public interface Renderer {

    void renderRoad(Road road);
    void renderSegment(RoadSegment segment);

    void rerenderAll();
    void applyRoadList(ArrayList<Road> roads);
    void reset();

    HashMap<RoadSegment, PolyLineMarker> getRenderedMarkers();
}
