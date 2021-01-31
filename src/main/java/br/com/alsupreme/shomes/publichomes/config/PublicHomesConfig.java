package main.java.br.com.alsupreme.shomes.publichomes.config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PublicHomesConfig {
	
	
	public static File file;
	public static FileConfiguration Cfile;
	
	public static void generateFile(String playername) {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("SupremeHomes").getDataFolder(),"Public Players Homes/" + playername + ".yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		Cfile = YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration getConfig() {
		return Cfile;
	}
	
	public static void saveConfig() {
		try {
			Cfile.save(file);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void reloadConfig() {
		YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration loadConfig(String playername) {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("SupremeHomes").getDataFolder(), "Public Players Homes/" + playername + ".yml");
		Cfile = YamlConfiguration.loadConfiguration(file);
		return Cfile;
	}
}
