package supreme.andrey.homes.player.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;
import supreme.andrey.homes.SupremeHomes;

public class HomeTabCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command com, String lab, String[] args) {
		List<String> list = new ArrayList<String>();
		if(sender instanceof Player) {
			Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
			Player player = (Player) sender;
			if(com.getName().equals("home") || com.getName().equals("delhome")) {
				Configuration config = new Configuration(plugin, "homes/", player.getName(), false);
				for(String home : config.getConfig().getKeys(false)) {
					list.add(home);
				}
			}
		}
		return list;
	}

}
