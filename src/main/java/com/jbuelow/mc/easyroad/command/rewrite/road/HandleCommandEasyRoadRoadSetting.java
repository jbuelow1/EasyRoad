package com.jbuelow.mc.easyroad.command.rewrite.road;

import com.github.overmighty.croissant.command.CommandExecutor;
import com.github.overmighty.croissant.command.CroissantCommand;
import com.jbuelow.mc.easyroad.EasyRoad;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.jbuelow.mc.easyroad.conf.ColorScheme.*;

public class HandleCommandEasyRoadRoadSetting extends CroissantCommand {

    public HandleCommandEasyRoadRoadSetting(EasyRoad easyRoad) {
        //configure
        super("setting");
        super.setDescription("Changes settings about the current road session");
        super.setUsage("/easyroad road setting <setting> <value>");
        super.setAliases("s", "set");
        super.setPlayerOnly(true);

        //subcommands
    }

    @CommandExecutor
    public void run (Player sender) {
        sender.sendMessage(TEXT_EMPHASIS + "road settings:");
        for (CroissantCommand subcommand : super.getSubcommands().values()) {
            sender.sendMessage(TEXT_DEFAULT + subcommand.getUsage(subcommand.getName()) + ": " +
                    TEXT_LOWEMPHASIS + subcommand.getDescription());
        }
    }
}
