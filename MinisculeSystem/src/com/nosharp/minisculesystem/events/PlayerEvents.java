package com.nosharp.minisculesystem.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.nosharp.minisculesystem.main.Main;
import com.nosharp.minisculesystem.sql.SQLHelper;

public class PlayerEvents implements Listener{

	
	private SQLHelper _sqlHelper = Main.getPlugin(Main.class).sqlHelper;

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player;
		String role;
		
		player = event.getPlayer();
		
		_sqlHelper.executePlayerSQLCheck(player);
		_sqlHelper.checkPlayerName(player);
		if(!Main.getPlugin(Main.class).activeRoles.containsKey(player)) {
			role = _sqlHelper.getPlayerRole(player);
			Main.getPlugin(Main.class).activeRoles.put(player, role);
		}
		
		
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		final Player player;
		player = event.getPlayer();
		if(Main.getPlugin(Main.class).activeRoles.containsKey(player)) {
			event.setFormat(ChatColor.translateAlternateColorCodes('&', "<" + Main.getPlugin(Main.class).activeRoles.get(player) +" " + player.getName() + "> " +event.getMessage()));
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {

		final Player player;
		player = event.getPlayer();
		if(Main.getPlugin(Main.class).activeRoles.containsKey(player)) {
			Main.getPlugin(Main.class).activeRoles.remove(player);
		}
	}
	
}
