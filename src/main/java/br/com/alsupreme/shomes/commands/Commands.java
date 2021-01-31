package main.java.br.com.alsupreme.shomes.commands;

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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.br.com.alsupreme.shomes.SoundManager;
import main.java.br.com.alsupreme.shomes.SupremeHomes;
import main.java.br.com.alsupreme.shomes.configs.HomesConfigs;
import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor, Listener{
	

	
	@Override
	public boolean onCommand(CommandSender sender, Command com, String label, String[] args) {
		
		Player player = (Player) sender;
		String NamePlayer = player.getPlayer().getName();
		Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
		HashMap<Boolean, String> savehome = new HashMap<Boolean, String>(); //HashMap<Is Teleport not Possible?, Reason>
		HomesConfigs.generateConfig(NamePlayer);

		
		
		if(com.getName().equals("sethome") && sender instanceof Player) {
			if(player.hasPermission("suphomes.sethome") || player.hasPermission("suphomes.playeruses")) {
				int MaxHomes;
			
				if(args.length == 1) {
					
	
					for(String DW : plugin.getConfig().getStringList("Disabled_Worlds")) {
						if(player.getLocation().getWorld().getName().equals(DW)) {
							savehome.put(false, "Impossible set home in this world");
						}
						
					}
					
						if(!plugin.getConfig().getBoolean("activate_limits")) {
							HomesConfigs.getConfig().set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
							HomesConfigs.getConfig().set(args[0] + "." +"X", (int)player.getLocation().getX());
							HomesConfigs.getConfig().set(args[0] + "." +"Y", (int)player.getLocation().getY());
							HomesConfigs.getConfig().set(args[0] + "." +"Z", (int)player.getLocation().getZ());
							

	
						}else if(HomesConfigs.getConfig().contains(args[0]) && plugin.getConfig().getBoolean("substitution_of_homes")) {
							
							HomesConfigs.getConfig().set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
							HomesConfigs.getConfig().set(args[0] + "." +"X", (int)player.getLocation().getX());
							HomesConfigs.getConfig().set(args[0] + "." +"Y", (int)player.getLocation().getY());
							HomesConfigs.getConfig().set(args[0] + "." +"Z", (int)player.getLocation().getZ());
							

							
							
						}else if(!player.hasPermission("suphomes.vip.tier1") && !player.hasPermission("suphomes.vip.tier2") && !player.hasPermission("suphomes.vip.tier3") && !player.hasPermission("suphomes.vip.tier4")){
							if(!(getHomes(NamePlayer) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(NamePlayer));
								
								MaxHomes = plugin.getConfig().getInt("vipsLimits.normalPlayer.limitHomes");
								if(Homelist.size() < MaxHomes) {
									HomesConfigs.getConfig().set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									HomesConfigs.getConfig().set(args[0] + "." +"X", (int)player.getLocation().getX());
									HomesConfigs.getConfig().set(args[0] + "." +"Y", (int)player.getLocation().getY());
									HomesConfigs.getConfig().set(args[0] + "." +"Z", (int)player.getLocation().getZ());

								}else if(!player.hasPermission("suphomes.vip.tier1") && !player.hasPermission("suphomes.vip.tier2") && !player.hasPermission("suphomes.vip.tier3") && !player.hasPermission("suphomes.vip.tier4")){
										if(!(savehome.get(false) == "Impossible set home in this world")) {
										savehome.put(false, "Require more vip");
										for(String messages : plugin.getConfig().getStringList("vipsLimits.normalPlayer.onLimit")) {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages));
									
									}}
									
									
								}
							}
							
							
						}
						if(player.hasPermission("suphomes.vip.tier1")) {
							
							if(!(getHomes(NamePlayer) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(NamePlayer));

								MaxHomes = plugin.getConfig().getInt("vipsLimits.viptier1.limitHomes");
								if(Homelist.size() < MaxHomes) {
									HomesConfigs.getConfig().set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									HomesConfigs.getConfig().set(args[0] + "." +"X", (int)player.getLocation().getX());
									HomesConfigs.getConfig().set(args[0] + "." +"Y", (int)player.getLocation().getY());
									HomesConfigs.getConfig().set(args[0] + "." +"Z", (int)player.getLocation().getZ());
									
						
								}else if(!player.hasPermission("suphomes.vip.tier4") && !player.hasPermission("suphomes.vip.tier3" ) && !player.hasPermission("suphomes.vip.tier2")){
									
										if(!(savehome.get(false) == "Impossible set home in this world")) {
										savehome.put(false, "Require more vip");
										for(String messages : plugin.getConfig().getStringList("vipsLimits.viptier1.onLimit")) {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages));
									
									}}
								}
							}
							
						}
						if(player.hasPermission("suphomes.vip.tier2")) {
							
							if(!(getHomes(NamePlayer) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(NamePlayer));

								MaxHomes = plugin.getConfig().getInt("vipsLimits.viptier2.limitHomes");
								if(Homelist.size() < MaxHomes) {
									HomesConfigs.getConfig().set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									HomesConfigs.getConfig().set(args[0] + "." +"X", (int)player.getLocation().getX());
									HomesConfigs.getConfig().set(args[0] + "." +"Y", (int)player.getLocation().getY());
									HomesConfigs.getConfig().set(args[0] + "." +"Z", (int)player.getLocation().getZ());
									
						
								}else if(!player.hasPermission("suphomes.vip.tier4") && !player.hasPermission("suphomes.vip.tier3")){
										if(!(savehome.get(false) == "Impossible set home in this world")) {
										savehome.put(false, "Require more vip");
										for(String messages : plugin.getConfig().getStringList("vipsLimits.viptier2.onLimit")) {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages));
									
									}}
								}
							}
							
						}
						if(player.hasPermission("suphomes.vip.tier3")) {
							
							if(!(getHomes(NamePlayer) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(NamePlayer));

								MaxHomes = plugin.getConfig().getInt("vipsLimits.viptier3.limitHomes");
								if(Homelist.size() < MaxHomes) {
									HomesConfigs.getConfig().set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									HomesConfigs.getConfig().set(args[0] + "." +"X", (int)player.getLocation().getX());
									HomesConfigs.getConfig().set(args[0] + "." +"Y", (int)player.getLocation().getY());
									HomesConfigs.getConfig().set(args[0] + "." +"Z", (int)player.getLocation().getZ());
									

						
								}else if(!player.hasPermission("suphomes.vip.tier4")){
										if(!(savehome.get(false) == "Impossible set home in this world")) {
										savehome.put(false, "Require more vip");
										for(String messages : plugin.getConfig().getStringList("vipsLimits.viptier3.onLimit")) {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages));
									
									}}
								}
							}
							
						}
						if(player.hasPermission("suphomes.vip.tier4")) {
							
							if(!(getHomes(NamePlayer) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(NamePlayer));

								MaxHomes = plugin.getConfig().getInt("vipsLimits.viptier4.limitHomes");
								if(Homelist.size() < MaxHomes) {
									HomesConfigs.getConfig().set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									HomesConfigs.getConfig().set(args[0] + "." +"X", (int)player.getLocation().getX());
									HomesConfigs.getConfig().set(args[0] + "." +"Y", (int)player.getLocation().getY());
									HomesConfigs.getConfig().set(args[0] + "." +"Z", (int)player.getLocation().getZ());
									
						
								}else {
									if(!(savehome.get(false) == "Impossible set home in this world")) {
										savehome.put(false, "Require more vip");
										for(String messages : plugin.getConfig().getStringList("vipsLimits.viptier4.onLimit")) {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages));
									
									}}
								}
							}
							
						}
						
						

						
						if(!savehome.containsKey(false)) {
							String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_define_a_default_home"));
							msg = msg.replace("{home}", args[0]);
							player.sendMessage(msg);
							HomesConfigs.getConfig().options().copyDefaults(true);
							HomesConfigs.saveConfig();
							SoundManager.playSoundSetHome(player);
						}else {
							SoundManager.playSoundError(player);
							if(!(savehome.get(false) == "Require more vip")) {player.sendMessage(savehome.get(false));}
						}
	
						/*
						 * 	aqui vem as configurações do preço com o vault
						 * 
						 * */
						

						
						
					}else {
						SoundManager.playSoundError(player);
						player.sendMessage("Your not define a home name, this correct use is /sethome <homename>");
	
					}
				}
			}
		
		if(com.getName().equals("home") && sender instanceof Player) {

			if(player.hasPermission("suphomes.home") || player.hasPermission("suphomes.playeruse")) {
				
				if(args.length == 1) {
					// Aqui vem uma verificação do preço
					try {
						String worldname = (String) HomesConfigs.loadFile(NamePlayer).get(args[0] + "." + ".world"); //pega o nome do mundo
						
						World world = Bukkit.getServer().getWorld(worldname); //carrega o mundo no plugin
						
						int X = HomesConfigs.loadFile(NamePlayer).getInt(args[0] + "." + "X");
						int Y = HomesConfigs.loadFile(NamePlayer).getInt(args[0] + "." + "Y");
						int Z = HomesConfigs.loadFile(NamePlayer).getInt(args[0] + "." + "Z");
						
						Location loc = new Location(world, X, Y, Z);
						
						
						if(plugin.getConfig().getBoolean("Delay_teleports")) {
							
							int CD = 5;
							HashMap<Player, Integer> counttime = new HashMap<Player, Integer>();
							HashMap<Player, BukkitRunnable> counttask = new HashMap<Player, BukkitRunnable>();
							

							
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
										message = message.replace("{player}", NamePlayer);
										message = message.replace("{home}", args[0]);
										
										player.sendMessage(message);
										SoundManager.playSoundHome(player);
									}else if(counttime.get(player).intValue() > 0) {
										player.sendMessage("wait: " + counttime.get(player).intValue() + " seconds");
										counttime.put(player, counttime.get(player).intValue() - 1);
										SoundManager.playSoundWaitForTp(player);
									}
									
								}
								
							});
							
							counttask.get(player).runTaskTimer(plugin, 0L, 20L);

					}else {
						
							player.teleport(loc);
							String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_teleport"));
							message = message.replace("{player}", NamePlayer);
							message = message.replace("{home}", args[0]);
							
							player.sendMessage(message);
							SoundManager.playSoundHome(player);
						
						}
						

						
					}catch (Exception e) {
						plugin.getServer().getConsoleSender().sendMessage("error teleport player " + NamePlayer + "error:  " + e);
						SoundManager.playSoundError(player);
						if(!HomesConfigs.loadFile(NamePlayer).contains(args[0])) {
							player.sendMessage("Home inexistente");
						}else {
							player.sendMessage("An error has occurred  in your teleport");
						}
					}
	
				}else {
					SoundManager.playSoundError(player);
					player.sendMessage("Your not define a home name, this correct use is /home <homename>");
				}
			}
		}
		
		if(com.getName().equals("homes") && sender instanceof Player) {
			if(player.hasPermission("suphomes.homes") || player.hasPermission("suphomes.playeruse")) {
				try {
					
					List<String> homes = new ArrayList<String>();
					if(getHomes(NamePlayer) == null) {
						
					}else {
						homes.addAll(getHomes(NamePlayer));
						
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.player_homes")));
						for(int i = 0; i<homes.size(); i++) {
							String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.player_on_see_homes"));
							message = message.replace("{home}", homes.get(i));
							player.sendMessage(message);
						}
						SoundManager.playSoundHomes(player);
					}
				} catch (Exception e) {
					SoundManager.playSoundError(player);
					plugin.getServer().getConsoleSender().sendMessage(e.toString());
				}
			}
		}
		
		if(com.getName().equals("delhome") && sender instanceof Player) {
			if(player.hasPermission("suphomes.delhome") || player.hasPermission("suphomes.playeruse")) {
				if(args.length == 1) {
				
					HomesConfigs.loadFile(NamePlayer).options().configuration().set(args[0], null);
					HomesConfigs.saveConfig();

					String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_delhome"));
					message = message.replace("{home}", args[0]);
					SoundManager.playSoundDelhomes(player);
					player.sendMessage(message);
				}else {
					SoundManager.playSoundError(player);
					player.sendMessage("The correct use of this command is /delhome <homename>");
				}
			}
		}


		
		return true;
	}
	
	public static Set<String> getHomes(String playername){
        File player_file = new File((new StringBuilder("plugins/SupremeHomes/Player Homes/")).append(playername).append(".yml").toString());
        if(player_file.exists())
        {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(player_file);
            return configuration.getKeys(false);
        } else
        {
            return new HashSet<String>();
        }
    }
	
}
