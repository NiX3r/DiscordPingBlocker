package cz.nixdevelopment.dpb;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordPingBlocker extends JavaPlugin implements Listener{

	public static JavaPlugin inst;
	
	public void onEnable() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		inst = this;
	    Bukkit.getPluginManager().registerEvents(this, this);
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(AsyncPlayerChatEvent e){
		
		// check if message contains ping
		if(e.getMessage().contains("@")) {
			
			// check block status
			if(this.getConfig().getBoolean("BlockOnlyHereAndEveryone")) {
				
				// block only @here and @everyone
				String msg = this.getConfig().getString("Prefix") + this.getConfig().getString("Messages.HereAndEveryoneBlocked");
				msg = msg.replaceAll("&", "§");

				e.getPlayer().sendMessage(msg);
				
				// check cancel status
				if(this.getConfig().getBoolean("CancelMessageOnUse")) {
					e.setCancelled(true);
				}
				else {
					e.setMessage(e.getMessage().replaceAll("@here", "").replaceAll("@everyone", ""));
				}
				
			}
			else {
				
				// block whole ping
				String msg = this.getConfig().getString("Prefix") + this.getConfig().getString("Messages.WholePingBlocked");
				msg = msg.replaceAll("&", "§");
				
				e.getPlayer().sendMessage(msg);
				
				// check cancel status
				if(this.getConfig().getBoolean("CancelMessageOnUse")) {
					e.setCancelled(true);
				}
				else {
					e.setMessage(e.getMessage().replaceAll("@", ""));
				}
				
			}
			
		}
		
	}
	
}
