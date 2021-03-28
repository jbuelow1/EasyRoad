package com.jbuelow.mc.easyroad;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.dynmap.markers.MarkerSet;
import org.dynmap.markers.PolyLineMarker;

import java.math.BigInteger;
import java.util.*;

public class Session {

    private final EasyRoad easyRoad;
    private final Player player;

    private boolean enabled = false;

    private Location p1;
    private Location p2;

    private Location p2a;

    ArrayList<PolyLineMarker> lineHistory = new ArrayList<>();
    ArrayList<Location> pointHistory = new ArrayList<>();

    private final Random r = new Random();

    private String set = "EasyRoad";
    private String roadName = "Unnamed Road";

    private int weight = 3;
    private double opacity = 0.8;
    private int color = 0xFF0000;

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

        event.setCancelled(true);

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p2a == null) {
                p2a = event.getClickedBlock().getLocation();
                p2a.add(0.5, 1, 0.5);
                event.getPlayer().sendMessage("Set subpoint to " + p1.getX() + " " + p1.getY() + " " + p1.getZ());
              
                player.playSound(p2a, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                player.spawnParticle(Particle.REDSTONE, p2a, 10, 0.0125, 0.00125, 0.0125, new Particle.DustOptions(Color.YELLOW, 3));
                return;
            } else {
                Location b = event.getClickedBlock().getLocation();
                b.add(0.5, 1, 0.5);
                p2 = new Location(p2a.getWorld(), (p2a.getX() + b.getX())/2, (p2a.getY() + b.getY())/2, (p2a.getZ() + b.getZ())/2);
                p2a = null;
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
          
            player.playSound(p1, Sound.BLOCK_ANVIL_PLACE, 1, 1);
            player.spawnParticle(Particle.COMPOSTER, p1, 25, 0.125, 0.0125, 0.125);
        } else {
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                p2 = event.getClickedBlock().getLocation();
                p2.add(0.5, 1, 0.5);
            }
            event.getPlayer().sendMessage("Set next point to " + p2.getX() + " " + p2.getY() + " " + p2.getZ());

            //Show green diamond particle on either end of the line
            player.spawnParticle(Particle.COMPOSTER, p1, 25, 0.125, 0.0125, 0.125);
            player.spawnParticle(Particle.COMPOSTER, p2, 25, 0.125, 0.0125, 0.125);

            makeLine();
            event.getPlayer().sendMessage("Line created!");
            pointHistory.add(p1);
            p1 = p2;
        }
    }

    private void makeLine() {
        MarkerSet markerset = easyRoad.getDapi().getMarkerAPI().getMarkerSet(set);

        if (markerset == null) {
            markerset = easyRoad.getDapi().getMarkerAPI().createMarkerSet(set, "EasyRoad Set", easyRoad.getDapi().getMarkerAPI().getMarkerIcons(), true);
        }

        UUID uid = UUID.randomUUID();
        String id = uid.toString();

        PolyLineMarker marker = markerset.createPolyLineMarker("eroad_" + id, roadName, false, p1.getWorld().getName(),
                new double[]{p1.getX(), p2.getX()}, new double[]{p1.getY(), p2.getY()}, new double[]{p1.getZ(), p2.getZ()}, false);

        marker.setLineStyle(weight, opacity, color);

        lineHistory.add(marker);

        player.playSound(p2, Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, 1, 1);
      
        //Show dust particles along line
        double distX = p2.getX() - p1.getX();
        double distY = p2.getY() - p1.getY();
        double distZ = p2.getZ() - p1.getZ();
        double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2) + Math.pow(distZ, 2));

        for (int i = 1; i < (int) dist; i++) {
            double p = i * (dist / (int) dist);
            Location part = new Location(p1.getWorld(), p1.getX(), p1.getY(), p1.getZ());
            part.add((distX / dist) * p, (distY / dist) * p, (distZ / dist) * p);
            player.spawnParticle(Particle.REDSTONE, part, 10, 0.25, 0.125, 0.25, new Particle.DustOptions(Color.fromRGB(color), 2));
        }
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
        } else if (p1 != null) {
            p1 = null;
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

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
