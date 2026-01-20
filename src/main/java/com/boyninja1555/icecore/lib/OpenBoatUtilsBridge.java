package com.boyninja1555.icecore.lib;

import com.boyninja1555.icecore.IceCore;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class OpenBoatUtilsBridge implements PluginMessageListener {
    public static final String CHANNEL = "openboatutils:settings";
    private final IceCore plugin;

    public OpenBoatUtilsBridge(IceCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    }

    public void sendStep(Player p, float height) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeShort(1);
        out.writeFloat(height);
        p.sendPluginMessage(plugin, CHANNEL, out.toByteArray());
    }

    public void sendSlippery(Player p, float value, String blocks) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeShort(3);
        out.writeFloat(value);
        out.writeUTF(blocks);
        p.sendPluginMessage(plugin, CHANNEL, out.toByteArray());
    }
}
