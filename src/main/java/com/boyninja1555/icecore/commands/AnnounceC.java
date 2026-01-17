package com.boyninja1555.icecore.commands;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.BaseC;
import com.boyninja1555.icecore.lib.IceMessage;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class AnnounceC extends BaseC {

    public AnnounceC(IceCore plugin, String mainLabel) {
        super(plugin, mainLabel);
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        FileConfiguration config = plugin.getConfig();
        CommandSender user = source.getSender();

        if (args.length == 0) {
            user.sendMessage(IceMessage.get(IceMessage.USAGE, Map.of("usage", "/" + mainLabel + " <message>")));
            return;
        }

        String message = String.join(" ", args);
        Sound sound = Sound.sound(
                Key.key("minecraft", config.getString("announcement-sound", "entity.villager.no")),
                Sound.Source.UI,
                1f,
                1f
        );
        Bukkit.broadcast(Component.join(
                JoinConfiguration.separator(Component.newline()),
                IceMessage.getAsLines(IceMessage.ANNOUNCEMENT, Map.of("announcement", message))
        ));
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(sound));
    }
}
