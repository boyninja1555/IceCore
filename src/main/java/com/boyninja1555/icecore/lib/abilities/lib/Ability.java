package com.boyninja1555.icecore.lib.abilities.lib;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

public abstract class Ability {

    @ApiStatus.OverrideOnly
    public abstract int slot();

    @ApiStatus.OverrideOnly
    public abstract String id();

    @ApiStatus.OverrideOnly
    public abstract Component name();

    @ApiStatus.OverrideOnly
    public abstract int cooldownTicks();

    @ApiStatus.OverrideOnly
    public abstract void execute(Player player);
}
