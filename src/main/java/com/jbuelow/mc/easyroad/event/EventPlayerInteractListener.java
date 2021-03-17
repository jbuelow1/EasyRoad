package com.jbuelow.mc.easyroad.event;

import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.Session;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.logging.Level;

public class EventPlayerInteractListener implements Listener {

    private final EasyRoad easyRoad;

    public EventPlayerInteractListener(EasyRoad easyRoad) {
        this.easyRoad = easyRoad;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (easyRoad.getActiveSessions().containsKey(event.getPlayer())) {
            Session s = easyRoad.getActiveSessions().get(event.getPlayer());
            if (s.isEnabled()) {
                if (event.getItem() != null) {
                    if (event.getItem().getType() == Material.WOODEN_SWORD) {
                        easyRoad.getLogger().log(Level.FINE, "Click detected!");
                        easyRoad.getActiveSessions().get(event.getPlayer()).handleClick(event);
                    }
                }
            }
        }
    }

}
