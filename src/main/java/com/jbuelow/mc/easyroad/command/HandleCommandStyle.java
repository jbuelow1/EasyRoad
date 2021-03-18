package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandleCommandStyle implements SubCommand {

    private final EasyRoad easyRoad;

    public HandleCommandStyle(EasyRoad easyRoad) {
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

        if (args.length < 2) {
            p.sendMessage("Error: Too few arguments! Usage: /easyroad style <weight|opacity|color> <value>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "weight":
            case "w":
                try {
                    easyRoad.getActiveSessions().get(p).setWeight(Integer.parseInt(args[1]));
                } catch (NumberFormatException ex) {
                    p.sendMessage("Error: Invalid value!");
                    return true;
                }
                break;
            case "opacity":
            case "o":
                try {
                    easyRoad.getActiveSessions().get(p).setOpacity(Double.parseDouble(args[1]));
                } catch (NumberFormatException ex) {
                    p.sendMessage("Error: Invalid value!");
                    return true;
                }
                break;
            case "color":
            case "c":
                try {
                    easyRoad.getActiveSessions().get(p).setColor(Integer.parseInt(args[1]));
                } catch (NumberFormatException ex) {
                    p.sendMessage("Error: Invalid value! Colors must be in integer format!");
                    return true;
                }
                break;
            default:
                p.sendMessage("Error: Invalid arguments! Usage: /easyroad style <weight|opacity|color> <value>");
                return true;
        }

        p.sendMessage("Updated style for future lines in session!");
        return true;
    }

    @Override
    public String[] getCallables() {
        return new String[] {"style", "s"};
    }
}
