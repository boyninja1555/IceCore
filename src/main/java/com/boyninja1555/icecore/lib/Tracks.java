package com.boyninja1555.icecore.lib;

import com.boyninja1555.icecore.IceCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tracks {
    private final IceCore plugin;
    private final File file;
    private final List<Track> tracks;

    public Tracks(IceCore plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        this.tracks = new ArrayList<>();
    }

    public void newTrack(Track track) {
        tracks.add(track);
        save();
    }

    public void removeTrack(String id) {
        tracks.remove(getTrack(id));
        save();
    }

    public Track getTrack(String id) {
        List<Track> results = tracks.stream()
                .filter(t -> t.id().equals(id))
                .toList();

        if (results.isEmpty())
            return null;

        return results.getFirst();
    }

    public List<Track> getAll() {
        return tracks;
    }

    public void load() {
        if (!file.exists()) {
            save();
            return;
        }

        FileConfiguration tracksConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection tracksSection = tracksConfig.getConfigurationSection("tracks");

        if (tracksSection == null) {
            plugin.getLogger().severe("Could not load tracks! Section does not exist in " + file.getName());
            return;
        }

        tracks.clear();
        tracksSection.getKeys(false).forEach(trackId -> {
            ConfigurationSection trackSection = tracksSection.getConfigurationSection(trackId);

            if (trackSection == null)
                return;

            tracks.add(Track.deserialize(trackSection));
        });
    }

    public void save() {
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (!created)
                    throw new IOException("Please ensure IceCore has access to " + file.getName());
            }

            FileConfiguration tracksConfig = YamlConfiguration.loadConfiguration(file);
            tracksConfig.set("tracks", null);

            ConfigurationSection tracksSection = tracksConfig.createSection("tracks");
            tracks.forEach(track -> track.serializeAndPut(tracksSection));
            tracksConfig.save(file);
        } catch (IOException ex) {
            plugin.getLogger().severe("Could not save tracks! " + ex.getMessage());
        }
    }
}
