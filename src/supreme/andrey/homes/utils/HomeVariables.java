package supreme.andrey.homes.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

import supreme.andrey.homes.utils.runnable.Delayed_Teleport;

public class HomeVariables {
	private static HashMap<Player, Delayed_Teleport> teleportPlayerMap = new HashMap<Player, Delayed_Teleport>();
	public static HashMap<Player,Delayed_Teleport> getTeleportPlayerMap() {
		return teleportPlayerMap;
	}
	public static void setHashMap(Player player, Delayed_Teleport dt) {
		teleportPlayerMap.put(player, dt);
	}
	public static void removeHashMap(Player player) {
		teleportPlayerMap.remove(player);
	}
}
