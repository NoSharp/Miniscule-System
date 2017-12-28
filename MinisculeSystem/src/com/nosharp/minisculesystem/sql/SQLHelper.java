package com.nosharp.minisculesystem.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

import com.nosharp.minisculesystem.config.ConfigHelper;
import com.nosharp.minisculesystem.main.Main;

public class SQLHelper{

	public Connection connection;
	public String hostname;
	public int portNumber;
	public String dbName;
	public String user;
	public String pass;	
	
	public void setup() {
		ConfigHelper configHelper;
		configHelper = Main.getPlugin(Main.class).configHelper;
		this.hostname = configHelper.hostname;
		this.portNumber = configHelper.port;
		this.dbName = configHelper.databaseName;
		this.user = configHelper.user;
		this.pass = configHelper.pass;
	}
	
	public void establishConnection(){
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" 
			+ this.hostname + ":" 
			+ this.portNumber + "/"
			+ this.dbName, 
			this.user,
			this.pass);
				
			
		} catch(Exception e){
			e.printStackTrace();
			
		}
		
	}

	
	public void tableCheck() {
		
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS playerData(`ID` VARCHAR(255) PRIMARY KEY NOT NULL, `NAME` VARCHAR(255) NOT NULL, `ROLE` VARCHAR(255) NOT NULL);");
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getPlayerName(Player player) {
		
		String playerUUID;
		ResultSet results;
		Statement statement;
		
		playerUUID = player.getUniqueId().toString();
		try{
			statement = connection.createStatement();
			
			results = statement.executeQuery("SELECT * FROM playerData WHERE ID='" + playerUUID + "';");
			results.next();
			return String.valueOf(results.getString("NAME")).toString();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}	
	
	public void checkPlayerName(Player player) {
		String sqlName;
		String currentName;
	
		sqlName = getPlayerName(player);
		currentName = player.getDisplayName();
		
		if(sqlName == currentName) {
			return;
		}else {
			executeNameChange(player);
		}
	}
	
	public void executeNameChange(Player player) {
		String targetName;
		String playerUUID;
		Statement statement;
		
		try {
			targetName = player.getDisplayName();
			playerUUID = player.getUniqueId().toString();
			statement = connection.createStatement();

			statement.execute("UPDATE playerData SET NAME='" + targetName +"' WHERE ID='" + playerUUID + "'");
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void executePlayerSQLCheck(Player player) {

		ConfigHelper configHelper;
		configHelper = Main.getPlugin(Main.class).configHelper;
		String playerUUID;
		String playerName;
		PreparedStatement statement;
		ResultSet results;
		
		try{
			playerUUID = player.getUniqueId().toString();
			playerName = player.getDisplayName();
			statement = connection.prepareStatement("SELECT * from playerData WHERE ID='" + playerUUID +  "'");
			statement.executeQuery();
			results = statement.getResultSet();
			
			if(results.next()){
				return;
			}else {
			
				PreparedStatement statement2;
				statement2 = connection.prepareStatement("INSERT INTO playerData (ID, NAME, ROLE) VALUES (?,?,?)");
				statement2.setString(1, playerUUID);
				statement2.setString(2, playerName);
				statement2.setString(3, configHelper.defaultRole);
				statement2.execute();
			}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void setPlayerRole(Player player, String role) {
		String playerUUID;
		Statement statement;
		
		try{
			playerUUID = player.getUniqueId().toString();
			statement = connection.createStatement();
			statement.execute("UPDATE playerData SET ROLE='" + role +"' WHERE ID='" + playerUUID + "'");
			statement.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getPlayerRole(Player player) {
		
		String playerUUID;
		Statement statement;
		ResultSet results;
		
		try{
			playerUUID = player.getUniqueId().toString();
			statement = connection.createStatement();
			results = statement.executeQuery("SELECT * FROM playerData WHERE ID='" + playerUUID + "'");
			results.next();
			
			return String.valueOf(results.getString("ROLE").toString());
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
