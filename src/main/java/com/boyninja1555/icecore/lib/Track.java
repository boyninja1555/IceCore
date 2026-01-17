package com.boyninja1555.icecore.lib;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Track {
    private String id;
    private String name;
    private List<Location> spawns;

    public Track(String id, String name, List<Location> spawns) {
        this.id = id;
        this.name = name;
        this.spawns = spawns;
    }

    public void serializeAndPut(ConfigurationSection parent) {
        ConfigurationSection section = parent.createSection(id);
        section.set("id", id);
        section.set("name", name);
        section.set("spawns", spawns.stream().map(Location::serialize).toList());
    }

    public static Track deserialize(ConfigurationSection section) {
        List<Location> spawns = new ArrayList<>();
        List<Map<?, ?>> rawSpawns = section.getMapList("spawns");
        rawSpawns.forEach(raw -> {
            HashMap<String, Object> spawn = new HashMap<>();
            raw.forEach((rawKey, rawValue) -> {
                if (!(rawKey instanceof String key) || rawValue == null)
                    return;

                spawn.put(key, rawValue);
            });
            spawns.add(Location.deserialize(spawn));
        });
        return new Track(
                section.getString("id"),
                section.getString("name"),
                spawns
        );
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public List<Location> spawns() {
        return spawns;
    }

    public void id(String id) {
        this.id = id;
    }

    public void name(String name) {
        this.name = name;
    }

    public void spawns(List<Location> spawns) {
        this.spawns = spawns;
    }
}
