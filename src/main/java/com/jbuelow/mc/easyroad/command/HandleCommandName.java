package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandleCommandName implements SubCommand {

    private final EasyRoad easyRoad;

    public HandleCommandName(EasyRoad easyRoad) {
        this.easyRoad = easyRoad;
    }

    @Override
    public boolean onCommand(CommandSender sender, String command, String[] args) {
        Player p;

        if (sender instanceof Player) {
            p = (Player) sender;
        } else {
            sender.sendMessage("Error: You must use this command as a player!");
            return true;
        }

        if (!easyRoad.getActiveSessions().containsKey(p)) {
            p.sendMessage("Error: You are not in an EasyRoad session!");
            return true;
        }

        if (args.length < 1) {
            p.sendMessage("Error: You must supply a name!");
            return true;
        }

        String roadName = args[0];

        for (int i = 1; i < args.length; i++) {
            roadName += " " + args[i];
        }

        easyRoad.getActiveSessions().get(p).setRoadName(roadName);

        p.sendMessage("New roads in this session will be named \"" + roadName + "\".");
        return true;
    }

    @Override
    public String[] getCallables() {
        return new String[] {"name"};
    }
}
