package supreme.andrey.homes.admin.tools.GUIManager;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import supreme.andrey.homes.utils.GUICreator;

public class HomePlayersGui {
	private static ArrayList<String> loadHomesFilesName() {
		File[] directorys = new File(Bukkit.getPluginManager().getPlugin("SupremeHomes2.0").getDataFolder().getPath() + "/homes").listFiles();
		ArrayList<String> fileName = new ArrayList<String>();
		for(File file : directorys) {
			String name = file.getName();
			String[] nameSplit = name.split(".yml");
			fileName.add(nameSplit[0]);
		}
		return fileName;
	}
	
	private static ArrayList<ItemStack> genFileItem() {
		ArrayList<ItemStack> itens = new ArrayList<ItemStack>();
		for(String name : loadHomesFilesName()) {
			ItemStack item = new ItemStack(Material.YELLOW_BED);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			item.setItemMeta(meta);
			itens.add(item);
		}
		return itens;
	}
	
	public static GUICreator genInventory(String title, Integer slots, Player player) {
		
		GUICreator gui = new GUICreator(title, slots, null, player);
		Integer actualSlot = 1;
		for (ItemStack item : genFileItem()) {
			gui.putItemInInventory(actualSlot, item);
			actualSlot+=2;
		}
		
		
		return gui;
	}
	
}
