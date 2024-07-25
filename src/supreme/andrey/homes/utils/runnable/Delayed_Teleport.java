package supreme.andrey.homes.utils.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import supreme.andrey.homes.SoundManager;
import supreme.andrey.homes.SupremeHomes;
import supreme.andrey.homes.events.definitions.OnPlayerTeleportHomeEvent;
import supreme.andrey.homes.utils.ClassUtils;
import supreme.andrey.homes.utils.HomeVariables;
import supreme.andrey.homes.utils.SpigotUtils;
import supreme.andrey.homes.utils.Teleport;

public class Delayed_Teleport extends BukkitRunnable {
	private Plugin plugin;
	private String homeName;
	private String playerHome = null;
	private String playerName = null;
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
	
	public Delayed_Teleport(Plugin plugin, String homeName, Player player, int delay, String playerName) {
		super();
		this.plugin = plugin;
		this.homeName = homeName;
		this.player = player;
		this.delay = delay;
		this.playerName = playerName;
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
					 
				if(playerHome == null && playerName == null) {
					Teleport.teleportPlayer(player, homeName, player.getName(), plugin);
				} else if (playerName == null) {
					Teleport.teleportPlayerToPublicHome(homeName, player, playerHome, plugin);
				} else {
					Teleport.teleportPlayer(player, homeName, playerName, plugin);
				}
				HomeVariables.getTeleportPlayerMap().remove(player);
			}
			
			this.cancel();
		} else {
			SoundManager.playSoundWaitForTp(player);
			String[] variables = {"{seconds}"};
			String[] values = {String.valueOf(delay-count)};
			player.sendMessage(SupremeHomes.getLanguage().getDelayedTeleportMessage("waiting", variables, values));
			if (ClassUtils.classExists("net.md_5.bungee.api.chat.HoverEvent"))
				SpigotUtils.createCancelTeleportCommand(player);
			
			count++;
		}	
	}
	
	public boolean hasDone() {
		return hasDone;
	}
}