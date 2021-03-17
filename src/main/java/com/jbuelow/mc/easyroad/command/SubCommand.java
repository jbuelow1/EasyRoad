package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface SubCommand {

    boolean onCommand(CommandSender sender, String command, String[] args);

    String[] getCallables();

}
