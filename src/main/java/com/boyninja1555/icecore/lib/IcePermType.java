package com.boyninja1555.icecore.lib;

public enum IcePermType {
    COMMAND("commands"),
    ROLE("roles");

    private final String identifier;

    IcePermType(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }
}
