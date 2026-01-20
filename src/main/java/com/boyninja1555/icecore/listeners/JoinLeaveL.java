package com.boyninja1555.icecore.listeners;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.IceMessage;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;

public record JoinLeaveL(IceCore plugin) implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setInvulnerable(true);
        IceCore.rp().apply(player);
        IceCore.spawn().teleport(player);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!IceCore.obu().hasOBU(player))
                player.sendMessage(IceMessage.get(IceMessage.DOWNLOAD_OBU));
        }, 60L); // ~3 seconds

        if (!player.hasPlayedBefore()) {
            Sound sound = Sound.sound(
                    Key.key("minecraft", plugin.getConfig().getString("joined-new-sound", "entity.villager.no")),
                    Sound.Source.UI,
                    1f,
                    1f
            );
            event.joinMessage(IceMessage.get(IceMessage.JOINED_NEW, Map.of("player", player.getName())));
            Bukkit.getOnlinePlayers().forEach(p -> p.playSound(sound));
            return;
        }

        event.joinMessage(IceMessage.get(IceMessage.JOINED_SERVER, Map.of("player", player.getName())));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.quitMessage(IceMessage.get(IceMessage.QUIT_SERVER, Map.of("player", event.getPlayer().getName())));
    }
}
