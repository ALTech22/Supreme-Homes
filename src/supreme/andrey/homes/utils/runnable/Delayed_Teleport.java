package supreme.andrey.homes.utils.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import supreme.andrey.homes.Configuration;
import supreme.andrey.homes.SoundManager;
import supreme.andrey.homes.events.definitions.OnPlayerTeleportHomeEvent;
import supreme.andrey.homes.player.tools.Home;
import supreme.andrey.homes.utils.Teleport;

public class Delayed_Teleport extends BukkitRunnable {
	private Plugin plugin;
	private String homeName;
	private String playerHome = null;
	private Player player;
	private int delay;
	private int count = 0;
	private boolean hasDone = false;
	
	public Delayed_Teleport(Plugin plugin, String homeName, Player player, int delay) {
		super();
		this.plugin = plugin;
		this.homeName = homeName;
		this.player = player;
		this.delay = delay;
	}
	
	public Delayed_Teleport(Plugin plugin, String playerHome, String homeName, Player player, int delay) {
		super();
		this.plugin = plugin;
		this.homeName = homeName;
		this.player = player;
		this.delay = delay;
		this.playerHome = playerHome;
	}

	@Override
	public void run() {
		
		if(count >= delay) {
			OnPlayerTeleportHomeEvent e = new OnPlayerTeleportHomeEvent(player);
			
			Bukkit.getPluginManager().callEvent(e);
			
			if (!e.isCancelled()) {
					
				player.sendMessage("Teleported to " + homeName);
				if(playerHome == null) {
					Teleport.teleportPlayer(homeName, player, plugin);
				} else {
					Teleport.teleportPlayerToPublicHome(homeName, player, playerHome, plugin);
				}
				Home.getTeleportPlayerMap().remove(player);
			}
			
			this.cancel();
		} else {
			SoundManager.playSoundWaitForTp(player);
			player.sendMessage("Wait "+ String.valueOf(delay-count) + " seconds");
			count++;
		}	
	}
	
	public boolean hasDone() {
		return hasDone;
	}
}