package com.boyninja1555.icecore.lib.obu;

import com.boyninja1555.icecore.IceCore;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jspecify.annotations.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OpenBoatUtilsBridge implements PluginMessageListener, Listener {
    public static final String CHANNEL = "openboatutils:settings";
    private final IceCore plugin;
    private final Set<UUID> obuPlayers;

    public OpenBoatUtilsBridge(IceCore plugin) {
        this.plugin = plugin;
        this.obuPlayers = new HashSet<>();
    }

    @Override
    public void onPluginMessageReceived(@NonNull String channel, @NonNull Player player, byte @NonNull [] message) {
        obuPlayers.add(player.getUniqueId());
    }

    public boolean hasOBU(Player p) {
        return obuPlayers.contains(p.getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID player = event.getPlayer().getUniqueId();

        if (!obuPlayers.contains(player))
            return;

        obuPlayers.remove(player);
    }

    /**
     * Packet sending
     * - Custom
     * - Generic
     */

    // Custom senders

    public void sendSlippery(Player player, float value, String blocks) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeShort(OBUClientboundPackets.SET_DEFAULT_SLIPPERINESS.value());
        out.writeFloat(value);
        out.writeUTF(blocks);
        player.sendPluginMessage(plugin, CHANNEL, out.toByteArray());
    }

    // Generic senders

    public void send(Player player, OBUClientboundPackets type) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeShort(type.value());
        player.sendPluginMessage(plugin, CHANNEL, out.toByteArray());
    }

    public void sendFloat(Player player, OBUClientboundPackets type, float value) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeShort(type.value());
        out.writeFloat(value);
        player.sendPluginMessage(plugin, CHANNEL, out.toByteArray());
    }

    public void sendBoolean(Player player, OBUClientboundPackets type, boolean value) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeShort(type.value());
        out.writeBoolean(value);
        player.sendPluginMessage(plugin, CHANNEL, out.toByteArray());
    }
}
