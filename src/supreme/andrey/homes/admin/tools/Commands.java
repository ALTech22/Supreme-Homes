package supreme.andrey.homes.admin.tools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import supreme.andrey.homes.SupremeHomes;
import supreme.andrey.homes.admin.tools.GUIManager.HomeGui;
import supreme.andrey.homes.admin.tools.GUIManager.HomePlayersGui;
import supreme.andrey.homes.utils.GUICreator;

public class Commands implements CommandExecutor{
	
	private SupremeHomes sh;
	
	

	public Commands(SupremeHomes sh) {
		this.sh = sh;
	}



	@Override
	public boolean onCommand(CommandSender sender, Command com, String lab, String[] args) {
		if(com.getName().equals("show")) {
			if(args.length == 0) {
				GUICreator gui = HomePlayersGui.genInventory("homes", 54, (Player) sender, sh);
				gui.openInventory(true);
				return true;
			} else {
				GUICreator gui = HomeGui.genInventory(args[0], 54, (Player) sender, args[0], 1, sh);
				gui.openInventory(true);
				return true;
			}
		}
		
		return false;
	}

}
