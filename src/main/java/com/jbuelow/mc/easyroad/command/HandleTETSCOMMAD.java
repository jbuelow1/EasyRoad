package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.store.Road;
import com.jbuelow.mc.easyroad.store.RoadSegment;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class HandleTETSCOMMAD implements CommandExecutor {

    private final EasyRoad easyRoad;

    public HandleTETSCOMMAD(EasyRoad easyRoad) {
        this.easyRoad = easyRoad;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Road r = new Road();
        r.setName("penis lane");
        UUID uid = UUID.randomUUID();
        r.getSegments().add(new RoadSegment(new Location(easyRoad.getServer().getWorld("world"), 0,0,0), new Location(easyRoad.getServer().getWorld("world"), 100,0,0), r));
        easyRoad.storageController.addRoad(r);
        easyRoad.storageController.save();
        easyRoad.renderer.rerenderAll();
        return true;
    }
}
