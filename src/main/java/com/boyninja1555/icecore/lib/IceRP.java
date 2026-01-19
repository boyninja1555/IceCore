package com.boyninja1555.icecore.lib;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

public record IceRP(String url, String hash, Component prompt, boolean required) {

    public void apply(Player player) {
        player.setResourcePack(
                UUID.randomUUID(),
                url + "?abc=" + UUID.randomUUID(),
                sha1FromHex(hash),
                prompt,
                required
        );
    }

    private static byte[] sha1FromHex(String hex) {
        int len = hex.length();
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
            out[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);

        return out;
    }
}
