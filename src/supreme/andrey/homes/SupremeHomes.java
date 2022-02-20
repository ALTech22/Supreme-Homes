package supreme.andrey.homes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import supreme.andrey.homes.player.tools.Commands;
import supreme.andrey.homes.player.tools.HomeTabCompleter;

public class SupremeHomes extends JavaPlugin{
	
	@Override
	public void onEnable() {
		this.saveResource("config.yml", false);
		SoundManager.genSoundConfig();
		
		registerCommands();
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED +   "|========================|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|===Supreme Homes 2.0====|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|==="+ this.getDescription().getVersion() +"=======|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD  + "|===Carregado com sucesso|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED +   "|========================|");
		
		new UpdateChecker(this, 88025).getVersion(Version -> {
			if (this.getDescription().getVersion().equalsIgnoreCase(Version)){
			this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8No update avaible for &bSupreme &aHomes 2, &6Current version: &f " + Version));
		}else {
			this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Update avaible for &bSupreme &aHomes 2, &6New version: &f" + Version));
		}
		});
		super.onEnable();
	}
	
	public void registerCommands() {
		this.getCommand("sethome").setExecutor(new Commands());
		this.getCommand("sethome").setTabCompleter(new TabCompleter() {
			
			@Override
			public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
				List<String> list = new ArrayList<String>();
				list.add("homeName");
				return list;
			}
		});
		this.getCommand("homes").setExecutor(new Commands());
		this.getCommand("home").setExecutor(new Commands());
		this.getCommand("delhome").setExecutor(new Commands());
		this.getCommand("home").setTabCompleter(new HomeTabCompleter());
		this.getCommand("delhome").setTabCompleter(new HomeTabCompleter());
		}
	
	public void testes() {
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
	}
	
}