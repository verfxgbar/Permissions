package de.verfxgbar.listener;

import de.verfxgbar.utils.FISPermissions;
import de.verfxgbar.utils.NameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Join_Leave implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FISPermissions.registerPlayer(player);
        NameManager.updateNames();
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        NameManager.updateNames();
    }
}
