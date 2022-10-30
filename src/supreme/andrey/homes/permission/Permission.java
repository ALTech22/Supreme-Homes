package supreme.andrey.homes.permission;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Permission {
	private ArrayList<String> message;
	private String name;
	private int homeLimit;
	public Permission(ArrayList<String> message, String name, int homeLimit) {
		super();
		this.message = message;
		this.name = name;
		this.homeLimit = homeLimit;
	}
	public ArrayList<String> getMessage() {
		return message;
	}
	public String getName() {
		return name;
	}
	public int getHomeLimit() {
		return homeLimit;
	}
	
	public void showMessage(Player player) {
		
		if (this.message != null)
			for (String item : message) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', item));
			}
	}
}
