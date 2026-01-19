package com.boyninja1555.icecore.lib;

import com.boyninja1555.icecore.IceCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public enum IceMessage {
    RESOURCE_PACK_PROMPT,

    USAGE,
    YOU_NOT_PLAYER,
    OTHER_NOT_PLAYER,
    TRACK_NOT_FOUND,
    TRACK_MISSING_SPAWNS,
    UNKNOWN_TRACK_PROPERTY,
    GET_OUT_OF_BOAT_FOR_COMMAND,
    COOLDOWN,

    JOINED_NEW,
    JOINED_SERVER,
    QUIT_SERVER,
    JOINED_TRACK,
    QUIT_TRACK,

    RELOADED,
    CONFIG_REGENERATED,
    TRACK_CREATED,
    TRACK_REMOVED,
    TRACK_PROPERTY_SET,
    TRACK_SPAWN_ADDED,

    TRACK_TELEPORTED,

    ANNOUNCEMENT;

    private static final Component DEFAULT = Component.text("Please fix the IceCore config.yml! Regenerating is also an option", NamedTextColor.RED);
    private static IceCore plugin;
    private static String root;

    public static void init(IceCore plugin, @Nullable String root) {
        IceMessage.plugin = plugin;
        IceMessage.root = root;
    }

    public static void init(IceCore plugin) {
        IceMessage.plugin = plugin;
    }

    public static Component get(IceMessage message, Map<String, String> placeholders) {
        FileConfiguration config = plugin.getConfig();
        String path = message.toString().toLowerCase().replaceAll("_", "-");

        if (root != null)
            path = root + "." + path;

        return replacePlaceholders(config.getRichMessage(path, DEFAULT), placeholders);
    }

    public static Component get(IceMessage message) {
        return IceMessage.get(message, new HashMap<>());
    }

    public static List<Component> getAsLines(IceMessage message, Map<String, String> placeholders) {
        FileConfiguration config = plugin.getConfig();
        String path = message.toString().toLowerCase().replace("_", "-");

        if (root != null)
            path = root + "." + path;

        List<?> rawList = config.getList(path, List.of(DEFAULT));
        List<Component> result = new ArrayList<>(rawList.size());

        for (int i = 0; i < rawList.size(); i++) {
            Object item = rawList.get(i);
            Component comp;

            if (item instanceof String) {
                String fakePath = "cache-" + path + "." + i;
                config.set(fakePath, item);
                comp = config.getRichMessage(fakePath, DEFAULT);
                config.set(fakePath, null);
            } else if (item instanceof Component)
                comp = (Component) item;
            else
                comp = DEFAULT;

            result.add(replacePlaceholders(comp, placeholders));
        }

        return result;
    }

    public static List<Component> getAsLines(IceMessage message) {
        return IceMessage.getAsLines(message, new HashMap<>());
    }

    private static Component replacePlaceholders(Component old, Map<String, String> placeholders) {
        final Component[] result = new Component[]{old};
        placeholders.forEach((id, replacement) -> result[0] = result[0].replaceText(TextReplacementConfig
                .builder()
                .match(Pattern.quote("{" + id + "}"))
                .replacement(replacement)
                .build()
        ));
        return result[0];
    }
}
