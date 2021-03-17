package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandleCommandSet implements SubCommand {

    private final EasyRoad easyRoad;

    public HandleCommandSet(EasyRoad easyRoad) {
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
            p.sendMessage("Error: You must supply a set id!");
            return true;
        }

        easyRoad.getActiveSessions().get(p).setSet(args[0]);

        p.sendMessage("New roads in this session will be a part of the \"" + args[0] + "\" set.");
        return true;
    }

    @Override
    public String[] getCallables() {
        return new String[] {"set"};
    }
}
