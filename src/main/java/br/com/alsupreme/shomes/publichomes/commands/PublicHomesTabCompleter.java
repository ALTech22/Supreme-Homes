package main.java.br.com.alsupreme.shomes.publichomes.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class PublicHomesTabCompleter implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command com, String lab, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String playername = player.getName();
			
			if(com.getName().equals("setpublichome")) {
				if(args.length == 1) {
					List<String> tabSupremeHomes = new ArrayList<String>();
					tabSupremeHomes.add("Complete with name of home");
					return tabSupremeHomes;
				}
			}
			
			if(com.getName().equals("delpublichome")) {
				if(args.length == 1) {
					List<String> TabSupremeHomes = new ArrayList<String>();
					if(PublicHomesCommands.getHomes(playername)==null) {
						return null;
					}else {
						TabSupremeHomes.addAll(PublicHomesCommands.getHomes(playername));
						return TabSupremeHomes;
					}
				}
			}
			
			if(com.getName().equals("publichome")) {
				
				
				if(args.length == 2) {
					playername = args[0];
					
					List<String> homes = new ArrayList<String>();
					if(PublicHomesCommands.getHomes(playername)==null) {
						return null;
					}else {
						homes.addAll(PublicHomesCommands.getHomes(playername));
						return homes;
						
						
					}
				}
			}
		}
		return null;
	}
		
}
