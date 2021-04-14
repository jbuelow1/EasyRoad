package com.jbuelow.mc.easyroad.command.rewrite.road.setting;

import com.github.overmighty.croissant.command.CommandExecutor;
import com.github.overmighty.croissant.command.CroissantCommand;
import com.github.overmighty.croissant.command.argument.Rest;
import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.Session;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;

public class HandleCommandEasyRoadRoadSettingName extends CroissantCommand {

    private EasyRoad easyRoad;

    public HandleCommandEasyRoadRoadSettingName(EasyRoad easyRoad) {
        super("name");
        super.setDescription("Sets the display name of a road");
        super.setUsage("/easyroad road setting name <road name...>");
        super.setAliases("n");
        super.setPlayerOnly(true);

        this.easyRoad = easyRoad;
    }

    @CommandExecutor
    public void run(Player sender, @Rest String name) {
        Map<Player, Session> sessions = easyRoad.getActiveSessions();
        if (!sessions.containsKey(sender)) {
            sender.sendMessage(ChatColor.RED + "Error: You are not in an EasyRoad session!");
            return;
        }

        Session s = sessions.get(sender);

        s.setRoadName(name);
        sender.sendMessage(ChatColor.BLUE + "Set road name!");
    }

}
