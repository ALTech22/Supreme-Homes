package supreme.andrey.homes.events.definitions;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnPlayerSetNewHome extends Event implements Cancellable {
	
	private static HandlerList handlers = new HandlerList();
	private Player player;
	private boolean cancelled = false;
	
	public OnPlayerSetNewHome(Player player) {
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

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
		
	}

}
