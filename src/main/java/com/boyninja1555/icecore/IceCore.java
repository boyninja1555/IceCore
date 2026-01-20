package com.boyninja1555.icecore;

import com.boyninja1555.icecore.commands.AnnounceC;
import com.boyninja1555.icecore.commands.CoreC;
import com.boyninja1555.icecore.commands.TrackC;
import com.boyninja1555.icecore.lib.*;
import com.boyninja1555.icecore.lib.abilities.TntA;
import com.boyninja1555.icecore.lib.abilities.lib.Abilities;
import com.boyninja1555.icecore.lib.abilities.lib.AbilityKey;
import com.boyninja1555.icecore.lib.obu.OpenBoatUtilsBridge;
import com.boyninja1555.icecore.listeners.AbilityL;
import com.boyninja1555.icecore.listeners.RaceJoinLeaver;
import com.boyninja1555.icecore.listeners.JoinLeaveL;
import com.boyninja1555.icecore.listeners.RestrictedMessagesL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class IceCore extends JavaPlugin {
    private static IceRP rp;
    private static CommandReg commands;
    private static Tracks tracks;
    private static Spawn spawn;
    private static Abilities abilities;
    private static OpenBoatUtilsBridge obu;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        IcePerm.init("icecore");
        IceMessage.init(this);
        AbilityKey.init(namespace());
        FileConfiguration config = getConfig();
        rp = new IceRP(
                config.getString("resource-pack", ""),
                config.getString("resource-pack-hash", ""),
                IceMessage.get(IceMessage.RESOURCE_PACK_PROMPT),
                config.getBoolean("resource-pack-required", false)
        );
        commands = new CommandReg(this, List.of(
                new CommandDef(List.of("core"), "Manages the IceCore plugin", CoreC.class),
                new CommandDef(List.of("announce", "announcement", "broadcast", "bc"), "Sends a public announcement", AnnounceC.class),
                new CommandDef(List.of("track", "t"), "Teleports you to a track", TrackC.class)
        ));
        commands.registerAll();
        tracks = new Tracks(this, new File(getDataFolder(), "tracks.yml"));
        tracks.load();
        spawn = new Spawn(this);
        abilities = new Abilities(this);
        abilities.create(new TntA());
        obu = new OpenBoatUtilsBridge(this);

        getServer().getMessenger().registerIncomingPluginChannel(this, OpenBoatUtilsBridge.CHANNEL, obu);
        getServer().getMessenger().registerOutgoingPluginChannel(this, OpenBoatUtilsBridge.CHANNEL);
        getServer().getPluginManager().registerEvents(obu, this);

        JoinLeaveL joinLeaveL = new JoinLeaveL(this);
        getServer().getPluginManager().registerEvents(joinLeaveL, this);

        RestrictedMessagesL restrictedMessagesL = new RestrictedMessagesL(this);
        getServer().getPluginManager().registerEvents(restrictedMessagesL, this);

        RaceJoinLeaver raceJoinLeaver = new RaceJoinLeaver(this);
        getServer().getPluginManager().registerEvents(raceJoinLeaver, this);

        AbilityL abilityL = new AbilityL(this);
        getServer().getPluginManager().registerEvents(abilityL, this);
    }

    public static IceRP rp() {
        return rp;
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

    public static Abilities abilities() {
        return abilities;
    }

    public static OpenBoatUtilsBridge obu() {
        return obu;
    }
}
