package com.boyninja1555.icecore.lib;

import com.boyninja1555.icecore.IceCore;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public record AdventureForcerL(IceCore plugin) implements Listener {

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
    }
}
