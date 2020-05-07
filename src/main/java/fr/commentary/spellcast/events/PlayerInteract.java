package fr.commentary.spellcast.events;

import fr.commentary.spellcast.Spellcast;
import fr.commentary.spellcast.spells.Captorum;
import fr.commentary.spellcast.spells.Wingardium;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    private Spellcast main;

    public PlayerInteract(Spellcast main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        //new Captorum(event, player).useSpell();
        new Wingardium(main, event, player).useSpell();
    }
}
