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

import static com.jbuelow.mc.easyroad.conf.ColorScheme.*;

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
            sender.sendMessage(TEXT_ERROR + "Error: You are not in an EasyRoad session!");
            return;
        }

        Session s = sessions.get(sender);

        s.disable();
        sessions.remove(sender);

        sender.sendMessage(TEXT_DEFAULT + "EasyRoad session ended.");

        if (!save) {
            easyRoad.storageController.removeRoadByUuid(s.getRoad().getUUID());
            easyRoad.renderer.rerenderAll();
            easyRoad.storageController.save();
        } else {
            sender.sendMessage(TEXT_DEFAULT + "Saved road \"" + s.getRoad().getName() + "\"\n" + TEXT_LOWEMPHASIS + "(id: "+TEXT_INSIGNIFICANT+s.getRoad().getUUID().toString()+TEXT_LOWEMPHASIS+")");
        }
    }

}
