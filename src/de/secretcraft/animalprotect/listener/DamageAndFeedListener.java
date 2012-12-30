package de.secretcraft.animalprotect.listener;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class DamageAndFeedListener implements Listener {
	WorldGuardPlugin wgPlugin;
	ArrayList<Short> animals = new ArrayList<Short>();

	public DamageAndFeedListener(Plugin wgPlugin) {
		this.wgPlugin = (WorldGuardPlugin) wgPlugin;
		animals.add(EntityType.SHEEP.getTypeId());
		animals.add(EntityType.CHICKEN.getTypeId());
		animals.add(EntityType.COW.getTypeId());
		animals.add(EntityType.PIG.getTypeId());
		animals.add(EntityType.WOLF.getTypeId());
		animals.add(EntityType.OCELOT.getTypeId());
		animals.add(EntityType.IRON_GOLEM.getTypeId());
		animals.add(EntityType.SNOWMAN.getTypeId());
		animals.add(EntityType.MUSHROOM_COW.getTypeId());
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
		if (e.isCancelled())
			return;
		Player player = e.getPlayer();
		
		switch (e.getRightClicked().getType()) {
		case SHEEP:
		case COW:
		case MUSHROOM_COW:
			if (player.getItemInHand().getType() != Material.WHEAT)
				return;
			break;
		case PIG:
			if (player.getItemInHand().getType() != Material.CARROT_ITEM)
				return;
			break;
		case CHICKEN:
			if (player.getItemInHand().getType() != Material.SEEDS)
				return;
			break;
		default:
			return;
		}

		if (!wgPlugin.canBuild(player, e.getRightClicked().getLocation())) {
			player.sendMessage(ChatColor.DARK_RED
					+ "You are not allowed to feed the animal in this area.");
			e.setCancelled(true);
		}
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;
		Player damager;
		try {
			damager = (Player) e.getDamager();
		} catch (Exception ex) {
			return;
		}
		
		if (!animals.contains(e.getEntity().getType().getTypeId()))
			return;

		if (!wgPlugin.canBuild(damager, e.getEntity().getLocation())) {
			damager.sendMessage(ChatColor.DARK_RED
					+ "You are not allowed to damage the animal in this area.");
			e.setCancelled(true);
		}
	}
}
