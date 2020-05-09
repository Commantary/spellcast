package fr.commentary.spellcast.classes;

import java.util.List;
import java.util.UUID;

public class PlayerStats {

    private List<Integer> effects;

    private UUID uuid;

    private String username;

    private PlayerStats instance;

    private int particlesInstance;

    public PlayerStats(List<Integer> effects, UUID uuid, String username) {
        this.effects = effects;
        this.uuid = uuid;
        this.username = username;
        this.instance = this;
    }

    public List<Integer> getEffects() {
        return effects;
    }

    public void setEffect(List<Integer> effects) {
        this.effects = effects;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PlayerStats getInstance() {
        return instance;
    }

    public void setInstance(PlayerStats instance) {
        this.instance = instance;
    }

    public int getParticlesInstance() {
        return particlesInstance;
    }

    public void setParticlesInstance(int particlesInstance) {
        this.particlesInstance = particlesInstance;
    }
}
