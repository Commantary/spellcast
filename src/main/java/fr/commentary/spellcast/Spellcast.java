package fr.commentary.spellcast;

import fr.commentary.spellcast.classes.PlayerStats;
import fr.commentary.spellcast.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Spellcast extends JavaPlugin implements Listener {

    public List<PlayerStats> playerStats = new ArrayList<>();
    public HashMap<Player, Integer> spellsPlayer = new HashMap<Player,Integer>();
    private static Spellcast instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents((Listener) new PlayerInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemHeldListener(), this);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
    }

    public static Spellcast getInstance() {
        return instance;
    }
    public PlayerStats getPlayerstatsFromUuid(UUID uuid){
        for(PlayerStats stats : playerStats){
            if(stats.getUuid()==uuid){
                return stats;
            }
        }
        return null;
    }
}
