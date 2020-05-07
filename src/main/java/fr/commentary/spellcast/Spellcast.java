package fr.commentary.spellcast;

import fr.commentary.spellcast.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Spellcast extends JavaPlugin implements Listener {

    public HashMap<Player, Integer> spellsPlayer = new HashMap<Player,Integer>();
    private static Spellcast instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents((Listener) new PlayerInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemHeldListener(), this);
    }

    public HashMap<Player, Integer> getSpellsPlayer() {
        return spellsPlayer;
    }
    public static Spellcast getInstance() {
        return instance;
    }
    public void setSpellsPlayer(HashMap<Player, Integer> spellsPlayer) {
        this.spellsPlayer = spellsPlayer;
    }
}
