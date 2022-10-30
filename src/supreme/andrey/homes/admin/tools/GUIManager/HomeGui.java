package supreme.andrey.homes.admin.tools.GUIManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import supreme.andrey.homes.utils.GUICreator;

public class HomeGui {
	
	private static Map<String, ArrayList<String>> loadHomesData(String playerName) {
		File fileHomes = new File(Bukkit.getPluginManager().getPlugin("SupremeHomes2.0").getDataFolder().getPath(), "homes/" + playerName + ".yml");
		FileConfiguration fcHomes = YamlConfiguration.loadConfiguration(fileHomes);
		Map<String, ArrayList<String>> homeData = new HashMap<String, ArrayList<String>>();
		Set<String> homesSet = fcHomes.getKeys(false);
		
		for(String home : homesSet) {
			ArrayList<String> homesData = new ArrayList<String>();
			homesData.add(fcHomes.getString(home + ".world"));
			homesData.add(String.valueOf(fcHomes.getInt(home + ".x")));
			homesData.add(String.valueOf(fcHomes.getInt(home + ".y")));
			homesData.add(String.valueOf(fcHomes.getInt(home + ".z")));
			homesData.add(String.valueOf(fcHomes.get(home + ".public")));
			homeData.put(home, homesData);
		}
		
		
		return homeData;
	}
	
	private static ArrayList<ItemStack> genFileItem(String playerName) {
		ArrayList<ItemStack> itens = new ArrayList<ItemStack>();
		Map<String, ArrayList<String>> homes = loadHomesData(playerName);
		Set<String> homeName = homes.keySet();
		for(String name : homeName) {
			
			String world = homes.get(name).get(0);
			String x = homes.get(name).get(1);
			String y = homes.get(name).get(2);
			String z = homes.get(name).get(3);
			Boolean isPublic = Boolean.valueOf(homes.get(name).get(4));
			ItemStack item = null;
			
			if(isPublic)
				item = new ItemStack(Material.GREEN_BED);
			else
				item = new ItemStack(Material.RED_BED);
			
			ItemMeta m = item.getItemMeta();
			m.setDisplayName(name);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("World: " + world);
			lore.add("x: " + x);
			lore.add("y: " + y);
			lore.add("z: " + z);
			if (isPublic)
				lore.add("Public Home");
			else
				lore.add("Private Home");
			m.setLore(lore);
			
			item.setItemMeta(m);
			
			itens.add(item);
		}
		return itens;
	}
	
	public static GUICreator genInventory(String title, Integer slots, Player player, String playerName, @Nullable Integer arg) {
		GUICreator gui = new GUICreator(title, slots, null, player);
		Integer actualSlot = 0;
		ArrayList<ItemStack> itens = genFileItem(playerName);
		
		Integer pageToShow = arg;


		System.out.println(pageToShow);
		System.out.println(itens.size());
		
		// Renders the previous page button
		if (arg > 1) {
			ItemStack nextPageItem = new ItemStack(Material.ACACIA_SIGN);
			ItemMeta m = nextPageItem.getItemMeta();
			m.setDisplayName("Previous page");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Current page: " + String.valueOf(pageToShow));
			m.setLore(lore);
			nextPageItem.setItemMeta(m);
			gui.putItemInInventory(actualSlot, nextPageItem);
			actualSlot+=2;
		}
		
		int i = arg == 1 ? 0 : (arg-1)*(9*6-2);
		while (i < itens.size()) {

			
			if (actualSlot >= 9*6) 
				break;
				
			if (actualSlot < 9*6-2) {
				gui.putItemInInventory(actualSlot, itens.get(i));
				actualSlot+=1;
			} else {
				ItemStack nextPageItem = new ItemStack(Material.ACACIA_SIGN);
				ItemMeta m = nextPageItem.getItemMeta();
				m.setDisplayName("Next page");
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("Current page: " + String.valueOf(arg));
				m.setLore(lore);
				nextPageItem.setItemMeta(m);
				gui.putItemInInventory(actualSlot+1, nextPageItem);
			}
			
			
			
			i+=1;
		}
		
		/*for (ItemStack item : itens) {
			
			if (actualSlot < 9*6) {
			
				if (actualSlot < 9*6-2) {
					gui.putItemInInventory(actualSlot, item);
					actualSlot+=1;
				} else {
					ItemStack nextPageItem = new ItemStack(Material.ACACIA_SIGN);
					ItemMeta m = nextPageItem.getItemMeta();
					m.setDisplayName("Next page");
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("Current page: " + String.valueOf(arg+1));
					m.setLore(lore);
					nextPageItem.setItemMeta(m);
					gui.putItemInInventory(actualSlot+1, nextPageItem);
				}
			}
		}*/

		//genFileItem(playerName);
		
		
		return gui;
	}
}
