package com.jbuelow.mc.easyroad.event;

import com.jbuelow.mc.easyroad.EasyRoad;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventPlayerDisconnect implements Listener {

    private final EasyRoad easyRoad;

    public EventPlayerDisconnect(EasyRoad easyRoad) {
        this.easyRoad = easyRoad;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (easyRoad.getActiveSessions().containsKey(event.getPlayer())) {
            easyRoad.getActiveSessions().get(event.getPlayer()).disable();
            easyRoad.getActiveSessions().remove(event.getPlayer());
        }
    }

}
