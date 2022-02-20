package supreme.andrey.homes.player.tools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command com, String lab, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(com.getName().equals("sethome"))
				Home.setHome(player, args);
			else if(com.getName().equals("homes"))
				Home.homes(player);
			else if(com.getName().equals("home"))
				Home.home(player, args);
			else if(com.getName().equals("delhome"))
				Home.delHome(player, args);
			
		}
		return true;
	}

}
