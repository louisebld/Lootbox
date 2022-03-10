package listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NoPick implements Listener {

	@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
	    	    
	    if (event.getView().getTitle()=="La lootbox de l'espace") {
			Bukkit.broadcastMessage("noooo");	
			event.setCancelled(true);

	    }
	    

		
		
	}
}
