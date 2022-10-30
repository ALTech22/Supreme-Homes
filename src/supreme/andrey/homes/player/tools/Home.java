package supreme.andrey.homes.player.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;
import supreme.andrey.homes.SoundManager;
import supreme.andrey.homes.SupremeHomes;
import supreme.andrey.homes.events.definitions.OnPlayerSetNewHome;
import supreme.andrey.homes.events.definitions.OnPlayerSetPublicHome;
import supreme.andrey.homes.events.definitions.OnPlayerUnsetPublicHome;
import supreme.andrey.homes.permission.Permission;
import supreme.andrey.homes.permission.PermissionManager;
import supreme.andrey.homes.utils.Teleport;
import supreme.andrey.homes.utils.runnable.Delayed_Teleport;

public class Home {
	private static Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
	private static HashMap<Player, Delayed_Teleport> teleportPlayerMap = new HashMap<Player, Delayed_Teleport>();
	
	private static void showMessage(Player player) {
		@SuppressWarnings("unchecked")
		ArrayList<String> message = (ArrayList<String>) plugin.getConfig().getList("limit.message");
		
		if (message != null)
			for (String item : message) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', item));
			}
		
	}
	
	private static boolean isUpdatingAHome(String homeName, Player player) {
		Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
		Set<String> homes = config.getConfig().getKeys(false);
		
		for (String home : homes) {
			if(home.equals(homeName)) {
				return true;
			}
		}
		return false;
		
	}
	
	private static boolean checkIfIsInLimit(Player player, String homeName) {
		PermissionManager pm = new PermissionManager(player, plugin);
		Permission permission = pm.getPlayerPermission();
		Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
		int currentNHomes = config.getConfig().getKeys(false).size() + 1;
		
		if (infinitHomesIsAllowed()) {
			return false;
		}
		
		if (permission == null) {

			int maxHomes = plugin.getConfig().getInt("limit.max_homes");
			
			if (currentNHomes > maxHomes) {
				if (!isUpdatingAHome(homeName, player))
					showMessage(player);
				return true;
			}
			
		} else {
	
			int maxHomes = permission.getHomeLimit();
			
			if (currentNHomes > maxHomes) {
				if (!isUpdatingAHome(homeName, player))
					permission.showMessage(player);
				return true;
			} 
		}
		return false;
	}
	
	public static boolean infinitHomesIsAllowed() {
		return !(plugin.getConfig().getBoolean("limit.isActive"));
	}
	
	
	public static void setHome(Player player, String[] args) {
		// /sethome <homename>
		if(args.length == 1) {
			if(args[0].equals("public")) {
				player.sendMessage("Unable to set a home with name 'public'");
				return;
			}

			String playerName = player.getName();
			String homeName = args[0];
			String world = player.getWorld().getName();
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			Configuration config = new Configuration(plugin, "homes/", playerName, false);
			
			if (checkIfIsInLimit(player, homeName) && !isUpdatingAHome(homeName, player)) {
				return;
			}
			
			try {
				
				OnPlayerSetNewHome e = new OnPlayerSetNewHome(player);
				
				Bukkit.getPluginManager().callEvent(e);
				
				if (!e.isCancelled()) {
				
					config.getConfig().set(homeName + ".world", world);
					config.getConfig().set(homeName + ".x", x);
					config.getConfig().set(homeName + ".y", y);
					config.getConfig().set(homeName + ".z", z);
					config.getConfig().set(homeName + ".public", false);
					config.saveConfig();
					config.reloadConfig();
					SoundManager.playSoundSetHome(player);
					player.sendMessage("new home: "+homeName+" defined");
				}
			}catch (Exception e) {
				System.out.println(e);
				player.sendMessage("error on sethome");
			}
		}else {
			player.sendMessage("Correct use is: /sethome <homename>");
		}
	}
	
	public static void home(Player player, String args[]) {
		if(args.length > 0) {
			String homeName = null;
			String playerHome = null;
			if(args.length == 1) {
				homeName = args[0];
				if(homeName.equals("public")) {
					player.sendMessage("Correct usage is: /home public <player_name> <home_name>");
					return;
				}
			} else if(args.length == 3) {
				homeName = args[2];
				playerHome = args[1];
				Configuration config = new Configuration(plugin, "homes/", playerHome, false);
				if(!config.getConfig().getBoolean(homeName + ".public")) {
					player.sendMessage("Home not exists");
					return;
				}
			}
			if(!plugin.getConfig().getBoolean("home_command.delay")) {
				if(args[0].equals("public") && args.length == 3) {
					
					Teleport.teleportPlayerToPublicHome(homeName, player, playerHome, plugin);
				} else if (args.length == 1){
					Bukkit.getConsoleSender().sendMessage(homeName + "-" + playerHome); 
					Teleport.teleportPlayer(homeName, player, plugin);
				}
			} else {
				int delay_time = plugin.getConfig().getInt("home_command.delay_time");
				if(args[0].equals("public") && args.length == 3) {
					if(!teleportPlayerMap.containsKey(player)) {
						teleportPlayerMap.put(player, new Delayed_Teleport(plugin, playerHome, homeName, player, delay_time));
						teleportPlayerMap.get(player).runTaskTimer(plugin, 0, 20);
					} else {
						player.sendMessage("Wait your teleport has been done");
					}
				} else {
					
					if(!teleportPlayerMap.containsKey(player)) {
						teleportPlayerMap.put(player, new Delayed_Teleport(plugin, homeName, player, delay_time));
						teleportPlayerMap.get(player).runTaskTimer(plugin, 0, 20);
					} else {
						player.sendMessage("Wait your teleport has been done");
					}
				}
				
				
			}
		}else {
			player.sendMessage("Correct use is: /sethome <homename>");
		}
	}
	
	public static void homes(Player player) {
		Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
		Set<String> homes = config.getConfig().getKeys(false);
		if(homes.isEmpty()) {
			player.sendMessage("you not have any home, use the /sethome <homename> to set a new home");
			return;
		}
		player.sendMessage(ChatColor.DARK_PURPLE + "Your homes: ");
		for(String home : homes) {
			player.sendMessage(home);
		}
		SoundManager.playSoundHomes(player);
			
	}
	
	public static void delHome(Player player, String args[]) {
		Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
		String homeName = args[0];
		
		config.getConfig().set(homeName, null);
		config.saveConfig();
		config.reloadConfig();
		SoundManager.playSoundHome(player);
		player.sendMessage(homeName + " deletado com sucesso");
	}
	
	public static void setPublic(Player player, String args[]) {
		String playerName = player.getName();
		String homeName = args[0];
		Configuration config = new Configuration(plugin, "homes/", playerName, false);
		OnPlayerSetPublicHome e = new OnPlayerSetPublicHome(player);
		
		
		Bukkit.getPluginManager().callEvent(e);
		
		if (!e.isCancelled()) {
		
			config.getConfig().set(homeName + ".public", true);
			config.saveConfig();
			config.reloadConfig();
			SoundManager.playSoundSetHome(player);
			player.sendMessage("new public home: "+homeName);
		}
	}
	
	public static void unsetPublic(Player player, String args[]) {
		String playerName = player.getName();
		String homeName = args[0];
		Configuration config = new Configuration(plugin, "homes/", playerName, false);
		OnPlayerUnsetPublicHome e = new OnPlayerUnsetPublicHome(player);
		
		Bukkit.getPluginManager().callEvent(e);
		
		if (!e.isCancelled()) {
		
			config.getConfig().set(homeName + ".public", false);
			config.saveConfig();
			config.reloadConfig();
			SoundManager.playSoundSetHome(player);
			player.sendMessage("unseted public: "+homeName);
		}
	}
	
	public static HashMap<Player, Delayed_Teleport> getTeleportPlayerMap(){
		return teleportPlayerMap;
	}

}