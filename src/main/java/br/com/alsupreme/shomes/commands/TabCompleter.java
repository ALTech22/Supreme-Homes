package main.java.br.com.alsupreme.shomes.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TabCompleter implements org.bukkit.command.TabCompleter{
	public TabCompleter() {
		
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command com, String lab, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(com.getName().equals("sethome")) {
				if(args.length == 1) {
					List<String> supremehomeList = new ArrayList<String>();
					supremehomeList.add("CompleteWithYourNameHome");
					return supremehomeList;
					
				}
				
		}else if(com.getName().equals("home")) {
					if(args.length == 1) {
						List<String> homes = new ArrayList<String>();
						String playername = player.getName();
						if(Commands.getHomes(playername) == null) {
							return null;
						}else {
							homes.addAll(Commands.getHomes(playername));
							return homes;
						}
					}
					
				}else if(com.getName().equals("delhome")){
					if(args.length == 1) {
						List<String> homes = new ArrayList<String>();
						String playername = player.getName();
						if(Commands.getHomes(playername)== null) {
							return null;
						}else {
							homes.addAll(Commands.getHomes(playername));
							return homes;
						}
					}
				}
			}
		return null;
	}
}
