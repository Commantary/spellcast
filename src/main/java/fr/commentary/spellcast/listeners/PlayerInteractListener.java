package fr.commentary.spellcast.listeners;

import fr.commentary.spellcast.Spellcast;
import fr.commentary.spellcast.spells.Captorum;
import fr.commentary.spellcast.spells.Evanesco;
import fr.commentary.spellcast.spells.Spells;
import fr.commentary.spellcast.spells.Wingardium;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;

public class PlayerInteractListener implements Listener {
    private Spellcast main;

    public PlayerInteractListener(Spellcast main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(event.getMaterial() == Material.STICK&&(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            for(Map.Entry<Player, Integer> entry : Spellcast.getInstance().spellsPlayer.entrySet()) {
                Player key = entry.getKey();
                Integer value = entry.getValue();

                if(key==player){
                    if(value == 0) {
                        Spellcast.getInstance().spellsPlayer.replace(key, 1);
                    } else if(value == Spells.values().length-1){
                        Spellcast.getInstance().spellsPlayer.replace(key, 0);
                    } else {
                        value++;
                        Spellcast.getInstance().spellsPlayer.replace(key, value);
                    }
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Spells.fromValue(value).getName()));
                }
            }
        }
        for(Map.Entry<Player, Integer> entry : Spellcast.getInstance().spellsPlayer.entrySet()) {
            Player key = entry.getKey();
            Integer value = entry.getValue();

            if(key==player){
                if(value == 0){
                    new Captorum(event, player).useSpell();
                } else if(value == 1){
                    new Wingardium(event, player).useSpell();
                } else if(value == 2){
                    new Evanesco(event, player).useSpell();
                }
            }
        }

    }
}
