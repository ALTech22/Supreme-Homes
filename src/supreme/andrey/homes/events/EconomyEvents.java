package supreme.andrey.homes.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;
import supreme.andrey.homes.EconomyKit;
import supreme.andrey.homes.SupremeHomes;
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
		config.getConfig().getString("messages.notHaveMoney");
		config.getConfig().getString("messages.succefullPayment");
		
		if (!checkIfHasMoney(price, actualMoney)) {
			String[] variables = {"{price}"};
			String[] values = {currencyName + String.valueOf((price - actualMoney))};
			return SupremeHomes.getLanguage().getEconomyMessage("not_have_money", variables, values);
		} else {
			String[] variables = {"{price}"};
			String[] values = {currencyName + String.valueOf(price)};
			return SupremeHomes.getLanguage().getEconomyMessage("successful_payment", variables, values);
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
	public void onPlayerDeleteHome(OnPlayerDeleteAHome e) {
		double price = config.getConfig().getDouble("delhomePrice");
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
	public void onPlayerSetPublic(OnPlayerSetPublicHome e) {
		double price = config.getConfig().getDouble("setPublicPrice");
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
	public void onPlayerUnsetPublic(OnPlayerUnsetPublicHome e) {
		double price = config.getConfig().getDouble("unsetPublicPrice");
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
}
