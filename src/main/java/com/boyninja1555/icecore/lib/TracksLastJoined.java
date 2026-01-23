package com.boyninja1555.icecore.lib;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TracksLastJoined {
    private final Map<UUID, Track> tracksLastJoined;

    public TracksLastJoined() {
        this.tracksLastJoined = new HashMap<>();
    }

    public void set(UUID player, Track track) {
        tracksLastJoined.put(player, track);
    }

    public void set(Player player, Track track) {
        set(player.getUniqueId(), track);
    }

    public Track get(UUID player) {
        Track result = tracksLastJoined.get(player);

        if (result == null)
            return new Track("unknown", "Unknown", new ArrayList<>(), false);

        return result;
    }

    public Track get(Player player) {
        return get(player.getUniqueId());
    }
}
