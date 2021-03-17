package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.Session;
import com.jbuelow.mc.easyroad.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandleCommandStart implements SubCommand {

    private final EasyRoad easyRoad;

    public HandleCommandStart(EasyRoad easyRoad) {
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

        if (easyRoad.getActiveSessions().containsKey(p)) {
            p.sendMessage("Error: You are already in an EasyRoad session!");
            return true;
        }

        Session s = new Session(easyRoad, p);
        s.enable();

        easyRoad.getActiveSessions().put(p, s);
        p.sendMessage("Started EasyRoad session!");
        return true;
    }

    @Override
    public String[] getCallables() {
        return new String[] {"start"};
    }

}
