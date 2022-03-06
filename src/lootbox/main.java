package lootbox;

import org.bukkit.plugin.java.JavaPlugin;

import listeners.LootboxListener;
import listeners.NoPick;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class main extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
	    getServer().getPluginManager().registerEvents(new LootboxListener(this), this);
	    getServer().getPluginManager().registerEvents(new NoPick(), this);

	}
}
