package com.jbuelow.mc.easyroad;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.PolyLineMarker;

import java.util.ArrayList;

public class Session {

    private final EasyRoad easyRoad;
    private final Player player;

    private boolean enabled = false;

    private Location p1;
    private Location p2;

    private Location p2a;

    ArrayList<PolyLineMarker> lineHistory = new ArrayList<>();
    ArrayList<Location> pointHistory = new ArrayList<>();

    private int id = 0;

    private String set = "EasyRoad";
    private String roadName = "Unnamed Road";

    public Session(EasyRoad easyRoad, Player player) {
        this.easyRoad = easyRoad;
        this.player = player;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void handleClick(PlayerInteractEvent event) {

        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            event.getPlayer().sendMessage("Error: Click the ground!");
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p2a == null) {
                p2a = event.getClickedBlock().getLocation();
                p2a.add(0.5, 1, 0.5);
                event.getPlayer().sendMessage("Set subpoint to " + p1.getX() + " " + p1.getY() + " " + p1.getZ());
                return;
            } else {
                Location b = event.getClickedBlock().getLocation();
                b.add(0.5, 1, 0.5);
                p2 = new Location(p2a.getWorld(), (p2a.getX() + b.getX())/2, (p2a.getY() + b.getY())/2, (p2a.getZ() + b.getZ())/2);
                event.getPlayer().sendMessage("Set next point to " + p2.getX() + " " + p2.getY() + " " + p2.getZ());
            }
        }

        if (p1 == null) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                p1 = event.getClickedBlock().getLocation();
                p1.add(0.5, 1, 0.5);
            } else {
                p1 = p2;
            }
            event.getPlayer().sendMessage("Set first point to " + p1.getX() + " " + p1.getY() + " " + p1.getZ());
        } else {
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                p2 = event.getClickedBlock().getLocation();
                p2.add(0.5, 1, 0.5);
            }
            event.getPlayer().sendMessage("Set next point to " + p2.getX() + " " + p2.getY() + " " + p2.getZ());
            makeLine();
            event.getPlayer().sendMessage("Line created!");
            pointHistory.add(p1);
            p1 = p2;
        }
    }

    private void makeLine() {
        MarkerSet markerset = easyRoad.getDapi().getMarkerAPI().getMarkerSet(set);

        if (markerset == null) {
            markerset = easyRoad.getDapi().getMarkerAPI().createMarkerSet(set, "EasyRoad Set", easyRoad.getDapi().getMarkerAPI().getMarkerIcons(), false);
        }

        PolyLineMarker marker = markerset.createPolyLineMarker("road" + id++, roadName, false, p1.getWorld().getName(),
                new double[]{p1.getX(), p2.getX()}, new double[]{p1.getY(), p2.getY()}, new double[]{p1.getZ(), p2.getZ()}, false);

        lineHistory.add(marker);
    }

    public boolean undo() {
        if (lineHistory.size() > 0 && pointHistory.size() > 0) {
            PolyLineMarker marker = lineHistory.get(lineHistory.size() - 1);
            lineHistory.remove(lineHistory.size() - 1);
            marker.deleteMarker();

            p2 = p1;
            p1 = pointHistory.get(pointHistory.size() - 1);
            pointHistory.remove(pointHistory.size() - 1);
            return true;
        }
        return false;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
}
