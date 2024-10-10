package supreme.andrey.homes.player.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import supreme.andrey.homes.utils.ClassUtils;
import supreme.andrey.homes.utils.HomeVariables;
import supreme.andrey.homes.utils.SpigotUtils;
import supreme.andrey.homes.utils.Teleport;
import supreme.andrey.homes.utils.runnable.Delayed_Teleport;

public class Home {
	private static Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
	
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
		Permission permission = SupremeHomes.getPermissions(player);
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
				player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("sethome_error_home_named_public"));
				return;
			}

			String playerName = player.getName();
			String homeName = args[0];
			String world = player.getWorld().getName();
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			Configuration config = new Configuration(plugin, "homes/", playerName, false);
			boolean isUpdating = isUpdatingAHome(homeName, player);
			
			if (checkIfIsInLimit(player, homeName) && !isUpdating) {
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
					String[] variables = {
							"{homeName}"
					};
					String[] values = {
							homeName
					};
					
					if (isUpdating) player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("sethome_update_success", variables, values));
					else player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("sethome_success", variables, values));
				}
			}catch (Exception e) {
				System.out.println(e);
				player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("sethome_error"));
			}
		}else {
			player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("sethome_incorrect"));
		}
	}
	
	public static void home(Player player, String args[]) {
		if(args.length > 0) {
			String homeName = null;
			String playerHome = null;
			if(args.length == 1) {
				homeName = args[0];
				if(homeName.equals("public")) {
					player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("home_incorrect"));
					return;
				}
			} else if(args.length == 3) {
				homeName = args[2];
				playerHome = args[1];
				Configuration config = new Configuration(plugin, "homes/", playerHome, false);
				if(!config.getConfig().getBoolean(homeName + ".public")) {
					player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("home_not_exists"));
					return;
				}
			}
			if(!plugin.getConfig().getBoolean("home_command.delay")) {
				if(args[0].equals("public") && args.length == 3) {
					
					Teleport.teleportPlayerToPublicHome(homeName, player, playerHome, plugin);
				} else if (args[0].equals("public")) {
					player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("home_incorrect"));
				} else if (args.length == 1) {
					Bukkit.getConsoleSender().sendMessage(homeName + "-" + playerHome); 
					Teleport.teleportPlayer(player, homeName, player.getName(), plugin);
				}
			} else {
				int delay_time = plugin.getConfig().getInt("home_command.delay_time");
				if(args[0].equals("public") && args.length == 3) {
					if(!HomeVariables.getTeleportPlayerMap().containsKey(player)) {
						HomeVariables.getTeleportPlayerMap().put(player, new Delayed_Teleport(plugin, playerHome, homeName, player, delay_time));
						HomeVariables.getTeleportPlayerMap().get(player).runTaskTimer(plugin, 0, 20);
					} else {
						player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("home_wait_teleport"));
					}
				} else {
					
					if(!HomeVariables.getTeleportPlayerMap().containsKey(player)) {
						HomeVariables.getTeleportPlayerMap().put(player, new Delayed_Teleport(plugin, homeName, player, delay_time));
						HomeVariables.getTeleportPlayerMap().get(player).runTaskTimer(plugin, 0, 20);
					} else {
						player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("home_wait_teleport"));
					}
				}
				
				
			}
		}else {
			player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("home_incorrect"));
		}
	}
	
	public static void homes(Player player, String args[]) {

		Configuration config = new Configuration(plugin, "homes/", args.length == 0 ? player.getName() : args[0], false);
		Set<String> bruteHomes = config.getConfig().getKeys(false);
		Set<String> homes = new HashSet<String>();

		if(args.length == 1) {
			for(String home : bruteHomes)
				if (config.getConfig().getBoolean(home + ".public"))
					homes.add(home);
					
		} else homes = bruteHomes;
		if(homes.isEmpty()) {
			if (args.length == 0)
				player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("homes_not_have_homes"));
			else {
				String[] variables = {"{playerName}"};
				String[] values = {args[0]};
				player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("homes_player_not_has_public_homes", variables, values));
			}
			return;
		}
		
		if (args.length == 0)
			player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("homes_your_homes"));
		else if (SupremeHomes.getLanguage().getHomeMessage("homes_player_homes") != null) {
			String[] variables = {"{playerName}"};
			String[] values = {args[0]};
			player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("homes_player_homes", variables, values));
		}
		else 
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Homes: "));
		
		for(String home : homes) {

			String[] variables = {"{home}"};
			String[] values = {home};
			if (ClassUtils.classExists("net.md_5.bungee.api.chat.HoverEvent"))
				SpigotUtils.teleportInHomeMessage(player, SupremeHomes.getLanguage().getHomeMessage("homes_home", variables, values), home, args.length == 0 ? player.getName() : args[0]);
			else
				player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("homes_home", variables, values));
			
		}
		SoundManager.playSoundHomes(player);
			
	}
	
	public static void delHome(Player player, String args[]) {
		System.out.println(args.length);
		if (args.length < 1) {
			player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("delhome_incorrect"));
			return;
		}
		Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
		for (int i=0; i<args.length; i++) {
			String homeName = args[i];
			
			
			if (config.getConfig().get(homeName) == null) {
				player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("delhome_home_not_exists"));
				continue;
			}
			
			config.getConfig().set(homeName, null);
			config.saveConfig();
			config.reloadConfig();
			SoundManager.playSoundHome(player);
			String[] variables = {"{homeName}"};
			String[] values = {homeName};
			player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("delhome_success", variables, values));
		}
	}
	
	public static void setPublic(Player player, String args[]) {
		String playerName = player.getName();
		if (args.length != 1) {
			player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("setpublic_incorrect"));
			return;
		}
		String homeName = args[0];
		Configuration config = new Configuration(plugin, "homes/", playerName, false);
		OnPlayerSetPublicHome e = new OnPlayerSetPublicHome(player);
		
		if (!config.getConfig().contains(homeName)) {
			player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("setpublic_not_have_selected_home"));
			return;
		}
		
		Bukkit.getPluginManager().callEvent(e);
		
		if (!e.isCancelled()) {
			config.getConfig().set(homeName + ".public", true);
			config.saveConfig();
			config.reloadConfig();
			SoundManager.playSoundSetHome(player);
			String[] variables = {"{homeName}"};
			String[] values = {homeName};
			player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("setpublic_success", variables, values));
		}
	}
	
	public static void unsetPublic(Player player, String args[]) {
		String playerName = player.getName();
		if (args.length != 1) {
			player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("unsetpublic_incorrect"));
			return;
		}
		String homeName = args[0];
		Configuration config = new Configuration(plugin, "homes/", playerName, false);
		OnPlayerUnsetPublicHome e = new OnPlayerUnsetPublicHome(player);
		
		Bukkit.getPluginManager().callEvent(e);
		
		if (!e.isCancelled()) {
		
			config.getConfig().set(homeName + ".public", false);
			config.saveConfig();
			config.reloadConfig();
			SoundManager.playSoundSetHome(player);
			String[] variables = {"{homeName}"};
			String[] values = {homeName};
			player.sendMessage(SupremeHomes.getLanguage().getHomePublicMessage("unsetpublic_success", variables, values));
		}
	}
	


}