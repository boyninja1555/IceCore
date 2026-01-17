package com.boyninja1555.icecore.commands;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.BaseC;
import com.boyninja1555.icecore.lib.IceMessage;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CoreC extends BaseC {
    private final String[] SUBCOMMANDS = new String[]{
            "reload",
            "regenerate-config",
    };

    public CoreC(IceCore plugin, String mainLabel) {
        super(plugin, mainLabel);
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender user = source.getSender();

        if (args.length == 0) {
            user.sendMessage(IceMessage.get(IceMessage.USAGE, Map.of("usage", "/" + mainLabel + " <action>")));
            return;
        }

        switch (args[0]) {
            case "reload" -> {
                plugin.saveDefaultConfig();
                plugin.reloadConfig();
                IceCore.tracks().load();
                user.sendMessage(IceMessage.get(IceMessage.RELOADED));
            }

            case "regenerate-config" -> {
                File configFile = new File(plugin.getDataFolder(), "config.yml");

                if (configFile.exists()) {
                    boolean deleted = configFile.delete();
                    if (!deleted) {
                        plugin.getLogger().severe("Could not regenerate " + configFile.getName() + "! Please ensure IceCore has access");
                        return;
                    }
                }

                plugin.saveDefaultConfig();
                plugin.reloadConfig();
                user.sendMessage(IceMessage.get(IceMessage.CONFIG_REGENERATED));
            }

            default -> user.sendMessage(IceMessage.get(IceMessage.USAGE, Map.of("usage", "/" + mainLabel + " <action>")));
        }
    }

    @Override
    @NotNull
    public Collection<String> suggest(@NotNull CommandSourceStack source, String[] args) {
        List<String> subCommands = Arrays.asList(SUBCOMMANDS);

        if (args.length == 0)
            return subCommands;

        if (args.length == 1)
            return subCommands.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();

        return List.of();
    }
}
