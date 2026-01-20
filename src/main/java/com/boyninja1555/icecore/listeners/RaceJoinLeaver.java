package com.boyninja1555.icecore.listeners;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.IceMessage;
import com.boyninja1555.icecore.lib.abilities.lib.Abilities;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

public record RaceJoinLeaver(IceCore plugin) implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (!player.isInsideVehicle())
            return;

        event.setCancelled(true);
        player.sendMessage(IceMessage.get(IceMessage.GET_OUT_OF_BOAT_FOR_COMMAND));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (!(event.getVehicle() instanceof Boat) || !(event.getEntered() instanceof Player player))
            return;

        player.getInventory().clear();
        IceCore.abilities().getAll().forEach(ability -> {
            ItemStack item = Abilities.toItem(ability);
            player.give(item);
        });
    }

    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        if (!(event.getVehicle() instanceof Boat boat) || !(event.getExited() instanceof Player player))
            return;

        player.leaveVehicle();
        boat.remove();
        IceCore.spawn().teleport(player);
    }
}
