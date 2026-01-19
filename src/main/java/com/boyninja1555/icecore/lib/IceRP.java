package com.boyninja1555.icecore.lib;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

public record IceRP(String url, Component prompt, boolean required) {

    public void apply(Player player) {
        player.setResourcePack(
                UUID.randomUUID(),
                url,
                new byte[20],
                prompt,
                required
        );
    }
}
