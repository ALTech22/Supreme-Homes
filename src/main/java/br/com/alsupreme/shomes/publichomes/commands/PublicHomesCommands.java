package main.java.br.com.alsupreme.shomes.publichomes.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.br.com.alsupreme.shomes.EconomyHandler;
import main.java.br.com.alsupreme.shomes.SoundManager;
import main.java.br.com.alsupreme.shomes.SupremeHomes;
import main.java.br.com.alsupreme.shomes.publichomes.config.PublicHomesConfig;
import net.md_5.bungee.api.ChatColor;

public class PublicHomesCommands extends EconomyHandler implements CommandExecutor, Listener{
	Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
	public static HashMap<Player, Boolean> cancelTP = new HashMap<Player, Boolean>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command com, String lab, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;
		String playername = player.getPlayer().getName();
		FileConfiguration config = PublicHomesConfig.loadConfig(playername);
		
		if(sender instanceof Player) {

			
			if(com.getName().equals("setpublichome")) {
				
				if(args.length != 1) {
					SoundManager.playSoundError(player);
					String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_error"));

					player.sendTitle(ChatColor.DARK_RED+"Error", msg, 10, 70, 20);
					player.sendMessage("Your not define a home name, this correct use is /setpublichome <homename>");
					return true;
				}
				
				if(useEconomy) {
					double sethomePrice = getConfig().getDouble("sethomePrice");
					
					if(getBalance(player) < sethomePrice && sethomePrice > 0) {
						String msg = getConfig().getString("messages.notHaveMoney");
						msg = ChatColor.translateAlternateColorCodes('&', msg);
						msg = msg.replace("{price}", String.valueOf(sethomePrice));
						player.sendMessage(msg);
						return true;
					}else if(sethomePrice > 0){
						if(sethomePrice < 0) {
							sethomePrice *= -1;
							addMoney(player, sethomePrice);
							String msg = getConfig().getString("messages.succefullReceivement");
							msg = ChatColor.translateAlternateColorCodes('&', msg);
							msg = msg.replace("{price}", String.valueOf(sethomePrice));
							player.sendMessage(msg);
						}else {
							removeMoney(player, sethomePrice);
							String msg = getConfig().getString("messages.succefullPayment");
							msg = ChatColor.translateAlternateColorCodes('&', msg);
							msg = msg.replace("{price}", String.valueOf(sethomePrice));
							player.sendMessage(msg);
						
						}
					}
				}
				
				
				if(player.hasPermission("suphomes.public.sethome") || player.hasPermission("suphomes.playerusepublic")) {
					String playerWorld = player.getLocation().getWorld().getName();

					
					config.set(args[0] + "." + "world", playerWorld);
					config.set(args[0] + "." + "X", player.getLocation().getX());
					config.set(args[0] + "." + "Y", player.getLocation().getY());
					config.set(args[0] + "." + "Z", player.getLocation().getZ());
					
					if((!plugin.getConfig().getBoolean("limited_homes") && canSetHome(plugin, playerWorld))) {
						config.options().copyDefaults(true);
						PublicHomesConfig.saveConfig();
						String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_define_a_public_home"));
						msg = msg.replace("{publichome}", args[0]);
						
						player.sendMessage(msg);
						
						msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_player_set_a_home"));
						msg = msg.replace("{home}", args[0]);
						player.sendTitle(ChatColor.DARK_GREEN+"new sethome", msg, 10, 70, 20);
						
						SoundManager.playSoundSetHome(player);
					}else if(!canSetHome(plugin, playerWorld)) {
						player.sendMessage("Impossible set a home in this world");
						String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_error"));

						player.sendTitle(ChatColor.DARK_RED+"Error", msg, 10, 70, 20);
						SoundManager.playSoundError(player);
					}else {
						
						int maxHomes = plugin.getConfig().getInt("vipsLimits.normalPlayer.limitHomes");
						List<String> homeList = new ArrayList<String>();
						homeList.addAll(getHomes(playername));
						
						if(player.hasPermission("suphomes.vip.tier1")) {
							maxHomes = plugin.getConfig().getInt("vipsLimits.viptier1.limitHomes");
							
						}
						if(player.hasPermission("suphomes.vip.tier2")) {
							maxHomes = plugin.getConfig().getInt("vipsLimits.viptier2.limitHomes");
							
						}
						if(player.hasPermission("suphomes.vip.tier3")) {
							maxHomes = plugin.getConfig().getInt("vipsLimits.viptier3.limitHomes");
							
						}
						if(player.hasPermission("suphomes.vip.tier4")){
							maxHomes = plugin.getConfig().getInt("vipsLimits.viptier4.limitHomes");
							
						}
						
						
						
						if(homeList.size() < maxHomes) {
							config.options().copyDefaults(true);
							PublicHomesConfig.saveConfig();
							String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_define_a_public_home"));
							msg = msg.replace("{publichome}", args[0]);
							
							player.sendMessage(msg);
							
							msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_player_set_a_home"));
							msg = msg.replace("{home}", args[0]);
							player.sendTitle(ChatColor.DARK_GREEN+"new sethome", msg, 10, 70, 20);
							
							SoundManager.playSoundSetHome(player);
						} else {
							if(maxHomes == plugin.getConfig().getInt("vipsLimits.viptier1.limitHomes")) {
								
								for(String msg : plugin.getConfig().getStringList("vipsLimits.viptier1.onLimit")) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
								}
								SoundManager.playSoundError(player);
							}else if(maxHomes == plugin.getConfig().getInt("vipsLimits.viptier2.limitHomes")) {
								
								for(String msg : plugin.getConfig().getStringList("vipsLimits.viptier2.onLimit")) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
								}
								SoundManager.playSoundError(player);
							}else if(maxHomes == plugin.getConfig().getInt("vipsLimits.viptier3.limitHomes")) {
								
								for(String msg : plugin.getConfig().getStringList("vipsLimits.viptier3.onLimit")) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
								}
								SoundManager.playSoundError(player);
							}else if(maxHomes == plugin.getConfig().getInt("vipsLimits.viptier4.limitHomes")) {
								
								for(String msg : plugin.getConfig().getStringList("vipsLimits.viptier4.onLimit")) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
								}
								SoundManager.playSoundError(player);
							} else {
								
								for(String msg : plugin.getConfig().getStringList("vipsLimits.normalPlayer.onLimit")) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
								}
								
							}
							String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_error"));

							player.sendTitle(ChatColor.DARK_RED+"Error", msg, 10, 70, 20);
						
						}
					}
				
					}
			
			}
			
			if(com.getName().equals("publichome")){
				if(player.hasPermission("suphomes.public.home") || player.hasPermission("suphomes.playerusepublic")) {
					if(args.length == 2) {
						
						if(useEconomy && PublicHomesConfig.loadConfig(args[0]).contains(args[1])) {
							double homePrice = getConfig().getDouble("homePrice");
							
							if(getBalance(player) < homePrice && homePrice > 0) {
								String msg = getConfig().getString("messages.notHaveMoney");
								msg = ChatColor.translateAlternateColorCodes('&', msg);
								msg = msg.replace("{price}", String.valueOf(homePrice));
								player.sendMessage(msg);
								return true;
							}else {
								if(homePrice < 0) {
									homePrice *= -1;
									addMoney(player, homePrice);
									String msg = getConfig().getString("messages.succefullReceivement");
									msg = ChatColor.translateAlternateColorCodes('&', msg);
									msg = msg.replace("{price}", String.valueOf(homePrice));
									player.sendMessage(msg);
								}else if(homePrice > 0){
									removeMoney(player, homePrice);
									String msg = getConfig().getString("messages.succefullPayment");
									msg = ChatColor.translateAlternateColorCodes('&', msg);
									msg = msg.replace("{price}", String.valueOf(homePrice));
									player.sendMessage(msg);
								
								}
							}
						}
						
						try {
							FileConfiguration pfileconfig = PublicHomesConfig.loadConfig(args[0]);
							
							String nameworld = pfileconfig.getString(args[1] + "." + "world");
							World world = Bukkit.getServer().getWorld(nameworld);
							int X = pfileconfig.getInt(args[1] + "." + "X");
							int Y = pfileconfig.getInt(args[1] + "." + "Y");
							int Z = pfileconfig.getInt(args[1] + "." + "Z");
							
							Location loc = new Location(world, X, Y, Z);
							
							
							
							
							if(plugin.getConfig().getBoolean("Delay_teleports")) {
	
								int CD = plugin.getConfig().getInt("Cooldown");
								HashMap<Player, Integer> counttime = new HashMap<Player, Integer>();
								HashMap<Player, BukkitRunnable> counttask = new HashMap<Player, BukkitRunnable>();
								cancelTP.put(player, false);
								
								
								String nameplayer = player.getName();
								
								if(counttime.containsKey(player)) {
									player.sendMessage("Wait for use this again");
								}
								
								counttime.put(player, CD);
								counttask.put(player, new BukkitRunnable() {
									
									@Override
									public void run() {
										if(counttime.get(player).intValue() <= 0) {
											counttime.remove(player);
											counttask.remove(player);
											cancel();
											player.teleport(loc);
											String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_teleport"));
											message = message.replace("{player}", nameplayer);
											message = message.replace("{home}", args[0]);
											
											player.sendMessage(message);
											message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_player_teleport"));
											message = message.replace("{home}", args[0]);
											player.sendTitle(ChatColor.DARK_RED+"Teleported", message, 10, 70, 20);
											SoundManager.playSoundHome(player);
											
										}else if(counttime.get(player).intValue() > 0) {
											
											if(cancelTP.get(player)) {
												player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.on_cancel_teleport_because_take_damage")));
												if(useEconomy) {
													double homePrice = getConfig().getDouble("homePrice");
													if(homePrice < 0) {
														homePrice *= -1;
														removeMoney(player, homePrice);

													}else if(homePrice > 0){
														addMoney(player, homePrice);
													
													}
												}
												counttime.remove(player);
												counttask.remove(player);
												cancel();
											}
											if((counttime.get(player)) != null){
												player.sendMessage("wait: " + counttime.get(player).intValue() + " seconds");
												counttime.put(player, counttime.get(player).intValue() - 1);
												SoundManager.playSoundWaitForTp(player);
											}

										}
										
									}
									
								});
								
								counttask.get(player).runTaskTimer(plugin, 0L, 20L);
	
						}else {
								player.teleport(loc);
								String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_teleport_public_home"));
								message = message.replace("{player}", playername);
								message = message.replace("{publichome}", args[1]);
								message = message.replace("{homeplayer}", args[0]);
								
								player.sendMessage(message);
								message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_player_teleport"));
								message = message.replace("{home}", args[0]);
								player.sendTitle(ChatColor.DARK_RED+"Teleported", message, 10, 70, 20);
								
								SoundManager.playSoundHome(player);
							}
						}catch (Exception e) {
							plugin.getServer().getConsoleSender().sendMessage("error teleport player " + playername + "error:  " + e);
							SoundManager.playSoundError(player);
							String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_error"));

							player.sendTitle(ChatColor.DARK_RED+"Error", msg, 10, 70, 20);

							SoundManager.playSoundError(player);
							if(!PublicHomesConfig.loadConfig(args[0]).contains(args[1])) {
								player.sendMessage("This home not exist");
							}else {
								player.sendMessage("An error has occurred  in your teleport");
							}
						}
						
						}else {
							String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_error"));

							player.sendTitle(ChatColor.DARK_RED+"Error", msg, 10, 70, 20);

							player.sendMessage("The correct use of this command is /publichome <nameofplayer> <homename>");
							SoundManager.playSoundError(player);
						}
					}
					
			}
			
			if(com.getName().equals("publichomes")) {
				if(player.hasPermission("suphomes.public.homes") || player.hasPermission("suphomes.playerusepublic")) {
					if(args.length == 1) {
						playername = args[0];
						
						List<String> homes = new ArrayList<String>();
						if(getHomes(playername)==null) {
							
						}else {
							try{
								String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.public_player_homes"));
								message = message.replace("{homeplayer}", playername);
								player.sendMessage(message);
							
								homes.addAll(getHomes(playername));
								for(int i=0; i<homes.size(); i++) {
									message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.public_player_on_see_homes"));
									message = message.replace("{publichome}", homes.get(i));
									player.sendMessage(message);
								}
								SoundManager.playSoundHomes(player);
							}catch(Exception e) {
								SoundManager.playSoundError(player);
								plugin.getServer().getConsoleSender().sendMessage(e.toString());
							}
						}
					}else {
						String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_error"));

						player.sendTitle(ChatColor.DARK_RED+"Error", msg, 10, 70, 20);
						player.sendMessage("Your not define a player name, this correct use is /publichomes <playername>");
						SoundManager.playSoundError(player);
					}
				}
			}
			if(com.getName().equals("delpublichome")) {
				if(player.hasPermission("suphomes.public.delhome") || player.hasPermission("suphomes.playerusepublic")) {
					if(args.length == 1) {
						
						if(useEconomy) {
							double delhomePrice = getConfig().getDouble("delhomePrice");
							
							if(getBalance(player) < delhomePrice && delhomePrice > 0) {
								String msg = getConfig().getString("messages.notHaveMoney");
								msg = ChatColor.translateAlternateColorCodes('&', msg);
								msg = msg.replace("{price}", String.valueOf(delhomePrice));
								player.sendMessage(msg);
								return true;
							}else {
								if(delhomePrice < 0) {
									delhomePrice *= (-1);
									addMoney(player, delhomePrice);
									String msg = getConfig().getString("messages.succefullReceivement");
									msg = ChatColor.translateAlternateColorCodes('&', msg);
									msg = msg.replace("{price}", String.valueOf(delhomePrice));
									player.sendMessage(msg);
								}else if(delhomePrice > 0){
									removeMoney(player, delhomePrice);
									String msg = getConfig().getString("messages.succefullPayment");
									msg = ChatColor.translateAlternateColorCodes('&', msg);
									msg = msg.replace("{price}", String.valueOf(delhomePrice));
									player.sendMessage(msg);
								
								}
							}
						}
						
						config.options().configuration().set(args[0], null);
						PublicHomesConfig.saveConfig();
						
						String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_delpublichome"));
						message = message.replace("{publichome}", args[0]);
						player.sendMessage(message);
						message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_player_del_a_home"));
						message = message.replace("{home}", args[0]);
						player.sendTitle(ChatColor.DARK_RED+"Home Deleted", message, 10, 70, 20);
						SoundManager.playSoundDelhomes(player);
					}else {
						String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("titles.on_error"));

						player.sendTitle(ChatColor.DARK_RED+"Error", msg, 10, 70, 20);
						player.sendMessage("Your not define a home name, this correct use is /delpublichome <homename>");
						SoundManager.playSoundError(player);
					}
				}
			}
			
		}
		
		return true;
	}
	
	public static Set<String> getHomes(String playername){
		
        File player_file = new File((new StringBuilder("plugins/SupremeHomes/Public Players Homes/")).append(playername).append(".yml").toString());
        if(player_file.exists())
        {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(player_file);
            return configuration.getKeys(false);
        } else
        {
            return new HashSet<String>();
        }
    }
	
	public Boolean canSetHome(Plugin plugin, String world) {
		for(String worlds : plugin.getConfig().getStringList("Disabled_Worlds")) {
			if(worlds.equals(world)) {
				return false;
			}
		}
		return true;
	}

	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			cancelTP.put(player, true);

			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					cancelTP.put(player, false);

					
				}
			}, 200);
		}
	}

}
