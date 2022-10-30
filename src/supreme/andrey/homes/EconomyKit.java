package supreme.andrey.homes;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class EconomyKit {
	
	public static Boolean canUseEconomyKit = hasVault();
	
	
	public static boolean hasVault(){
        if(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
        	return true;
        }
        return false;
    }
	
	public static double getPlayerBalance(OfflinePlayer player) {
		if(hasVault()) {
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
			return economyProvider.getProvider().getBalance(player);
		}
		return 0;
	}
	
	public static void setPlayerBalance(OfflinePlayer player, double money) {
		if(hasVault()) {
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
			economyProvider.getProvider().withdrawPlayer(player, getPlayerBalance(player));
			economyProvider.getProvider().depositPlayer(player, money);
		}
	}
	public static void addMoneyToPlayer(OfflinePlayer player, double money) {
		if(hasVault()) {
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
			economyProvider.getProvider().depositPlayer(player, money);
		}
	}
	public static void removeMoneyPlayer(OfflinePlayer player, double money) {
		if(hasVault()) {
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
			economyProvider.getProvider().withdrawPlayer(player, money);
		}
	}
	
	public static String getCurrencyName() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		return economyProvider.getProvider().currencyNamePlural();
	}
	
}
