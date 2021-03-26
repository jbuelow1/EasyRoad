package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandleCommandStop implements SubCommand {

    private final EasyRoad easyRoad;

    public HandleCommandStop(EasyRoad easyRoad) {
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

        easyRoad.getActiveSessions().get(p).disable();

        easyRoad.getActiveSessions().remove(p);
        p.sendMessage("EasyRoad session ended.");
        return true;
    }

    @Override
    public String[] getCallables() {
        return new String[] {"stop"};
    }

}
