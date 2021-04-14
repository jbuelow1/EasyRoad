package com.jbuelow.mc.easyroad.command.rewrite;

import com.github.overmighty.croissant.command.CommandExecutor;
import com.github.overmighty.croissant.command.CroissantCommand;
import com.jbuelow.mc.easyroad.EasyRoad;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HandleCommandEasyRoad extends CroissantCommand {

    public HandleCommandEasyRoad(EasyRoad easyRoad) {
        //configure
        super("easyroad");
        super.setDescription("Create and manage easyroad road markers.");
        super.setAliases("er");
        super.setPlayerOnly(true);

        //subcommands
        super.addSubcommand(new HandleCommandEasyRoadCreate(easyRoad));
        super.addSubcommand(new HandleCommandEasyRoadStop(easyRoad));
    }

    @CommandExecutor
    public void run (Player sender) {
        sender.sendMessage(ChatColor.YELLOW + "EasyRoad commands:");
        for (CroissantCommand subcommand : super.getSubcommands().values()) {
            sender.sendMessage(ChatColor.GOLD + subcommand.getUsage(subcommand.getName()) + ": " +
                    ChatColor.RESET + subcommand.getDescription());
        }
    }
}
