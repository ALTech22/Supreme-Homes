package supreme.andrey.homes.player.tools;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.SupremeHomes;
import supreme.andrey.homes.utils.HomeVariables;
import supreme.andrey.homes.utils.Teleport;
import supreme.andrey.homes.utils.runnable.Delayed_Teleport;

public class HomeTeleport {
	
	public static void teleportPlayer(Player player, String homeName, Plugin plugin, String playerHome) {
		System.out.println(homeName);
		if(!plugin.getConfig().getBoolean("home_command.delay")) {

			Teleport.teleportPlayer(player, homeName, playerHome, plugin);
			
		} else {
			int delay_time = plugin.getConfig().getInt("home_command.delay_time");
			System.out.println(playerHome);
			if(!HomeVariables.getTeleportPlayerMap().containsKey(player)) {
				HomeVariables.getTeleportPlayerMap().put(player, new Delayed_Teleport(plugin, homeName, player, delay_time, playerHome));
				HomeVariables.getTeleportPlayerMap().get(player).runTaskTimer(plugin, 0, 20);
			} else {
				player.sendMessage(SupremeHomes.getLanguage().getHomeMessage("home_wait_teleport"));
			}
			
		}
	
	}
	
}
