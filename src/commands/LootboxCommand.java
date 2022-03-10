package commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lootbox.main;

public class LootboxCommand implements CommandExecutor {
	
	private main plug;

	public LootboxCommand(main plug) {
		plug=plug;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		sender.sendMessage("La clé vous a été give");
		Player player = (Player) sender;
		//String name = plug.getConfig().getString("key");
		Material key = Material.getMaterial("NETHER_STAR");
		System.out.println(key);

		ItemStack item = new ItemStack(key, 1);
		
    	player.getInventory().addItem(item);
    	
		return false;
	}


}
