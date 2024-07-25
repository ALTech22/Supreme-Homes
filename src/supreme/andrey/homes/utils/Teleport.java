package supreme.andrey.homes.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;
import supreme.andrey.homes.SoundManager;
import supreme.andrey.homes.SupremeHomes;

public class Teleport {
	public static void teleportPlayer(Player player, String homeName, String playerHome, Plugin plugin) {
		Configuration config = new Configuration(plugin, "homes/", playerHome, false);
		String worldname = config.getConfig().getString(homeName + ".world");
		if (worldname == null) {
			player.sendMessage("Home not exists, set a home with /sethome <home_name> before use /home <home_name>");
			return;
		}
		World world = Bukkit.getWorld(worldname);
		double x = config.getConfig().getDouble(homeName + ".x");
		double y = config.getConfig().getDouble(homeName + ".y");
		double z = config.getConfig().getDouble(homeName + ".z");
		Location loc = new Location(world, x, y, z);
		player.teleport(loc);
		
		String[] variables = {
				"{homeName}"
		};
		
		String[] values = {
				homeName
		};
		
		player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("on_teleport", variables, values));
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
		String[] variables = {
				"{homeName}",
				"{playerName}"
		};
		
		String[] values = {
				homeName,
				playerHome
		};
		player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("on_teleport", variables, values));
		SoundManager.playSoundHome(player);
	}
}
