package pl.sopelplyt.spin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.sopelplyt.spin.Main;

public class JoinListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		if(!(Main.getInstance().auth.contains(event.getPlayer().getUniqueId()))){
			Inventory inv = Bukkit.createInventory(null, 27, (Main.getInstance().getConfig().getString("users." + event.getPlayer().getUniqueId().toString()) == null) ? "§cRejestracja" : "§cLogowanie" );
			ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta im = pane.getItemMeta();
			im.setDisplayName("§2§l§nKlawisz");
			pane.setItemMeta(im);
			
			ItemStack book = new ItemStack(Material.BOOK, 1);
			ItemMeta im_book = book.getItemMeta();
			im_book.setDisplayName("§4§l§nZatwierdz");
			book.setItemMeta(im_book);
			
			ItemStack feather = new ItemStack(Material.FEATHER, 1);
			ItemMeta im_feather = feather.getItemMeta();
			im_feather.setDisplayName("§c§l§nPoka¿ pin");
			feather.setItemMeta(im_feather);
			
			inv.setItem(11, feather);
			inv.setItem(16, book);
			inv.setItem(3, pane);
			pane.setAmount(2);
			inv.setItem(4, pane);
			pane.setAmount(3);
			inv.setItem(5, pane);
			pane.setAmount(4);
			inv.setItem(12, pane);
			pane.setAmount(5);
			inv.setItem(13, pane);
			pane.setAmount(6);
			inv.setItem(14, pane);
			pane.setAmount(7);
			inv.setItem(21, pane);
			pane.setAmount(8);
			inv.setItem(22, pane);
			pane.setAmount(9);
			inv.setItem(23, pane);
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable(){
				public void run(){
					event.getPlayer().openInventory(inv);
				}
			}, 20);
			return;
		}
	}
}