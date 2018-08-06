package pl.sopelplyt.spin.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import pl.sopelplyt.spin.Main;

public class InventoryClick implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event){
		if(event.getInventory().getName().equals("§cRejestracja") || event.getInventory().getName().equals("§cLogowanie")){
			ItemStack item = event.getCurrentItem();
			if(item != null){
				if(item.getType().equals(Material.STAINED_GLASS_PANE)){
					if(item.hasItemMeta()){
						if(item.getItemMeta().getDisplayName().equals("§2§l§nKlawisz")){
							if(Main.getInstance().pins.containsKey(event.getWhoClicked().getUniqueId())){
								Main.getInstance().pins.put(event.getWhoClicked().getUniqueId(), Main.getInstance().pins.get(event.getWhoClicked().getUniqueId()) + "" + item.getAmount());
							}else{
								Main.getInstance().pins.put(event.getWhoClicked().getUniqueId(), "" + item.getAmount());
							}
						}
					}
				}else if(item.getType().equals(Material.BOOK)){
					if(item.hasItemMeta()){
						if(item.getItemMeta().getDisplayName().equals("§4§l§nZatwierdz")){
							if(event.getInventory().getName().equals("§cRejestracja")){
								if(Main.getInstance().pins.containsKey(event.getWhoClicked().getUniqueId())){
									String pin = Main.getInstance().getHashedString(Main.getInstance().pins.get(event.getWhoClicked().getUniqueId()));
									Main.getInstance().addToConfig("users." + event.getWhoClicked().getUniqueId().toString(), pin);
									Main.getInstance().auth.add(event.getWhoClicked().getUniqueId());
									Main.getInstance().pins.remove(event.getWhoClicked().getUniqueId());
									event.getWhoClicked().closeInventory();
									event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().prefix + " &aZarejestrowano! &2Twoj pin to &6" + pin));
								}
							}else if(event.getInventory().getName().equals("§cLogowanie")){
								if(Main.getInstance().pins.containsKey(event.getWhoClicked().getUniqueId())){
									String pin = Main.getInstance().getHashedString(Main.getInstance().pins.get(event.getWhoClicked().getUniqueId()));
									String truePin = Main.getInstance().getConfig().getString("users." + event.getWhoClicked().getUniqueId().toString());
									if(pin.equals(truePin)){
										Main.getInstance().auth.add(event.getWhoClicked().getUniqueId());
										Main.getInstance().pins.remove(event.getWhoClicked().getUniqueId());
										if(Main.getInstance().fails.containsKey(event.getWhoClicked().getUniqueId())) Main.getInstance().fails.remove(event.getWhoClicked().getUniqueId());
										event.getWhoClicked().closeInventory();
										event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().prefix + " &aZalogowano!"));
									}else{
										Main.getInstance().pins.remove(event.getWhoClicked().getUniqueId());
										if(Main.getInstance().fails.containsKey(event.getWhoClicked().getUniqueId())){
											Main.getInstance().fails.put(event.getWhoClicked().getUniqueId(), Main.getInstance().fails.get(event.getWhoClicked().getUniqueId()) + 1);
										}else{
											Main.getInstance().fails.put(event.getWhoClicked().getUniqueId(), 1);
										}
										if(Main.getInstance().fails.get(event.getWhoClicked().getUniqueId()) >= 3){
											Main.getInstance().fails.remove(event.getWhoClicked().getUniqueId());
											((Player) event.getWhoClicked()).kickPlayer(ChatColor.translateAlternateColorCodes('&', Main.getInstance().prefix + " Wprowadzono nieprawidlowy pin 3 razy"));
										}else{
											event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().prefix + " &cNiepoprawny pin!"));
										}
									}
								}
							}
						}
					}
				}else if(item.getType().equals(Material.FEATHER)){
					if(item.hasItemMeta()){
						if(item.getItemMeta().getDisplayName().equals("§c§l§nPoka¿ pin")){
							if(Main.getInstance().pins.containsKey(event.getWhoClicked().getUniqueId())){
								event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', " &6Wprowadzony pin: &a" + Main.getInstance().pins.get(event.getWhoClicked().getUniqueId())));
							}
						}
					}
				}
			}
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event){
		if(event.getInventory().getName().equals("§cRejestracja") || event.getInventory().getName().equals("§cLogowanie")){
			if(!(Main.getInstance().auth.contains(event.getPlayer().getUniqueId()))){
				((Player)event.getPlayer()).kickPlayer(ChatColor.translateAlternateColorCodes('&', Main.getInstance().prefix + " &4Proces logowania nie powiodl sie!"));
				return;
			}
		}
	}
}
