package supreme.andrey.homes.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;
import supreme.andrey.homes.SoundManager;

public class Teleport {
	public static void teleportPlayer(String homeName, Player player, Plugin plugin) {
		Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
		World world = Bukkit.getWorld(config.getConfig().getString(homeName + ".world"));
		double x = config.getConfig().getDouble(homeName + ".x");
		double y = config.getConfig().getDouble(homeName + ".y");
		double z = config.getConfig().getDouble(homeName + ".z");
		Location loc = new Location(world, x, y, z);
		player.teleport(loc);
		player.sendMessage("teleportado para " + homeName);
		SoundManager.playSoundHome(player);
	}
	
	public static void teleportPlayerToPublicHome(String homeName, Player player, String playerHome, Plugin plugin) {
		Configuration config = new Configuration(plugin, "homes/", playerHome, false);
		World world = Bukkit.getWorld(config.getConfig().getString(homeName + ".world"));
		double x = config.getConfig().getDouble(homeName + ".x");
		double y = config.getConfig().getDouble(homeName + ".y");
		double z = config.getConfig().getDouble(homeName + ".z");
		Location loc = new Location(world, x, y, z);
		player.teleport(loc);
		player.sendMessage("teleportado para " + homeName);
		SoundManager.playSoundHome(player);
	}
}
