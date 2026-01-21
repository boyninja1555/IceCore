package com.boyninja1555.icecore.lib.papi;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

public abstract class IcePlaceholder {

    @ApiStatus.OverrideOnly
    public abstract String identifier();

    @ApiStatus.OverrideOnly
    public abstract String get(Player player);
}
