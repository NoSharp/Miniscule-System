package com.nosharp.minisculesystem.config;

import java.io.File;

import org.bukkit.plugin.Plugin;

import com.nosharp.minisculesystem.main.Main;

public class ConfigHelper {

	
	
	public String hostname;
	public int port;
	public String databaseName;
	public String user;
	public String pass;
	public String defaultRole;
	public String notEnoughArguments;
	public String noPermissions;
	public String notAPlayer;
	public String notARole;
	public String setRole;
	
	
	public void createConfig() {
		Plugin _plugin;
		_plugin = Main.getPlugin(Main.class);
		
		try {
			
			if(!_plugin.getDataFolder().exists()) {
				_plugin.getDataFolder().mkdirs();
			}
			
			File file;
			
			file = new File(_plugin.getDataFolder(),"config.yml");
			
			if(!file.exists()) {
				_plugin.getLogger().info("Creating new config file.");
				
				_plugin.saveDefaultConfig();
				
				_plugin.getLogger().info("Created new config file, please edit the mysql login info.");
			}
			
			this.hostname =_plugin.getConfig().getString("hostname");
			this.port = _plugin.getConfig().getInt("port");
			this.databaseName = _plugin.getConfig().getString("databasename");
			this.user = _plugin.getConfig().getString("user");
			this.pass = _plugin.getConfig().getString("pass");
			this.defaultRole = _plugin.getConfig().getString("defaultrole");
			this.notEnoughArguments = _plugin.getConfig().getString("NotEnoughArguments");
			this.noPermissions = _plugin.getConfig().getString("NoPermissions");
			this.notARole = _plugin.getConfig().getString("NotARole");
			this.notAPlayer = _plugin.getConfig().getString("NotAPlayer");
			this.setRole = _plugin.getConfig().getString("SetRole");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
