package com.nosharp.minisculesystem.main;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.nosharp.minisculesystem.commands.SetRoleCommand;
import com.nosharp.minisculesystem.config.ConfigHelper;
import com.nosharp.minisculesystem.events.PlayerEvents;
import com.nosharp.minisculesystem.sql.SQLHelper;

public class Main extends JavaPlugin {

	
	public SQLHelper sqlHelper = new SQLHelper();
	public ConfigHelper configHelper = new ConfigHelper();
	
	public HashMap<Player, String> activeRoles = new HashMap<Player, String>();
	
	@Override
	public void onEnable() {
		Logger logger = getLogger();
		logger.info("Started initialization proccess.");
		
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		getCommand("setrole").setExecutor(new SetRoleCommand());
		
		configHelper.createConfig();
		sqlHelper.setup();
		sqlHelper.establishConnection();
		sqlHelper.tableCheck();
		
		logger.info("Finished initialization proccess.");
	}

	
}
