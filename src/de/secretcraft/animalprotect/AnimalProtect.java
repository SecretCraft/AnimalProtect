package de.secretcraft.animalprotect;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.secretcraft.animalprotect.listener.DamageAndFeedListener;

public class AnimalProtect extends JavaPlugin {
	Logger log;

	@Override
	public void onEnable() {
		log = this.getLogger();
		try {
			Bukkit.getPluginManager().registerEvents(
					new DamageAndFeedListener(Bukkit.getPluginManager()
							.getPlugin("WorldGuard")), this);
		} catch (Exception e) {
			log.warning("Could not enable AnimalProtect correctly");
		}
	}

	@Override
	public void onDisable() {

	}
}
