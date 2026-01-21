package com.boyninja1555.icecore.lib.abilities.lib;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class AbilityParticles {
    private float radius = 1f;
    private float particleGap = .1f;

    public AbilityParticles radius(float radius) {
        this.radius = radius;
        return this;
    }

    public AbilityParticles particleGap(float particleGap) {
        this.particleGap = particleGap;
        return this;
    }

    public void buildAndSpawn(Location center, Particle particle) {
        double circumference = 2 * Math.PI * radius;
        int particleCount = (int) Math.ceil(circumference / particleGap);
        double angleStep = 2 * Math.PI / particleCount;
        for (int i = 0; i < particleCount; i++) {
            double angle = i * angleStep;
            center.getWorld().spawnParticle(
                    particle,
                    center.getX() + Math.cos(angle) * radius,
                    center.getY(),
                    center.getZ() + Math.sin(angle) * radius,
                    1
            );
        }
    }

    public void buildAndSpawn(World world, double x, double y, double z, Particle particle) {
        buildAndSpawn(new Location(world, x, y, z), particle);
    }

    public void buildAndSpawn(World world, float x, float y, float z, Particle particle) {
        buildAndSpawn(new Location(world, x, y, z), particle);
    }

    public static AbilityParticles builder() {
        return new AbilityParticles();
    }
}
