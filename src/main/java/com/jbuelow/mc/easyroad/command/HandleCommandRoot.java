package com.jbuelow.mc.easyroad.command;

import com.jbuelow.mc.easyroad.EasyRoad;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class HandleCommandRoot implements CommandExecutor {

    private final EasyRoad easyRoad;

    private final HashMap<String, SubCommand> subcommands;

    public HandleCommandRoot(EasyRoad easyRoad) {
        this.easyRoad = easyRoad;

        //Load all subcommands
        subcommands = new HashMap<>();

        SubCommand[] subCommandHandlers = new SubCommand[] {new HandleCommandStart(easyRoad),
                new HandleCommandStop(easyRoad),
                new HandleCommandUndo(easyRoad),
                new HandleCommandName(easyRoad),
                new HandleCommandSet(easyRoad)};

        for (SubCommand sc : subCommandHandlers) {
            for (String callable : sc.getCallables()) {
                subcommands.put(callable, sc);
            }
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Subcommand needed!");
            return false;
        }

        if (subcommands.containsKey(args[0])) {

            String[] subargs = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                subargs[i - 1] = args[i];
            }

            return subcommands.get(args[0]).onCommand(sender, args[0], subargs);
        } else {
            sender.sendMessage("Error: " + label + " is not a valid subcommand!");
            return false;
        }
    }

}
