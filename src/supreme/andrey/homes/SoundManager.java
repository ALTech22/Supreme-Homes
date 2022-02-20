package supreme.andrey.homes;

import java.io.File;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class SoundManager {
	
	static Plugin plugin = SupremeHomes.getPlugin(SupremeHomes.class);
	static File soundsfile = new File (plugin.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), "sounds.yml");
	static FileConfiguration soundsconfig;

	static float f1 = 0.5f;
	static float f2 = 0.5f;
	
	public static void playSoundSetHome(Player player) {
		
		 player.playSound(player.getLocation(), Sound.valueOf(getSoundConfigs().getString("sethome")), f1, f2);
	}
	
	public static void playSoundHome(Player player) {
		 player.playSound(player.getLocation(), Sound.valueOf(getSoundConfigs().getString("home")), f1, f2);
	}
	
	public static void playSoundHomes(Player player) {
		 player.playSound(player.getLocation(), Sound.valueOf(getSoundConfigs().getString("homes")), f1, f2);
	}
	
	public static void playSoundDelhomes(Player player) {
		 player.playSound(player.getLocation(), Sound.valueOf(getSoundConfigs().getString("delhome")), f1, f2);
	}
	
	public static void playSoundError(Player player) {
		player.playSound(player.getLocation(), Sound.valueOf(getSoundConfigs().getString("error")), f1, f2);
	}
	
	public static void playSoundWaitForTp(Player player) {
		player.playSound(player.getLocation(), Sound.valueOf(getSoundConfigs().getString("waitfortp")), f1, f2);
	}
	
	//Soundconfig
	
	public static void genSoundConfig() {
		if(!soundsfile.exists()) {
			plugin.saveResource("sounds.yml", false);
			soundsconfig = YamlConfiguration.loadConfiguration(soundsfile);
		}else {
			reloadSoundConfig();
		}
	}
	
	public static void reloadSoundConfig() {
		soundsconfig = YamlConfiguration.loadConfiguration(soundsfile);
	}
	
	public static FileConfiguration getSoundConfigs() {
		return soundsconfig;
	}
	
	}
