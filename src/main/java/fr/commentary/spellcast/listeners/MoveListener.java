package fr.commentary.spellcast.listeners;

import fr.commentary.spellcast.Spellcast;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if(Spellcast.getInstance().getPlayerstatsFromUuid(player.getUniqueId()).getEffects().contains(0)){
            player.setWalkSpeed(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 180, false, false));
            Location goTo = event.getTo();
            if(goTo.getX() != player.getLocation().getX() || goTo.getY() != player.getLocation().getY() ||goTo.getZ() != player.getLocation().getZ()){
                event.setCancelled(true);
            }
        } else {
            player.setWalkSpeed(0.2f);
            player.removePotionEffect(PotionEffectType.JUMP);
        }
    }
}
