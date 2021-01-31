package main.java.br.com.alsupreme.shomes.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HomesConfigs {
	public static File file;
	public static FileConfiguration CFile;

	
	//encontre e/ou gera as config
	public static void generateConfig(String player) {

		
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("SupremeHomes").getDataFolder(), "Player Homes/" + player + ".yml");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
		CFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration getConfig() {
		return CFile;
	}
	
	public static void saveConfig() {
		try {
			CFile.save(file);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void reloadConfig() {
		CFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration loadFile(String player) {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("SupremeHomes").getDataFolder(), "Player Homes/" + player + ".yml");
		CFile = YamlConfiguration.loadConfiguration(file);
		return CFile;
	}
	
}
