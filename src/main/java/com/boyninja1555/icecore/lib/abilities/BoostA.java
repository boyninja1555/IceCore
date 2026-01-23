package com.boyninja1555.icecore.lib.abilities;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.abilities.lib.Ability;
import com.boyninja1555.icecore.lib.abilities.lib.AbilityParticles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BoostA extends Ability {

    @Override
    public int slot() {
        return 6;
    }

    @Override
    public String id() {
        return "boost";
    }

    @Override
    public Component name() {
        return Component.text("Upward Boost", NamedTextColor.LIGHT_PURPLE);
    }

    @Override
    public int cooldownTicks() {
        return 20 * 2; // 2 seconds
    }

    @Override
    public void execute(IceCore plugin, Player player) {
        if (!(player.getVehicle() instanceof Boat boat) || !boat.isOnGround())
            return;

        float radius = 1.5f;
        float particleGap = .5f;
        for (float r = 0; r < radius; r += .25f)
            AbilityParticles.builder()
                    .radius(r)
                    .particleGap(particleGap)
                    .buildAndSpawn(player.getLocation(), Particle.EXPLOSION);

        player.getWorld().playSound(
                player.getLocation(),
                "entity.wind_charge.wind_burst",
                1f,
                1f
        );

        Vector velocity = boat.getVelocity();
        Bukkit.getScheduler().runTask(plugin, () -> boat.setVelocity(new Vector(velocity.getX(), velocity.getY() + 1f, velocity.getZ())));
    }
}
