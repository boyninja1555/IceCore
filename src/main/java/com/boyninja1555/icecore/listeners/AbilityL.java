package com.boyninja1555.icecore.listeners;

import com.boyninja1555.icecore.IceCore;
import com.boyninja1555.icecore.lib.abilities.lib.Abilities;
import com.boyninja1555.icecore.lib.abilities.lib.Ability;
import com.boyninja1555.icecore.lib.abilities.lib.AbilityKey;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public record AbilityL(IceCore plugin) implements Listener {

    private void useAbility(Player player, ItemStack item, Runnable preUse, Runnable onFail) {
        PersistentDataContainerView data = item.getPersistentDataContainer();

        if (!data.has(AbilityKey.get())) {
            onFail.run();
            return;
        }

        Ability ability = IceCore.abilities().get(data.get(AbilityKey.get(), PersistentDataType.STRING));

        if (ability == null) {
            onFail.run();
            return;
        }

        preUse.run();
        ability.execute(plugin, player);
        player.setCooldown(Abilities.toItem(ability), ability.cooldownTicks());
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();

        if (item.getType() != Material.FISHING_ROD)
            return;

        useAbility(
                player,
                item,
                () -> event.setCancelled(true),
                () -> {}
        );
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null || !item.getType().equals(Material.FISHING_ROD))
            return;

        useAbility(
                player,
                item,
                () -> event.setCancelled(true),
                () -> event.setCancelled(false)
        );
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();

        if (!event.getState().equals(PlayerFishEvent.State.FISHING) || !item.getType().equals(Material.FISHING_ROD))
            return;

        useAbility(
                player,
                item,
                () -> event.setCancelled(true),
                () -> event.setCancelled(false)
        );
    }
}
