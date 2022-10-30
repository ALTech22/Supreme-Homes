package supreme.andrey.homes.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import supreme.andrey.homes.Configuration;
import supreme.andrey.homes.EconomyKit;
import supreme.andrey.homes.events.definitions.OnPlayerDeleteAHome;
import supreme.andrey.homes.events.definitions.OnPlayerSetNewHome;
import supreme.andrey.homes.events.definitions.OnPlayerSetPublicHome;
import supreme.andrey.homes.events.definitions.OnPlayerTeleportHomeEvent;
import supreme.andrey.homes.events.definitions.OnPlayerUnsetPublicHome;

public class EconomyEvents implements Listener {
	
	private Configuration config;
	
	public EconomyEvents(Plugin plugin) {
		config = new Configuration(plugin, "", "Economy", true);
	}
	
	private String buildMessage(double price, double actualMoney) {
		String currencyName = EconomyKit.getCurrencyName() + " ";
		String messageNotHasMoney = config.getConfig().getString("messages.notHaveMoney");
		String messageHasMoney = config.getConfig().getString("messages.succefullPayment");
		
		if (!checkIfHasMoney(price, actualMoney)) {
			String missingMoney = currencyName + String.valueOf((price - actualMoney));
			messageNotHasMoney = messageNotHasMoney.replace("{price}", missingMoney);
			return ChatColor.translateAlternateColorCodes('&', messageNotHasMoney);
		} else {
			String priceFormatted = currencyName + String.valueOf(price);
			messageHasMoney = messageHasMoney.replace("{price}", priceFormatted);
			return ChatColor.translateAlternateColorCodes('&', messageHasMoney);
		}
	}
	
	private boolean checkIfHasMoney(double price, double playerMoney) {
		return price <= playerMoney;
	}
	
	@EventHandler
	public void onPlayerSetHome(OnPlayerSetNewHome e) {
		double price = config.getConfig().getDouble("sethomePrice");
		Player player = e.getPlayer();
		double playerMoney = EconomyKit.getPlayerBalance(player);
		
		if(checkIfHasMoney(price, playerMoney)) {
			player.sendMessage(buildMessage(price, playerMoney));
			EconomyKit.removeMoneyPlayer(player, price);
		}else {
			e.setCancelled(true);
			player.sendMessage(buildMessage(price, playerMoney));
		}
		
		
	}
	
	@EventHandler
	public void onPlayerTeleportToHome(OnPlayerTeleportHomeEvent e) {
		double price = config.getConfig().getDouble("homePrice");
		OfflinePlayer player = e.getPlayer();
		double playerMoney = EconomyKit.getPlayerBalance(player);
		
		if(checkIfHasMoney(price, playerMoney)) {
			buildMessage(price, playerMoney);
			EconomyKit.removeMoneyPlayer(player, price);
		}else {
			e.setCancelled(true);
			buildMessage(price, playerMoney);
		}
	}
	
	@EventHandler
	public void onPlayerDeleteHome(OnPlayerDeleteAHome e) {
		double price = config.getConfig().getDouble("delhomePrice");
		OfflinePlayer player = e.getPlayer();
		double playerMoney = EconomyKit.getPlayerBalance(player);
		
		if(checkIfHasMoney(price, playerMoney)) {
			buildMessage(price, playerMoney);
			EconomyKit.removeMoneyPlayer(player, price);
		}else {
			e.setCancelled(true);
			buildMessage(price, playerMoney);
		}
	}
	
	@EventHandler
	public void onPlayerSetPublic(OnPlayerSetPublicHome e) {
		double price = config.getConfig().getDouble("setPublicPrice");
		OfflinePlayer player = e.getPlayer();
		double playerMoney = EconomyKit.getPlayerBalance(player);
		
		if(checkIfHasMoney(price, playerMoney)) {
			buildMessage(price, playerMoney);
			EconomyKit.removeMoneyPlayer(player, price);
		}else {
			e.setCancelled(true);
			buildMessage(price, playerMoney);
		}
	}

	@EventHandler
	public void onPlayerUnsetPublic(OnPlayerUnsetPublicHome e) {
		double price = config.getConfig().getDouble("unsetPublicPrice");
		OfflinePlayer player = e.getPlayer();
		double playerMoney = EconomyKit.getPlayerBalance(player);
		
		if(checkIfHasMoney(price, playerMoney)) {
			buildMessage(price, playerMoney);
			EconomyKit.removeMoneyPlayer(player, price);
		}else {
			e.setCancelled(true);
			buildMessage(price, playerMoney);
		}
	}
}
