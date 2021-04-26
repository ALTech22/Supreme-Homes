package main.java.br.com.alsupreme.shomes;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public abstract class EconomyHandler {
	
	
	static Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
	static File file = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getDescription().getName()).getDataFolder(), "Economy.yml");
	static FileConfiguration EconomyConfig;
	public static Boolean useEconomy = false;
	static RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

	
	private static boolean hasVault(){
        if(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null && plugin.getConfig().getBoolean("UseEconomy")) {
			usePriceHomeSystem(true);
			genFile();
        	reloadConfig();
        	return true;
        }
        return false;
    }
	
	public static double getBalance(Player player) {
		if(hasVault()) {

			return economyProvider.getProvider().getBalance(player);
		}
		return 0;
	}
	public static void addMoney(Player player, double money) {
		if(hasVault()) {
			economyProvider.getProvider().depositPlayer(player, money);
		}
	}
	public static void removeMoney(Player player, double money) {
		if(hasVault()) {
			economyProvider.getProvider().withdrawPlayer(player, money);
		}
	}
	public static void Message() {
		if(plugin.getConfig().getBoolean("UseEconomy") && hasVault()) {
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5--------Supreme &2Homes------"));
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&9|&4    VAULT HAS BEEN DETECTED"));
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&9|&4    ECONOMY OPTION HAS ENABLE"));
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&9|&4    ACTIVING THE PAYMENT FOR HOMES"));
			if(getConfig().getDouble("sethomePrice") == 0 && getConfig().getDouble("homePrice") == 0 && getConfig().getDouble("delhomePrice") == 0) {
				plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&9|&c    Sethome, Home and Delhome price is 0, Payment system is set to off"));
				usePriceHomeSystem(false);
			}
		}
	}
	private static void genFile() {
		if(!file.exists()) {
			plugin.saveResource("Economy.yml", false);
		}
	}
	
	private static void reloadConfig() {
		EconomyConfig = YamlConfiguration.loadConfiguration(file);
	}
	public static FileConfiguration getConfig() {
		return EconomyConfig;
	}
	
	public static boolean usePriceHomeSystem(Boolean canUse) {
		useEconomy = canUse;
		return useEconomy;
	}
}
