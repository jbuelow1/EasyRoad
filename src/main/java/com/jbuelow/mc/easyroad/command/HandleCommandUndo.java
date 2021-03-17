package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandleCommandUndo implements SubCommand {

    private final EasyRoad easyRoad;

    public HandleCommandUndo(EasyRoad easyRoad) {
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

        if (easyRoad.getActiveSessions().get(p).undo()) {
            p.sendMessage("Undid last line and point.");
        } else {
            p.sendMessage("Error: Nothing to undo!");
        }
        return true;
    }

    @Override
    public String[] getCallables() {
        return new String[] {"undo", "u"};
    }
}
