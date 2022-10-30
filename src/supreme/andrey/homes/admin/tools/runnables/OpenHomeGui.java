package supreme.andrey.homes.admin.tools.runnables;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import supreme.andrey.homes.admin.tools.GUIManager.HomeGui;
import supreme.andrey.homes.utils.GUICreator;

public class OpenHomeGui extends BukkitRunnable{
	
	private Player player;
	private String playerName;
	
	

	public OpenHomeGui(Player player, String playerName) {
		this.player = player;
		this.playerName = playerName;
	}

	@Override
	public void run() {
		System.out.println("teste");
		GUICreator gui = HomeGui.genInventory(playerName, 9, player, playerName, null);
		gui.openInventory();
	}

}
