package com.boyninja1555.icecore.commands;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.*;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.List;

public class TrackC extends BaseC {
    private static final Random RANDOM = new Random();
    private final String[] SUBCOMMANDS = new String[]{
            "new",
            "remove",
            "edit",
    };

    public TrackC(IceCore plugin, String mainLabel) {
        super(plugin, mainLabel);
    }

    private void teleportTrack(Player player, String trackId) {
        Track track = IceCore.tracks().getTrack(trackId);

        if (track == null) {
            player.sendMessage(IceMessage.get(IceMessage.TRACK_NOT_FOUND));
            return;
        }

        if (track.spawns().isEmpty()) {
            player.sendMessage(IceMessage.get(IceMessage.TRACK_MISSING_SPAWNS));
            return;
        }

        if (track.usesOBU() && !IceCore.obu().hasOBU(player)) {
            player.sendMessage(IceMessage.get(IceMessage.TRACK_USES_OBU));
            return;
        }

        int randomInt = RANDOM.nextInt(0, track.spawns().size());
        Location randomSpawn = track.spawns().get(randomInt);
        Boat boat = (Boat) randomSpawn.getWorld().spawnEntity(randomSpawn, EntityType.OAK_CHEST_BOAT);
        player.leaveVehicle();
        IceCore.spawn().exit(player, new Location(
                randomSpawn.getWorld(),
                randomSpawn.x(),
                100f,
                randomSpawn.z(),
                randomSpawn.getYaw(),
                randomSpawn.getPitch()
        ));
        boat.setInvulnerable(true);
        boat.addPassenger(player);
        IceCore.tracksLastJoined().set(player, track);
        player.sendMessage(IceMessage.get(IceMessage.TRACK_TELEPORTED, Map.of("track", track.name())));
    }

    @Override
    public void execute(CommandSourceStack source, String @NonNull [] args) {
        if (!(source.getSender() instanceof Player player)) {
            source.getSender().sendMessage(IceMessage.get(IceMessage.YOU_NOT_PLAYER));
            return;
        }

        boolean isStaff = player.hasPermission(IcePerm.STAFF_ROLE);
        if (args.length == 0) {
            player.sendMessage(IceMessage.get(IceMessage.USAGE, Map.of("usage", "/" + mainLabel + " <track" + (isStaff ? "|action" : "") + ">" + (isStaff ? " <track-id?>" : ""))));
            return;
        }

        switch (args[0]) {
            case "new" -> {
                if (!isStaff) {
                    teleportTrack(player, args[0]);
                    return;
                }

                if (args.length < 4) {
                    player.sendMessage(IceMessage.get(IceMessage.USAGE, Map.of("usage", "/" + mainLabel + " new <uses-obu> <track-id> <track-name>")));
                    return;
                }

                if (!List.of("true", "false").contains(args[1])) {
                    player.sendMessage(IceMessage.get(IceMessage.USAGE_ERROR, Map.of(
                            "usage", "/" + mainLabel + " new <uses-obu> <track-id> <track-name>",
                            "error", "<uses-obu> must be boolean (true/false)"
                    )));
                    return;
                }

                String trackName = String.join(" ", Arrays.asList(args).subList(3, args.length));
                IceCore.tracks().newTrack(new Track(
                        args[2],
                        trackName,
                        new ArrayList<>(),
                        Boolean.getBoolean(args[1])
                ));
                player.sendMessage(IceMessage.get(IceMessage.TRACK_CREATED, Map.of("track", trackName)));
            }

            case "remove" -> {
                if (!isStaff) {
                    teleportTrack(player, args[0]);
                    return;
                }

                if (args.length < 2) {
                    player.sendMessage(IceMessage.get(IceMessage.USAGE, Map.of("usage", "/" + mainLabel + " remove <track-id>")));
                    return;
                }

                String trackId = args[1];

                if (IceCore.tracks().getTrack(trackId) == null) {
                    player.sendMessage(IceMessage.get(IceMessage.TRACK_NOT_FOUND));
                    return;
                }

                IceCore.tracks().removeTrack(trackId);
                player.sendMessage(IceMessage.get(IceMessage.TRACK_REMOVED));
            }

            case "edit" -> {
                if (!isStaff) {
                    teleportTrack(player, args[0]);
                    return;
                }

                if (args.length < 3) {
                    player.sendMessage(IceMessage.get(IceMessage.USAGE, Map.of("usage", "/" + mainLabel + " edit <track-id> <property> <value?>")));
                    return;
                }

                Track track = IceCore.tracks().getTrack(args[1]);

                if (track == null) {
                    player.sendMessage(IceMessage.get(IceMessage.TRACK_NOT_FOUND));
                    return;
                }

                if (args[2].equals("addspawn")) {
                    track.spawns().add(player.getLocation());
                    IceCore.tracks().save();
                    player.sendMessage(IceMessage.get(IceMessage.TRACK_SPAWN_ADDED));
                    return;
                }

                if (args.length < 4) {
                    player.sendMessage(IceMessage.get(IceMessage.USAGE, Map.of("usage", "/" + mainLabel + " edit <track-id> <property> <value>")));
                    return;
                }

                String property = args[2];
                String value = args[3];

                switch (property) {
                    case "id" -> {
                        track.id(value);
                        player.sendMessage(IceMessage.get(IceMessage.TRACK_PROPERTY_SET, Map.of("property", property)));
                    }

                    case "name" -> {
                        track.name(String.join(" ", Arrays.asList(args).subList(3, args.length)));
                        player.sendMessage(IceMessage.get(IceMessage.TRACK_PROPERTY_SET, Map.of("property", property)));
                    }

                    case "uses-obu" -> {
                        if (!List.of("true", "false").contains(value)) {
                            player.sendMessage(Component.join(
                                    JoinConfiguration.separator(Component.newline()),
                                    IceMessage.getAsLines(IceMessage.USAGE_ERROR, Map.of(
                                            "usage", "/" + mainLabel + " edit <track-id> <property> <value>",
                                            "error", "<value> must be a boolean (true/false)"
                                    ))
                            ));
                            return;
                        }

                        track.usesOBU(Boolean.getBoolean(value));
                        player.sendMessage(IceMessage.get(IceMessage.TRACK_PROPERTY_SET, Map.of("property", property)));
                    }

                    default -> player.sendMessage(IceMessage.get(IceMessage.UNKNOWN_TRACK_PROPERTY, Map.of("property", property)));
                }

                IceCore.tracks().save();
            }

            default -> teleportTrack(player, args[0]);
        }
    }

    @Override
    @NotNull
    public Collection<String> suggest(CommandSourceStack source, String[] args) {
        List<Track> tracks = IceCore.tracks().getAll();
        List<String> trackIds = tracks.stream()
                .map(Track::id)
                .toList();

        boolean isStaff = source.getSender().hasPermission(IcePerm.STAFF_ROLE);
        if (args.length == 0)
            if (isStaff) {
                List<String> subCommands = new ArrayList<>(trackIds);
                subCommands.addAll(Arrays.asList(SUBCOMMANDS));
                return subCommands;
            } else
                return trackIds;

        if (args.length == 1)
            if (isStaff) {
                List<String> subCommands = new ArrayList<>(trackIds);
                subCommands.addAll(Arrays.asList(SUBCOMMANDS));
                return subCommands.stream()
                        .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                        .toList();
            } else
                return trackIds.stream()
                        .filter(t -> t.toLowerCase().startsWith(args[0].toLowerCase()))
                        .toList();

        if (args.length == 2)
            return switch (args[0]) {
                case "new" -> List.of("<uses-obu>");

                case "remove", "edit" -> trackIds.stream()
                        .filter(t -> t.toLowerCase().startsWith(args[1].toLowerCase()))
                        .toList();

                default -> List.of();
            };

        if (args.length == 3)
            return switch (args[0]) {
                case "new" -> List.of("<track-id>");

                case "edit" -> List.of("id", "name", "addspawn", "uses-obu");

                default -> List.of();
            };

        if (args.length == 4)
            return switch (args[0]) {
                case "new" -> List.of("<track-name>");

                case "edit" -> {
                    Track track = IceCore.tracks().getTrack(args[1]);
                    yield switch (args[3]) {
                        case "id" -> List.of(track.id());

                        case "name" -> List.of(track.name());

                        case "uses-obu" -> List.of("true", "false");

                        default -> List.of();
                    };
                }

                default -> List.of();
            };

        return List.of();
    }
}
