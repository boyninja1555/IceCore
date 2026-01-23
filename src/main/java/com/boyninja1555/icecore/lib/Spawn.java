package com.boyninja1555.icecore.lib;

import com.boyninja1555.icecore.IceCore;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Spawn {
    private final IceCore plugin;

    public Spawn(IceCore plugin) {
        this.plugin = plugin;
    }

    public void teleport(Player player) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection spawnSection = config.getConfigurationSection("spawn");

        if (spawnSection == null)
            return;

        if (player.isInsideVehicle())
            player.leaveVehicle();

        player.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY,
                PotionEffect.INFINITE_DURATION,
                255,
                true,
                false,
                false
        ));
        player.getInventory().clear();
        player.teleport(Location.deserialize(spawnSection.getValues(false)));
    }

    public void exit(Player player, Location location) {
        player.clearActivePotionEffects();
        player.teleport(location);
    }
}
