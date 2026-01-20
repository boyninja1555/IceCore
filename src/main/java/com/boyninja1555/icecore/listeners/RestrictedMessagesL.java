package com.boyninja1555.icecore.listeners;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.IceMessage;
import com.boyninja1555.icecore.lib.IcePerm;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;
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

            if (recipient.hasPermission(IcePerm.STAFF_ROLE)) {
                recipient.sendMessage(IceMessage.get(IceMessage.HIDDEN_MESSAGE, Map.of(
                        "prefix", "%vault_prefix%",
                        "suffix", "%vault_suffix%",
                        "player", player.getName(),
                        "message", MiniMessage.miniMessage().serialize(event.message())
                ), player));
            }

            event.viewers().remove(audience);
        });
    }
}
