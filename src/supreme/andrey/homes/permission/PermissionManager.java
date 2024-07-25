package supreme.andrey.homes.permission;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;

public class PermissionManager {
	private Plugin plugin;
	private Configuration config;
	
	public PermissionManager(Plugin plugin) {
		super();
		this.setPlugin(plugin);
		this.config = new Configuration(plugin, "", "permissions", true);
	}
	
	public Permission getPlayerPermission(Player player) {
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

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
}
