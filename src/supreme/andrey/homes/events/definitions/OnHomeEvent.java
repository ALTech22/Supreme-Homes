package supreme.andrey.homes.events.definitions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnHomeEvent extends Event {
	
	private static HandlerList handlers = new HandlerList();
	private Player player;
	
	public OnHomeEvent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static HandlerList getHandlerList() {
		// TODO Auto-generated method stub
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

}
