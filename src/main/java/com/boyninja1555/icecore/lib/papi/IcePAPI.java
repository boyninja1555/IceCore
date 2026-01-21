package com.boyninja1555.icecore.lib.papi;

import com.boyninja1555.icecore.IceCore;
import io.papermc.paper.plugin.configuration.PluginMeta;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class IcePAPI extends PlaceholderExpansion {
    private final PluginMeta meta;
    private final List<IcePlaceholder> placeholders;

    public IcePAPI(IceCore plugin) {
        this.meta = plugin.getPluginMeta();
        this.placeholders = new ArrayList<>();
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "icecore";
    }

    @Override
    @NotNull
    public String getAuthor() {
        return String.join(", ", meta.getAuthors());
    }

    @Override
    @NotNull
    public String getVersion() {
        return meta.getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        for (IcePlaceholder placeholder : placeholders) {
            if (!placeholder.identifier().equals(identifier))
                continue;

            return placeholder.get(player);
        }

        return null;
    }

    public void registerPlaceholder(Class<? extends IcePlaceholder> placeholder) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        placeholders.add(placeholder.getDeclaredConstructor().newInstance());
    }
}
