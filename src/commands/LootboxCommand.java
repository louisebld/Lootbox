package commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lootbox.main;

public class LootboxCommand implements CommandExecutor {
	
	private main plug;

	public LootboxCommand(main plugin) {
		plug=plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		Player player = (Player) sender;
		Material key = Material.getMaterial(plug.getConfig().getString("key.item"));
//		System.out.println(key);

		ItemStack item = new ItemStack(key, 1);
    	ItemMeta custom = item.getItemMeta();
    	custom.setDisplayName(plug.getConfig().getString("key.name"));
    	custom.setLore(new ArrayList() {{
    		add(plug.getConfig().getString("key.lore"));
    		}});
    	item.setItemMeta(custom);

		
    	player.getInventory().addItem(item);
    	
		sender.sendMessage("La clé vous a été give");
    	
		return false;
	}


}
