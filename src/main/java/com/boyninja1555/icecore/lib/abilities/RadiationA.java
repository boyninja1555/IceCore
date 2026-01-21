package com.boyninja1555.icecore.lib.abilities;

import com.boyninja1555.icecore.lib.abilities.lib.Ability;
import com.boyninja1555.icecore.lib.abilities.lib.AbilityParticles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RadiationA extends Ability {

    @Override
    public String id() {
        return "radiation";
    }

    @Override
    public Component name() {
        return Component.text("Radiation Blast", NamedTextColor.GOLD);
    }

    @Override
    public int cooldownTicks() {
        return 20 * 5; // 5 seconds
    }

    @Override
    public void execute(Player player) {
        float radius = 15f;
        float particleGap = .5f;
        for (float r = 0; r < radius; r += .25f)
            AbilityParticles.builder()
                    .radius(r)
                    .particleGap(particleGap)
                    .buildAndSpawn(player.getLocation(), Particle.SOUL);

        player.getWorld()
                .getNearbyPlayers(player.getLocation(), radius, radius, radius)
                .stream()
                .filter(p -> !p.getUniqueId().equals(player.getUniqueId()))
                .toList()
                .forEach(victim -> victim.addPotionEffect(new PotionEffect(
                        PotionEffectType.NAUSEA,
                        20 * 10, // 10 seconds
                        255
                )));

        player.getWorld().playSound(
                player.getLocation(),
                "entity.elder_guardian.curse",
                1f,
                .1f
        );
    }
}
