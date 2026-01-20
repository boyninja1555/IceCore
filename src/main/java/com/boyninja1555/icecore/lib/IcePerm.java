package com.boyninja1555.icecore.lib;

public class IcePerm {
    public static final String STAFF_ROLE = IcePerm.get(IcePermType.ROLE, "staff");
    private static String identifier = "unnamed";

    public static void init(String identifier) {
        IcePerm.identifier = identifier;
    }

    public static String get(IcePermType type, String label) {
        return identifier + "." + type.identifier() + "." + label;
    }
}
