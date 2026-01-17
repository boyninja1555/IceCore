package com.boyninja1555.icecore.listeners;

import com.boyninja1555.icecore.IceCore;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public record RestrictedMessagesL(IceCore plugin) implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Set.copyOf(event.viewers()).forEach(audience -> {
            if (!(audience instanceof Player recipient))
                return;

            if (recipient.getWorld().getName().equals(player.getWorld().getName()))
                return;

            event.viewers().remove(audience);
        });
    }
}
