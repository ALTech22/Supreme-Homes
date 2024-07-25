package supreme.andrey.homes.utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import supreme.andrey.homes.SupremeHomes;
import supreme.andrey.homes.player.tools.HomeTeleport;
import supreme.chatapi.SpigotCallback;

public class SpigotUtils {
	public static void createCancelTeleportCommand(Player player) {
		TextComponent t = new TextComponent(SupremeHomes.getLanguage().getDelayedTeleportMessage("cancel"));
		t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(SupremeHomes.getLanguage().getDelayedTeleportMessage("cancel_hover"))));
		SpigotCallback.createCommand(t, playerCb -> {
			BukkitRunnable playerTeleport = HomeVariables.getTeleportPlayerMap().get(playerCb);
			if (playerTeleport != null) { 
				playerTeleport.cancel();
				HomeVariables.getTeleportPlayerMap().remove(playerCb);
				playerCb.sendMessage(SupremeHomes.getLanguage().getDelayedTeleportMessage("success_cancel"));
			}
		});
		player.spigot().sendMessage(t);
	}
	
	public static void teleportInHomeMessage(Player player, String playerMessage, String homeString) {
		TextComponent t = new TextComponent(playerMessage);
		t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click here to teleport")));
		SpigotCallback.createCommand(t, playerCb -> {

			HomeTeleport.teleportPlayer(player, homeString, SupremeHomes.getPlugin(SupremeHomes.class), player.getName());
			
		});
		player.spigot().sendMessage(t);
	}
}
