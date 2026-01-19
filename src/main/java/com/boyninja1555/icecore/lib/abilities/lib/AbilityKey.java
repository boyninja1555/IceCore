package com.boyninja1555.icecore.lib.abilities.lib;

import org.bukkit.NamespacedKey;

public class AbilityKey {
    private static String namespace = "unknown";

    public static void init(String namespace) {
        AbilityKey.namespace = namespace;
    }

    public static NamespacedKey get() {
        return new NamespacedKey(namespace, "ability");
    }
}
