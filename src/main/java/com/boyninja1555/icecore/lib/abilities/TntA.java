package com.boyninja1555.icecore.lib.abilities;

import com.boyninja1555.icecore.lib.abilities.lib.Ability;
import com.boyninja1555.icecore.lib.abilities.lib.AbilityParticles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Particle;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;

public class TntA extends Ability {

    @Override
    public String id() {
        return "tnt";
    }

    @Override
    public Component name() {
        return Component.empty()
                .append(Component.text("T", NamedTextColor.RED))
                .append(Component.text("N", NamedTextColor.WHITE))
                .append(Component.text("T", NamedTextColor.RED));
    }

    @Override
    public int cooldownTicks() {
        return 20 * 10; // 10 seconds
    }

    @Override
    public void execute(Player player) {
        float radius = 15f;
        float particleGap = 1.5f;
        for (float r = 0; r < radius; r += .25f)
            AbilityParticles.builder()
                    .radius(r)
                    .particleGap(particleGap)
                    .buildAndSpawn(player.getLocation(), Particle.EXPLOSION);

        var center = player.getLocation().toVector();
        player.getWorld()
                .getNearbyEntities(player.getLocation(), radius, radius, radius, entity -> entity instanceof Boat)
                .forEach(entity -> {
                    Boat boat = (Boat) entity;

                    if (player.getVehicle() != null && boat.getUniqueId().equals(player.getVehicle().getUniqueId()))
                        return;

                    var dir = boat.getLocation().toVector().subtract(center);
                    dir.setY(1);

                    double dist = dir.length();
                    if (dist == 0 || dist > radius)
                        return;

                    double strength = (radius - dist) / radius;
                    boat.setVelocity(dir.normalize().multiply(10 * strength));
                });

        player.getWorld().playSound(
                player.getLocation(),
                "entity.generic.explode",
                1f,
                1f
        );
    }
}
