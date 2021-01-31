package main.java.br.com.alsupreme.shomes;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public abstract class SoundManager {
	
	static Sound home = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
	static Sound homes = Sound.BLOCK_BEEHIVE_ENTER;
	static Sound sethome = Sound.ENTITY_PLAYER_LEVELUP;
	static Sound delhome = Sound.BLOCK_NETHERITE_BLOCK_BREAK; 
	static Sound error = Sound.ENTITY_PLAYER_BIG_FALL;
	static Sound waitfortp = Sound.BLOCK_SOUL_SOIL_BREAK;
	
	static float f1 = 0.5f;
	static float f2 = 0.5f;
	
	public static void playSoundSetHome(Player player) {
		
		 player.playSound(player.getLocation(), sethome, f1, f2);
	}
	
	public static void playSoundHome(Player player) {
		 player.playSound(player.getLocation(), home, f1, f2);
	}
	
	public static void playSoundHomes(Player player) {
		 player.playSound(player.getLocation(), homes, f1, f2);
	}
	
	public static void playSoundDelhomes(Player player) {
		 player.playSound(player.getLocation(), delhome, f1, f2);
	}
	
	public static void playSoundError(Player player) {
		player.playSound(player.getLocation(), error, f1, f2);
	}
	
	public static void playSoundWaitForTp(Player player) {
		player.playSound(player.getLocation(), waitfortp, f1, f2);
	}
	
	//Soundconfig
}
