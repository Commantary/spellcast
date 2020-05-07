package fr.commentary.spellcast.listeners;

import fr.commentary.spellcast.Spellcast;
import fr.commentary.spellcast.spells.Spells;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Spellcast.getInstance().spellsPlayer.put(player, Spells.CAPTORUM.getValue());

        new BukkitRunnable(){
            @Override
            public void run() {
                if(player.getInventory().getItem(player.getInventory().getHeldItemSlot()) == null) return;

                if(player.getInventory().getItem(player.getInventory().getHeldItemSlot()).getType() == Material.STICK){
                    for(Map.Entry<Player, Integer> entry : Spellcast.getInstance().spellsPlayer.entrySet()) {
                        Player key = entry.getKey();
                        Integer value = entry.getValue();

                        if(key==player){
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Spells.fromValue(value).getName()));
                        }
                    }
                }
            }
        }.runTaskTimer(Spellcast.getInstance(), 0, 20);
    }

}
