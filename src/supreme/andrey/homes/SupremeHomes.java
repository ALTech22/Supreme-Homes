package supreme.andrey.homes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import supreme.api.java.org.bstats.bukkit.Metrics;
import supreme.chatapi.SpigotCallback;
import supreme.api.java.org.bstats.bukkit.Metrics.SimplePie;
import supreme.andrey.homes.admin.tools.events.Events;
import supreme.andrey.homes.events.EconomyEvents;
import supreme.andrey.homes.permission.Permission;
import supreme.andrey.homes.permission.PermissionManager;
import supreme.andrey.homes.player.tools.Commands;
import supreme.andrey.homes.player.tools.HomeTabCompleter;
import supreme.andrey.homes.utils.Language;

public class SupremeHomes extends JavaPlugin {
	
	public static HashMap<Player, Inventory> playerAdminInventoryEvent = new HashMap<Player, Inventory>();
	public static HashMap<Player, Inventory> playerInventoryEvent = new HashMap<Player, Inventory>();
	private static PermissionManager permissions;
	private static Language lang;
	 
	@Override
	public void onEnable() {
		this.saveResource("config.yml", false);
		boolean active_economy = this.getConfig().getBoolean("active_economy");
		
		
		//Metrics bstats
		if(this.getConfig().getBoolean("Active_Bstats_Metrics")) {
			Metrics metrics = new Metrics(this, 10590);
			metrics.addCustomChart(new SimplePie("economy", new Callable<String>() {
				
				@Override
				public String call() throws Exception {
					return String.valueOf(active_economy);
				}
			}));
		}
		
		SoundManager.genSoundConfig();
		permissions = new PermissionManager(this);
		registerCommands();
		this.getServer().getPluginManager().registerEvents(new Events(this), this);
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED +   "|===============================|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|===Supreme Homes 2=============|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|========="+ this.getDescription().getVersion() +"=================|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD  + "|===Carregado com sucesso=======|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD  + "|===By: Andrey H. (Supremeyerd)=|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED +   "|===============================|");
		
		new UpdateChecker(this, 88025).getVersion(Version -> {
			System.out.println(Version);
			if (this.getDescription().getVersion().equalsIgnoreCase(Version)){
			this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8No update avaible for &bSupreme &aHomes 2, &6Current version: &f " + Version));
		}else {
			this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Update avaible for &bSupreme &aHomes 2, &6New version: &f" + Version));
		}
		});
		
		if (active_economy) {
			if (getServer().getPluginManager().getPlugin("Vault") == null) 
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Vault plugin not found, please install it if using economy"));
			// economy events
			else this.getServer().getPluginManager().registerEvents(new EconomyEvents(this), this);
			
		}
		
		
		new SpigotCallback(this);
		lang = new Language(this.getConfig().getString("lang"), this);
		super.onEnable();
	}
	
	public void registerCommands() {
		Commands com = new Commands();
		HomeTabCompleter tabComp = new HomeTabCompleter(this);
		
		this.getCommand("sethome").setExecutor(com);
		this.getCommand("sethome").setTabCompleter(new TabCompleter() {
			
			@Override
			public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
				List<String> list = new ArrayList<String>();
				list.add("homeName");
				return list;
			}
		});
		this.getCommand("homes").setExecutor(com);
		this.getCommand("homes").setTabCompleter(tabComp);
		this.getCommand("home").setExecutor(com);
		this.getCommand("delhome").setExecutor(com);
		this.getCommand("home").setTabCompleter(tabComp);
		this.getCommand("delhome").setTabCompleter(tabComp);
		
		this.getCommand("setpublic").setExecutor(com);
		this.getCommand("setpublic").setTabCompleter(tabComp);
		this.getCommand("unsetpublic").setExecutor(com);
		this.getCommand("unsetpublic").setTabCompleter(tabComp);
		
		/*this.getCommand("test").setExecutor(new CommandExecutor() {

			
			@Override
			public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
				
				PermissionManager pm = new PermissionManager((Player) arg0, SupremeHomes.getPlugin(SupremeHomes.class)); 
				pm.getPlayerPermission();
				return false;
			}
		});*/
		
		this.getCommand("show").setExecutor(new supreme.andrey.homes.admin.tools.Commands(this));
		}
	
	/*public void testes() {
		this.getConfig().set("tururu", true);
		this.saveConfig();
		this.reloadConfig();
		if(this.getConfig().getBoolean("tururu")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "it's works! Default edition");
		}
		
		Configuration config = new Configuration(this, "homes/", "Zuma22", false);
		config.getConfig().set("turururu", true);
		config.saveConfig();
		config.reloadConfig();
		if(config.getConfig().getBoolean("turururu")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "it's works!");
		}
	}*/
	
	public static Language getLanguage() {
		return lang;
	}
	public static Permission getPermissions(Player player) {
		return permissions.getPlayerPermission(player);
	}
	
}