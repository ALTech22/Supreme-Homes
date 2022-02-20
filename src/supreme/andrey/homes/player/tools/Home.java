package supreme.andrey.homes.player.tools;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;
import supreme.andrey.homes.SoundManager;
import supreme.andrey.homes.SupremeHomes;

public class Home {
	private static Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
	public static void setHome(Player player, String[] args) {
		// /sethome <homename>
		if(args.length == 1) {
			String playername = player.getName();
			String homename = args[0];
			String world = player.getWorld().getName();
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			Configuration config = new Configuration(plugin, "homes/", playername, false);
			
			try {
				config.getConfig().set(homename + ".world", world);
				
				config.getConfig().set(homename + ".x", x);
				config.getConfig().set(homename + ".y", y);
				config.getConfig().set(homename + ".z", z);
				config.saveConfig();
				config.reloadConfig();
				SoundManager.playSoundSetHome(player);
				player.sendMessage("nova home: "+homename+" definida");
			}catch (Exception e) {
				player.sendMessage("impossivel setar home");
			}
		}else {
			player.sendMessage("Modo certo de usar o comando é: /sethome <homename>");
		}
	}
	
	public static void home(Player player, String args[]) {
		if(args.length == 1) {
			Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
			String homename = args[0];
			World world = Bukkit.getWorld(config.getConfig().getString(homename + ".world"));
			double x = config.getConfig().getDouble(homename + ".x");
			double y = config.getConfig().getDouble(homename + ".y");
			double z = config.getConfig().getDouble(homename + ".z");
			Location loc = new Location(world, x, y, z);
			player.teleport(loc);
			player.sendMessage("teleportado para " + homename);
			SoundManager.playSoundHome(player);
		}else {
			player.sendMessage("Modo certo de usar o comando é: /sethome <homename>");
		}
	}
	
	public static void homes(Player player) {
		Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
		Set<String> homes = config.getConfig().getKeys(false);
		if(homes.isEmpty()) {
			player.sendMessage("Ainda nao possui nenhuma home, use /sethome <homename> para criar uma nova home");
			return;
		}
		player.sendMessage(ChatColor.DARK_PURPLE + "Suas homes: ");
		for(String home : homes) {
			player.sendMessage(home);
		}
		SoundManager.playSoundHomes(player);
			
	}
	
	public static void delHome(Player player, String args[]) {
		Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
		String homename = args[0];
		
		config.getConfig().set(homename, null);
		config.saveConfig();
		config.reloadConfig();
		SoundManager.playSoundHome(player);
		player.sendMessage(homename + " deletado com sucesso");
	}
}