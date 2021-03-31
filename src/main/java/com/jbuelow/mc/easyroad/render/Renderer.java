package com.jbuelow.mc.easyroad.render;

import com.jbuelow.mc.easyroad.store.Road;
import com.jbuelow.mc.easyroad.store.RoadSegment;

import java.util.ArrayList;

public interface Renderer {

    void renderRoad(Road road);
    void renderSegment(RoadSegment segment);

    void rerenderAll();
    void applyRoadList(ArrayList<Road> roads);
    void reset();

}
