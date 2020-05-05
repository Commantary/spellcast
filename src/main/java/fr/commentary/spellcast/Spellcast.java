package fr.commentary.spellcast;

import fr.commentary.spellcast.events.PlayerInteract;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Spellcast extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents((Listener) new PlayerInteract(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
