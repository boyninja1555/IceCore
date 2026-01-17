package com.boyninja1555.icecore.lib;

import com.boyninja1555.icecore.IceCore;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public record CommandReg(IceCore plugin, List<CommandDef> commands) {

    public void registerAll() {
        commands.forEach(this::register);
    }

    private void register(@NotNull CommandDef command) {
        if (command.labels().isEmpty()) {
            plugin.getLogger().warning("Could not register command! Please ensure all have at least 1 label");
            return;
        }

        try {
            List<String> aliases = new ArrayList<>(command.labels());
            aliases.removeFirst();
            plugin.registerCommand(command.labels().getFirst(), command.help(), aliases, command.command()
                    .getConstructor(IceCore.class, String.class)
                    .newInstance(plugin, command.labels().getFirst()));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            plugin.getLogger().severe("Could not register command /" + command.labels().getFirst() + "! Error found while registering command class. " + ex.getMessage());
        }
    }
}
