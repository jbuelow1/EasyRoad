package com.jbuelow.mc.easyroad.command.rewrite;

import com.github.overmighty.croissant.command.CommandExecutor;
import com.github.overmighty.croissant.command.CroissantCommand;
import com.github.overmighty.croissant.command.argument.Default;
import com.github.overmighty.croissant.command.argument.Optional;
import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.Session;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;

public class HandleCommandEasyRoadStop extends CroissantCommand {

    private EasyRoad easyRoad;

    public HandleCommandEasyRoadStop(EasyRoad easyRoad) {
        //configure
        super("stop");
        super.setDescription("Ends an existing EasyRoad session and saves road data.");
        super.setUsage("/easyroad stop [dontsave?]");
        super.setAliases("s", "end");
        super.setPlayerOnly(true);

        this.easyRoad = easyRoad;
    }

    @CommandExecutor
    public void run(Player sender, @Default("true") boolean save) {
        Map<Player, Session> sessions = easyRoad.getActiveSessions();
        if (!sessions.containsKey(sender)) {
            sender.sendMessage(ChatColor.RED + "Error: You are not in an EasyRoad session!");
            return;
        }

        Session s = sessions.get(sender);

        s.disable();
        sessions.remove(sender);

        sender.sendMessage(ChatColor.BLUE + "EasyRoad session ended.");

        if (!save) {
            easyRoad.storageController.removeRoadByUuid(s.getRoad().getUUID());
            easyRoad.renderer.rerenderAll();
            easyRoad.storageController.save();
        } else {
            sender.sendMessage(ChatColor.BLUE + "Saved road \"" + s.getRoad().getName() + "\"\n" + ChatColor.DARK_BLUE + "(id: "+ChatColor.GRAY+s.getRoad().getUUID().toString()+ChatColor.DARK_BLUE+")");
        }
    }

}
