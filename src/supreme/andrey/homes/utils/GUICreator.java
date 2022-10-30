package supreme.andrey.homes.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import supreme.andrey.homes.SupremeHomes;


public class GUICreator implements Listener{
	private String title;
	private Integer slotQuantity;
	private Map<Integer, ItemStack> item;
	private Player player;
	private Inventory inventory;
	
	public GUICreator(String title, Integer slotQuantity, @Nullable Map<Integer, ItemStack> item, @Nullable Player player) {
		this.title = title;
		this.slotQuantity = slotQuantity;
		this.item = (item == null) ? new HashMap<Integer, ItemStack>() : item;
		this.player = player;
		//if(item == null) this.item = new HashMap<Integer, ItemStack>();
		inventory = Bukkit.createInventory(null, this.slotQuantity, this.title);

	}
	
	public void setItem(Integer slot, ItemStack item) {
		this.item.put(slot, item);
	}
	/**
	 * <p> This method create a new item with the data in paramater </p>
	 * <p> lore can be null </p>
	 ** */
	public void setItem(Integer slot, Material material, @Nullable List<String> lore , String itemDisplayName) {
		ItemStack itemstack = new ItemStack(material);
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setLore(lore);
		itemmeta.setDisplayName(itemDisplayName);
		itemstack.setItemMeta(itemmeta);
		
		this.item.put(slot, itemstack);
	}
	public void putItemInInventory(int slot, ItemStack item) {
		inventory.setItem(slot, item);
	}
	public void putItensInInventory() {
		for(int i=0; i<slotQuantity; i++) {
			if(item.get(i) != null) inventory.addItem(item.get(i));
		}

	}
	public void openInventory() {
		SupremeHomes.playerInventoryEvent.put(player, inventory);
		player.openInventory(inventory); 
	}
	

}
