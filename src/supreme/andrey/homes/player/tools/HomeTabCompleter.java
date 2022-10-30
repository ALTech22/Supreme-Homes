package supreme.andrey.homes.player.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;

public class HomeTabCompleter implements TabCompleter{
	
	private Plugin plugin;
	public HomeTabCompleter(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command com, String lab, String[] args) {
		List<String> list = new ArrayList<String>();
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(com.getName().equals("delhome")) {
				Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
				for(String home : config.getConfig().getKeys(false)) {
					list.add(home);
				}
			} else if(com.getName().equals("setpublic")) {
				Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
				for(String home : config.getConfig().getKeys(false)) {
					if(!config.getConfig().getBoolean(home + ".public"))
					list.add(home);
				}
			} else if(com.getName().equals("unsetpublic")) {
				Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
				for(String home : config.getConfig().getKeys(false)) {
					if(config.getConfig().getBoolean(home + ".public"))
					list.add(home);
				}
			} else if(com.getName().equals("home")) {
				if(args.length == 1) {
					list.add("public");
					Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
					for(String home : config.getConfig().getKeys(false)) {
						list.add(home);
					}
				} else if(args[0].equals("public")) {
					
					if(args.length == 2) {
						for(OfflinePlayer playerList : Bukkit.getOfflinePlayers()) {
							list.add(playerList.getName());
						}
					}else if(args.length == 3) {
						Configuration config = new Configuration(plugin, "homes/", args[1], false);
						for(String home : config.getConfig().getKeys(false)) {
							if(config.getConfig().getBoolean(home + ".public"))
								list.add(home);
						}
					}
				}
			}
		}
		return list;
	}

}
