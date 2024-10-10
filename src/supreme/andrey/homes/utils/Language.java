package supreme.andrey.homes.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.Configuration;

public class Language {
	private String lang;
	private String publicHome = "home.public.";
	private String home = "home.";
	private String messages = "messages.";
	private String economy = "economy.";
	private String teleport = "delayed_teleport";
	private Configuration langFile;
	
	public Language(String lang, Plugin plugin) {
		this.setLang(lang);
		langFile = new Configuration(plugin, "lang", lang, true);
	}
	
	private String textGenerator(String path, String[] variables, String[] values) {
		String message = langFile.getConfig().getString(path);

		for (int i=0; i<variables.length; i++) {
			message = message.replace(variables[i], values[i]);
		}
		return message;
	}
	
	public String getHomeMessage(String path, String[] variables, String[] values) {
		String message = textGenerator(home+"."+path, variables, values);
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	public String getHomeMessage(String path) {
		String message = langFile.getConfig().getString(home+"."+path);
		if (message == null) return null;
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String getHomePublicMessage(String path, String[] variables, String[] values) { 
		String message = textGenerator(publicHome+"."+path, variables, values);
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String getHomePublicMessage(String path) { 
		String message = langFile.getConfig().getString(publicHome+"."+path);
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String getEconomyMessage(String path, String[] variables, String[] values) { 
		String message = textGenerator(economy+"."+path, variables, values);
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String getEconomyMessage(String path) { 
		String message = langFile.getConfig().getString(economy+"."+path);
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String getDelayedTeleportMessage(String path, String[] variables, String[] values) { 
		String message = textGenerator(teleport+"."+path, variables, values);
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String getDelayedTeleportMessage(String path) { 
		String message = langFile.getConfig().getString(teleport+"."+path);
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String getGlobalMessage(String path, String[] variables, String[] values) {
		String message = textGenerator(messages+"."+path, variables, values);
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
}
