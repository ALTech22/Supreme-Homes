package supreme.andrey.homes;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Configuration {
	private File file;
	private FileConfiguration fileConfiguration;
	private Plugin plugin;
	private String filePath;
	private String fileName;
	private boolean isPreGenerated;
	
	public Configuration(Plugin plugin, String filePath, String fileName, boolean ispregenerated) {
		this.plugin = plugin;
		this.filePath = filePath;
		this.isPreGenerated = ispregenerated;
		this.fileName = fileName + ".yml"; 
		
		file = new File(plugin.getDataFolder(), this.filePath + "/" + this.fileName);
		genFile();
		reloadConfig();
	}
	
	public void saveConfig() {
		try {
			fileConfiguration.save(file);
			reloadConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reloadConfig() {
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
	}
	
	public FileConfiguration getConfig() {
		return fileConfiguration;
	}
	
	private void genFile() {
		if(!(file.exists())) {
			if(isPreGenerated) {
				
				plugin.saveResource(filePath, false);
			}else {
				try {
					new File(plugin.getDataFolder(), this.filePath).mkdirs();
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
