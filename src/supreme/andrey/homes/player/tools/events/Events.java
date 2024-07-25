package supreme.andrey.homes.player.tools.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import supreme.andrey.homes.SupremeHomes;
import supreme.andrey.homes.admin.tools.GUIManager.HomeGui;
import supreme.andrey.homes.utils.GUICreator;

public class Events implements Listener{
	
	private SupremeHomes sh;
	
	public Events(SupremeHomes sh) {
		this.sh = sh;
	}
	
	@EventHandler
	public void inventoryEvent(InventoryClickEvent e) {
		
		if (e.getCurrentItem() == null)
			return;
		
		Player player = (Player) e.getWhoClicked();
		if (SupremeHomes.playerInventoryEvent.containsKey(player)) {
			if(e.getInventory().firstEmpty() == 0) {
				player.closeInventory();
				String playerName = e.getCurrentItem().getItemMeta().getDisplayName();
				GUICreator gui = HomeGui.genInventory(playerName, 9*6, player, playerName, 1, sh);
				gui.openInventory(false);
			}
			
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Next Page")) {
				player.closeInventory();
				String playerName = player.getName();
				String CPage = e.getCurrentItem().getItemMeta().getLore().get(0);
				CPage = CPage.substring(14);
				Integer currentPage = Integer.valueOf(CPage);
				GUICreator gui = HomeGui.genInventory(playerName, 9*6, player, playerName, currentPage+1, sh);

				gui.openInventory(false);
			} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Previous page")) {
				player.closeInventory();
				String playerName = player.getName();
				String CPage = e.getCurrentItem().getItemMeta().getLore().get(0);
				CPage = CPage.substring(14);
				Integer currentPage = Integer.valueOf(CPage);
				GUICreator gui = HomeGui.genInventory(playerName, 9*6, player, playerName, currentPage-1, sh);

				gui.openInventory(false);
			}
			
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void closeInventoryEvent(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		
		if (SupremeHomes.playerInventoryEvent.containsKey(player)) {
			SupremeHomes.playerInventoryEvent.remove(player);
		}
	}
}
