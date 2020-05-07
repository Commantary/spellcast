package fr.commentary.spellcast.listeners;

import fr.commentary.spellcast.Spellcast;
import fr.commentary.spellcast.spells.Spells;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.Map;

public class ItemHeldListener implements Listener {

    @EventHandler
    public void onItemSwap(PlayerItemHeldEvent event){
        Player player = event.getPlayer();

        if(player.getInventory().getItem(event.getNewSlot()) == null) return;

        if(player.getInventory().getItem(event.getNewSlot()).getType() == Material.STICK){
            for(Map.Entry<Player, Integer> entry : Spellcast.getInstance().spellsPlayer.entrySet()) {
                Player key = entry.getKey();
                Integer value = entry.getValue();

                if(key==player){
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Spells.fromValue(value).getName()));
                }
            }
        } else {
            player.resetTitle();
        }
    }
}
