package listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import lootbox.main;

import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class LootboxListener implements Listener {

	private main plug;
	private List<Player> players = new ArrayList<>(); 
	
	public LootboxListener(main lootbox) {
		plug = lootbox;
		
	}
	
	/*
	@EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            System.out.println("event fired !");
            event.getPlayer().openInventory(Bukkit.createInventory(null, 9));
        }
    }
	*/
	
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
    	
//    	event.setCancelled(true);
    	
    	// ------------------------------------------------ init variables
    	
        Action action = event.getAction();
        //Bukkit.broadcastMessage(action.toString());

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
                        
//        Map<Material, Double> items = new HashMap<>();
        
        List<String> listitems = new ArrayList<String>(); 
        List<Double> listproba = new ArrayList<Double>(); 

        listitems = plug.getConfig().getStringList("items");
        listproba = plug.getConfig().getDoubleList("probabilities");
        
        int x = plug.getConfig().getInt("localisation.x");
        int y = plug.getConfig().getInt("localisation.y");
        int z = plug.getConfig().getInt("localisation.z");
        Material blocktype = Material.getMaterial(plug.getConfig().getString("localisation.type"));
        

    	// ------------------------------------------------ probabilities
           
        ArrayList<Material> proba = new ArrayList<Material>();
        
		for (int i=0; i<listitems.size(); i++) {
			
			for(int j = 0 ; j < 10*listproba.get(i) ; j++){
				proba.add(Material.getMaterial(listitems.get(i)));
        	}
        }
		
		System.out.println(proba);
		
        int nb = proba.size();
                        
        Inventory lootbox = Bukkit.createInventory(null, 9, "La lootbox de l'espace");
        
    	// ------------------------------------------------ start of the test

        
        if (action==Action.RIGHT_CLICK_BLOCK) {
        
        	if ((block.getX()==x && block.getY()==y && block.getZ()==z) && (block.getType().equals(blocktype))) {

            	int randomItem = 0 + (int)(Math.random() * nb);
            	
            	Material choice = (Material) proba.get(randomItem);
            	ItemStack item = new ItemStack(choice, 1);
            	ItemMeta custom = item.getItemMeta();
            	
            	
            	double prob = Collections.frequency(proba, choice)*10;
            	custom.setDisplayName("Bravo "+ prob + "%");
            	item.setItemMeta(custom);
            	           	
            	lootbox.setItem(4, item);
            	
            	player.openInventory(lootbox);
            	
            	System.out.println("cb");
            	            	
            	new BukkitRunnable() {

					@Override
					public void run() {
						
//						System.out.println(item);
		            	player.closeInventory();
		            	if (!players.contains(player)) {
		            		players.add(player);
			            	player.getInventory().addItem(item);
			            	
			            	new BukkitRunnable() {

								@Override
								public void run() {
									players.remove(player);
								}
								
							}.runTaskLater(plug,30);
			            	
			            	
		            	}
		            	System.out.println("cb2");
					}
            			
            		
            	}.runTaskLater(plug, 50);     	
            
            	
            
            }
        }


}
    
    
}
