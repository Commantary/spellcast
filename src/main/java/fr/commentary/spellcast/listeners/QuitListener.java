package fr.commentary.spellcast.listeners;

import fr.commentary.spellcast.Spellcast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Spellcast.getInstance().spellsPlayer.remove(player);
        Spellcast.getInstance().playerStats.remove(Spellcast.getInstance().getPlayerstatsFromUuid(player.getUniqueId()));
    }

}
