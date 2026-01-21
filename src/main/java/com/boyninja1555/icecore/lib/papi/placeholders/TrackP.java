package com.boyninja1555.icecore.lib.papi.placeholders;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.papi.IcePlaceholder;
import org.bukkit.entity.Player;

public class TrackP extends IcePlaceholder {

    @Override
    public String identifier() {
        return "track";
    }

    @Override
    public String get(Player player) {
        return IceCore.tracksLastJoined().get(player).name();
    }
}
