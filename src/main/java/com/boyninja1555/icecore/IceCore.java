package com.boyninja1555.icecore;

import com.boyninja1555.icecore.commands.AnnounceC;
import com.boyninja1555.icecore.commands.CoreC;
import com.boyninja1555.icecore.commands.TrackC;
import com.boyninja1555.icecore.lib.*;
import com.boyninja1555.icecore.listeners.RaceJoinLeaver;
import com.boyninja1555.icecore.listeners.RestrictedJoinLeaveMessagesL;
import com.boyninja1555.icecore.listeners.RestrictedMessagesL;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class IceCore extends JavaPlugin {
    private static CommandReg commands;
    private static Tracks tracks;
    private static Spawn spawn;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        IcePerm.init("icecore");
        IceMessage.init(this);
        commands = new CommandReg(this, List.of(
                new CommandDef(List.of("core"), "Manages the IceCore plugin", CoreC.class),
                new CommandDef(List.of("announce", "announcement", "broadcast", "bc"), "Sends a public announcement", AnnounceC.class),
                new CommandDef(List.of("track", "t"), "Teleports you to a track", TrackC.class)
        ));
        commands.registerAll();
        tracks = new Tracks(this, new File(getDataFolder(), "tracks.yml"));
        tracks.load();
        spawn = new Spawn(this);

        RestrictedJoinLeaveMessagesL restrictedJoinLeaveMessagesL = new RestrictedJoinLeaveMessagesL(this);
        getServer().getPluginManager().registerEvents(restrictedJoinLeaveMessagesL, this);

        RestrictedMessagesL restrictedMessagesL = new RestrictedMessagesL(this);
        getServer().getPluginManager().registerEvents(restrictedMessagesL, this);

        RaceJoinLeaver raceJoinLeaver = new RaceJoinLeaver(this);
        getServer().getPluginManager().registerEvents(raceJoinLeaver, this);
    }

    public static CommandReg commands() {
        return commands;
    }

    public static Tracks tracks() {
        return tracks;
    }

    public static Spawn spawn() {
        return spawn;
    }
}
