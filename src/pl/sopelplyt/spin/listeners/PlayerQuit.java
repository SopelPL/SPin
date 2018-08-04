package pl.sopelplyt.spin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import pl.sopelplyt.spin.Main;

public class PlayerQuit implements Listener{

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		if(Main.getInstance().auth.contains(event.getPlayer().getUniqueId())){
			Main.getInstance().auth.remove(event.getPlayer().getUniqueId());
		}
	}
}
