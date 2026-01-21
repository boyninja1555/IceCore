package com.boyninja1555.icecore.lib.papi;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.papi.placeholders.TrackP;

public class IcePlaceholders {

    public static void registerPlaceholders(IceCore plugin, IcePAPI papi) {
        try {
            papi.registerPlaceholder(TrackP.class);
        } catch (Exception ex) {
            plugin.getLogger().severe("Could not register a placeholder! " + ex.getMessage());
        }
    }
}
