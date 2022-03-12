package listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
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
		
	                        
        Inventory lootbox = Bukkit.createInventory(null, 9, "La lootbox de l'espace");
        
    	// ------------------------------------------------ start of the test
       
        ItemStack hand = player.getInventory().getItemInMainHand();  	
        
		if (event.getHand() == EquipmentSlot.HAND) {

        if (action==Action.RIGHT_CLICK_BLOCK) {

        
        	if ((block.getX()==x && block.getY()==y && block.getZ()==z) && (block.getType().equals(blocktype))) {

        		if (hand.equals(getKey(hand.getAmount()))) {
        			player.openInventory(lootbox);
            	    
            	    hand.setAmount(hand.getAmount()-1);
	            	
            	
            	
            	// ------------------------------------------------ scroll	
            		new BukkitRunnable() {
            			
            			int time = 10;
            			ItemStack item = null;
    					@Override
    					public void run() {
            		
    						
		            		if (time == 0) 
		            		{
		            			cancel();
		            		}
		            		else {
		            			    								            			
			            		item = getRandomItem(proba, item);
			                	lootbox.setItem(4, item);		                	
			                	time=time-1;
		                	
//		                    Bukkit.broadcastMessage("test");
		            		}
		                	
    					}
    					
            		
            		}.runTaskTimer(plug, 0L, 5L);            	
            	// toutes les secondes : 20L
            	            		
                	// ------------------------------------------------ item give

                	new BukkitRunnable() {

    					@Override
    					public void run() {
    						
    	            		ItemStack item = getRandomItem(proba, null);
//    						System.out.println(item);
    		            	player.closeInventory();
    			            player.getInventory().addItem(item);
    			         
    					}
                			
                		
                	}.runTaskLater(plug, 50);     	
 	
            
        		}
            }
      
        }
		}

}
    
    
    public ItemStack getRandomItem(ArrayList<Material> list, ItemStack before) {
    	
        int randomItem = 0 + (int)(Math.random() * list.size());
        ItemStack item;
    	Material choice = (Material) list.get(randomItem);

    	item = new ItemStack(choice, 1);
    	
    	if (item==before) {
    		item = getRandomItem(list, before);
    	}
/*    	ItemMeta custom = item.getItemMeta();
    	
    	double prob = Collections.frequency(list, choice)*10;
    	custom.setDisplayName("Bravo "+ prob + "%");
    	item.setItemMeta(custom); */ // no name
    	return item;
    }
    
    
    public ItemStack getKey(int nb) {
		Material key = Material.getMaterial(plug.getConfig().getString("key.item"));
		ItemStack item = new ItemStack(key, nb);
    	ItemMeta custom = item.getItemMeta();
    	custom.setDisplayName(plug.getConfig().getString("key.name"));
    	custom.setLore(new ArrayList() {{
    		add(plug.getConfig().getString("key.lore"));
    		}});
    	item.setItemMeta(custom);
        return item;

    }
    
    
    
}

