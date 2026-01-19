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
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public record AbilityL(IceCore plugin) implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();

        if (event.getState() != PlayerFishEvent.State.FISHING || item.getType() != Material.FISHING_ROD)
            return;

        PersistentDataContainerView data = item.getPersistentDataContainer();

        if (!data.has(AbilityKey.get()))
            return;

        Ability ability = IceCore.abilities().get(data.get(AbilityKey.get(), PersistentDataType.STRING));

        if (ability == null)
            return;

        event.setCancelled(true);
        ability.execute(player);
        player.setCooldown(Abilities.toItem(ability), ability.cooldownTicks());
    }
}
