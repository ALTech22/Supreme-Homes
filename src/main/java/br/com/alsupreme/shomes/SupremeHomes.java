package main.java.br.com.alsupreme.shomes;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import main.api.java.org.bstats.bukkit.Metrics;
import main.java.br.com.alsupreme.shomes.commands.Commands;
import main.java.br.com.alsupreme.shomes.commands.TabCompleter;
import main.java.br.com.alsupreme.shomes.publichomes.commands.PublicHomesCommands;
import main.java.br.com.alsupreme.shomes.publichomes.commands.PublicHomesTabCompleter;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class SupremeHomes extends JavaPlugin{
	 public FileConfiguration config = this.getConfig();
	 
	 public static Economy economy = null;
	

	@Override
	public void onEnable() {
		File file = new File(this.getServer().getPluginManager().getPlugin("SupremeHomes").getDataFolder(), "config.yml");
		//setup config
		if(!file.exists()) {
			this.saveResource("config.yml", false);
		}else {
			this.reloadConfig();
			config.options().copyDefaults(true);
			this.saveConfig();
		}
		
		
		
		this.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "SUPREME SET HOMES ARE BEEN LOADED, VERSION: " + ChatColor.GREEN + this.getDescription().getVersion() + ChatColor.DARK_GRAY + " Created by: Andrey H.");
		SoundManager.genSoundConfig();
		
		EconomyHandler.Message();


		startCommands();
		if(config.getBoolean("Cancel_teleport_on_damage")) {
			this.getServer().getPluginManager().registerEvents(new Commands(), this);
			this.getServer().getPluginManager().registerEvents(new PublicHomesCommands(), this);
		}
		

		
		new UpdateChecker(this, 88025).getVersion(Version -> {
			if (this.getDescription().getVersion().equalsIgnoreCase(Version)){
			this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8No update avaible for &bSupreme &aHomes, &6Current version: &f " + Version));
		}else {
			this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Update avaible for &bSupreme &aHomes, &6New version: &f" + Version));
		}
		});
		
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, 10590);
		

		
		super.onEnable();
	}

	
	public void startCommands() {
		this.getCommand("sethome").setExecutor(new Commands());
		this.getCommand("sethome").setTabCompleter(new TabCompleter());
		
		this.getCommand("home").setExecutor(new Commands());
		this.getCommand("home").setTabCompleter(new TabCompleter());
		
		this.getCommand("homes").setExecutor(new Commands());
		
		this.getCommand("delhome").setExecutor(new Commands());
		this.getCommand("delhome").setTabCompleter(new TabCompleter());
		
		//commands of public homes
		this.getCommand("setpublichome").setExecutor(new PublicHomesCommands());
		this.getCommand("setpublichome").setTabCompleter(new PublicHomesTabCompleter());
		
		this.getCommand("publichome").setExecutor(new PublicHomesCommands());
		this.getCommand("publichome").setTabCompleter(new PublicHomesTabCompleter());
		
		this.getCommand("delpublichome").setExecutor(new PublicHomesCommands());
		this.getCommand("delpublichome").setTabCompleter(new PublicHomesTabCompleter());
		
		this.getCommand("publichomes").setExecutor(new PublicHomesCommands());
		
	}
	
}
