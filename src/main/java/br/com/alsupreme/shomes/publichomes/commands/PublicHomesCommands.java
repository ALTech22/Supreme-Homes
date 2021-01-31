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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.br.com.alsupreme.shomes.SoundManager;
import main.java.br.com.alsupreme.shomes.SupremeHomes;
import main.java.br.com.alsupreme.shomes.publichomes.config.PublicHomesConfig;
import net.md_5.bungee.api.ChatColor;

public class PublicHomesCommands implements CommandExecutor{
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command com, String lab, String[] args) {
		
		Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
		Player player = (Player) sender;
		String playername = player.getPlayer().getName();
		HashMap<Boolean, String> savehome = new HashMap<Boolean, String>(); //HashMap<Is Teleport not Possible?, Reason>
		FileConfiguration config = PublicHomesConfig.loadConfig(playername);
		
		if(sender instanceof Player) {
			
			if(com.getName().equals("setphome")) {
				if(player.hasPermission("suphomes.public.sethome") || player.hasPermission("suphomes.playerusepublic")) {
					if(args.length == 1) {
						
						for(String DW : plugin.getConfig().getStringList("Disabled_Worlds")) {
							if(player.getLocation().getWorld().getName().equals(DW)) {
								savehome.put(false, "Impossible set home in this world");
							}
							
						}
						
						int MaxHomes;
						if(!plugin.getConfig().getBoolean("activate_limits")) {
	
								
							config.set(args[0] + "." + "world", player.getLocation().getWorld().getName());
							config.set(args[0] + "." + "X", (int)player.getLocation().getX());
							config.set(args[0] + "." + "Y", (int)player.getLocation().getY());
							config.set(args[0] + "." + "Z", (int)player.getLocation().getZ());
								
						}else if(config.contains(args[0]) && plugin.getConfig().getBoolean("substitution_of_homes")) {
							
							config.set(args[0] + "." + "world", player.getLocation().getWorld().getName());
							config.set(args[0] + "." + "X", (int)player.getLocation().getX());
							config.set(args[0] + "." + "Y", (int)player.getLocation().getY());
							config.set(args[0] + "." + "Z", (int)player.getLocation().getZ());
							
						}else if(!player.hasPermission("suphomes.vip.tier1") && !player.hasPermission("suphomes.vip.tier2") && !player.hasPermission("suphomes.vip.tier3") && !player.hasPermission("suphomes.vip.tier4")){
							if(!(getHomes(playername) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(playername));
								
								MaxHomes = plugin.getConfig().getInt("vipsLimits.normalPlayer.limitHomes");
								if(Homelist.size() < MaxHomes) {
									
									config.set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									config.set(args[0] + "." +"X", (int)player.getLocation().getX());
									config.set(args[0] + "." +"Y", (int)player.getLocation().getY());
									config.set(args[0] + "." +"Z", (int)player.getLocation().getZ());
									
						
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
							
							if(!(getHomes(playername) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(playername));

								MaxHomes = plugin.getConfig().getInt("vipsLimits.viptier1.limitHomes");
								if(Homelist.size() < MaxHomes) {
									config.set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									config.set(args[0] + "." +"X", (int)player.getLocation().getX());
									config.set(args[0] + "." +"Y", (int)player.getLocation().getY());
									config.set(args[0] + "." +"Z", (int)player.getLocation().getZ());
									
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
							
							if(!(getHomes(playername) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(playername));

								MaxHomes = plugin.getConfig().getInt("vipsLimits.viptier2.limitHomes");
								if(Homelist.size() < MaxHomes) {
									config.set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									config.set(args[0] + "." +"X", (int)player.getLocation().getX());
									config.set(args[0] + "." +"Y", (int)player.getLocation().getY());
									config.set(args[0] + "." +"Z", (int)player.getLocation().getZ());
									
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
							
							if(!(getHomes(playername) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(playername));

								MaxHomes = plugin.getConfig().getInt("vipsLimits.viptier3.limitHomes");
								if(Homelist.size() < MaxHomes) {
									config.set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									config.set(args[0] + "." +"X", (int)player.getLocation().getX());
									config.set(args[0] + "." +"Y", (int)player.getLocation().getY());
									config.set(args[0] + "." +"Z", (int)player.getLocation().getZ());
									
									
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
							
							if(!(getHomes(playername) == null)) {
								List<String> Homelist = new ArrayList<String>();
								Homelist.addAll(getHomes(playername));

								MaxHomes = plugin.getConfig().getInt("vipsLimits.viptier4.limitHomes");
								if(Homelist.size() < MaxHomes) {
									config.set(args[0] + "." + ".world", player.getLocation().getWorld().getName());
									config.set(args[0] + "." +"X", (int)player.getLocation().getX());
									config.set(args[0] + "." +"Y", (int)player.getLocation().getY());
									config.set(args[0] + "." +"Z", (int)player.getLocation().getZ());

									
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
							String msg = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_define_a_public_home"));
							msg = msg.replace("{publichome}", args[0]);
							
							player.sendMessage(msg);
							
							config.options().copyDefaults(true);
							PublicHomesConfig.saveConfig();
							
							SoundManager.playSoundSetHome(player);
						
						}else {
							SoundManager.playSoundError(player);
							if(!(savehome.get(false) == "Require more vip")) {player.sendMessage(savehome.get(false));}
						}

					}else {
						player.sendMessage("Your not define a home name, this correct use is /sethome <homename>");
					}
				}
			}
			
			if(com.getName().equals("phome")){
				if(player.hasPermission("suphomes.public.home") || player.hasPermission("suphomes.playerusepublic")) {
					if(args.length == 2) {
						FileConfiguration pfileconfig = PublicHomesConfig.loadConfig(args[0]);
						
						String nameworld = pfileconfig.getString(args[1] + "." + "world");
						World world = Bukkit.getServer().getWorld(nameworld);
						int X = pfileconfig.getInt(args[1] + "." + "X");
						int Y = pfileconfig.getInt(args[1] + "." + "Y");
						int Z = pfileconfig.getInt(args[1] + "." + "Z");
						
						Location loc = new Location(world, X, Y, Z);
						
						
						
						
						if(plugin.getConfig().getBoolean("Delay_teleports")) {

							int CD = 5;
							HashMap<Player, Integer> counttime = new HashMap<Player, Integer>();
							HashMap<Player, BukkitRunnable> counttask = new HashMap<Player, BukkitRunnable>();
							
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
							String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_teleport_public_home"));
							message = message.replace("{player}", playername);
							message = message.replace("{publichome}", args[1]);
							message = message.replace("{homeplayer}", args[0]);
							
							player.sendMessage(message);
							
							SoundManager.playSoundHome(player);
						}
					}else {
						player.sendMessage("The correct use of this command is /phome <nameofplayer> <homename>");
						SoundManager.playSoundError(player);
					}
				}
			}
			
			if(com.getName().equals("phomes")) {
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
						player.sendMessage("the correct use of this command is /phomes <nameofplayer>");
						SoundManager.playSoundError(player);
					}
				}
			}
			if(com.getName().equals("delphome")) {
				if(player.hasPermission("suphomes.public.delhome") || player.hasPermission("suphomes.playerusepublic")) {
					if(args.length == 1) {
						config.options().configuration().set(args[0], null);
						PublicHomesConfig.saveConfig();
						
						String message = ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().get("messages.on_player_delpublichome"));
						message = message.replace("{publichome}", args[0]);
						player.sendMessage(message);
						SoundManager.playSoundDelhomes(player);
					}else {
						player.sendMessage("the correct use of this command is /delphome <homename>");
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
	

}
