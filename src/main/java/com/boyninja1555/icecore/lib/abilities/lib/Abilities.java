package com.boyninja1555.icecore.lib.abilities.lib;

import com.boyninja1555.icecore.IceCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Abilities {
    private static IceCore plugin;
    private final List<Ability> abilities;

    public Abilities(IceCore plugin) {
        Abilities.plugin = plugin;
        this.abilities = new ArrayList<>();
    }

    public List<Ability> getAll() {
        return new ArrayList<>(abilities);
    }

    public Ability get(String id) {
        return abilities.stream()
                .filter(a -> a.id().equals(id))
                .toList()
                .getFirst();
    }

    public void create(Ability ability) {
        abilities.add(ability);
    }

    public static ItemStack toItem(Ability ability) {
        FileConfiguration config = plugin.getConfig();
        ItemStack item = ItemStack.of(Material.FISHING_ROD);
        item.editPersistentDataContainer(d -> d.set(AbilityKey.get(), PersistentDataType.STRING, ability.id()));
        item.editMeta(meta -> {
            meta.itemName(ability.name());
            meta.setItemModel(NamespacedKey.fromString(
                    config.getString("resource-pack-namespace", "unknown")
                            + ":"
                            + ability.id()
            ));
        });
        return item;
    }
}
