package com.nosharp.minisculesystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nosharp.minisculesystem.config.ConfigHelper;
import com.nosharp.minisculesystem.main.Main;
import com.nosharp.minisculesystem.sql.SQLHelper;

import net.md_5.bungee.api.ChatColor;

public class SetRoleCommand implements CommandExecutor{

	private ConfigHelper _configHelper = Main.getPlugin(Main.class).configHelper;
	private SQLHelper _sqlHelper = Main.getPlugin(Main.class).sqlHelper;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length != 2) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',_configHelper.notEnoughArguments));
			return false;
			
		}
		
		if(!sender.hasPermission("minisculesystem.setrole")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', _configHelper.noPermissions));
			return false;
		}
		
		final Player targetPlayer = sender.getServer().getPlayer(args[0]);
		
		if(targetPlayer == null){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', _configHelper.notAPlayer));
			return false;
		}
		String role;
		
		role = args[1];
		_sqlHelper.setPlayerRole(targetPlayer, role);
		
		if(Main.getPlugin(Main.class).activeRoles.containsKey(targetPlayer)) {
			Main.getPlugin(Main.class).activeRoles.remove(targetPlayer);
			
		}
		Main.getPlugin(Main.class).activeRoles.put(targetPlayer, role);
		
		
		targetPlayer.setPlayerListName(Main.getPlugin(Main.class).activeRoles + targetPlayer.getName());
		
		
		
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',_configHelper.setRole));
		
		
		return false;
	}

}
