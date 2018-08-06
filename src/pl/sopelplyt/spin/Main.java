package pl.sopelplyt.spin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import pl.sopelplyt.spin.listeners.InventoryClick;
import pl.sopelplyt.spin.listeners.JoinListener;
import pl.sopelplyt.spin.listeners.PlayerQuit;

public class Main extends JavaPlugin{
	static Main instance;
	public List<UUID> auth = new ArrayList<UUID>();
	public Map<UUID,String> pins = new HashMap<UUID, String>();
	public Map<UUID,Integer> fails = new HashMap<UUID, Integer>();
	public String prefix;
	
	@Override
	public void onEnable(){
		instance = this;
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
		prefix = this.getConfig().getString("prefix");
	}
	
	@Override
	public void onDisable(){
		int i = 0;
		for(UUID uuid : auth){
			Player p = Bukkit.getPlayer(uuid);
			p.kickPlayer("§2Wylogowano z serwera!");
			i++;
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &aWylogowano &6" + i + " &agraczy"));
	}
	
	public void addToConfig(String path, String text){
		this.getConfig().set(path, text);
		this.saveConfig();
		return;
	}
	
	public String getHashedString(String string){
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(string.getBytes(),0,string.length());
			return new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Main getInstance(){ return instance; }
}