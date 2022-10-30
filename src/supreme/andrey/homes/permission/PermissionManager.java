package supreme.andrey.homes.permission;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;

public class PermissionManager {
	private Player player;
	private Plugin plugin;
	private Configuration config;
	
	public PermissionManager(Player player, Plugin plugin) {
		super();
		this.player = player;
		this.plugin = plugin;
		this.config = new Configuration(plugin, "", "permissions", true);
	}
	
	public Permission getPlayerPermission() {
		Set<String> keys = config.getConfig().getKeys(false);
		
		for (String key : keys) {
			if (player.isPermissionSet(key)) {
				@SuppressWarnings("unchecked")
				Permission permission = new Permission((ArrayList<String>) config.getConfig().getList(key + ".message"), 
						key, config.getConfig().getInt(key + ".limitHomes"));
				
				return permission;
			}
		}
		
		return null;
	}
	
}
