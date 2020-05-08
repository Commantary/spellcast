package fr.commentary.spellcast.spells;

import fr.commentary.spellcast.Spellcast;
import fr.commentary.spellcast.entities.CustomBat;
import net.minecraft.server.v1_15_R1.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Wingardium {

    private PlayerInteractEvent event;
    private Player player;
    private double t = 0;
    private double r = 1;

    public Wingardium(PlayerInteractEvent event, Player player) {
        this.event = event;
        this.player = player;
    }

    public void useSpell(){
        if(event.getMaterial() == Material.STICK && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)){
            Vector dir = player.getLocation().getDirection().normalize();
            Location loc = player.getLocation();
            boolean stop = false;
            for(double t = 0; t<17; t+=0.5) {
                if(stop)
                    break;
                double x = dir.getX() * t;
                double y = dir.getY() * t + 1.5;
                double z = dir.getZ() * t;
                loc.add(x, y, z);
                player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, loc, 0);
                for (Entity e : loc.getChunk().getEntities()) {
                    if (e.getLocation().distance(loc) < 2.0 && !e.equals(player)){
                        CustomBat customBat = new CustomBat(EntityTypes.BAT , e.getLocation());
                        ((CraftWorld)loc.getWorld()).getHandle().addEntity(customBat, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        customBat.setPosition(e.getLocation().getX(), e.getLocation().getY()+1, e.getLocation().getZ());
                        customBat.getBukkitEntity().addPassenger(e);
                        e.setInvulnerable(true);
                        destroyBat(customBat, e);
                        particlesFly(customBat, e);
                        stop = true;
                    }
                }
                if(loc.getBlock().getType()!=Material.AIR&&loc.getBlock().getType()!=Material.GRASS&&loc.getBlock().getType()!=Material.WATER&&loc.getBlock().getType()!=Material.GRASS_BLOCK)
                    stop = true;
                loc.subtract(x, y, z);
            }
        }
    }

    public void destroyBat(CustomBat customBat, Entity entity){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(Spellcast.getInstance(), new Runnable() {
            @Override
            public void run() {
                entity.leaveVehicle();
                customBat.setPosition(entity.getLocation().getX(), 300, entity.getLocation().getZ());
                customBat.killEntity();
                entity.setInvulnerable(false);
            }
        }, 100);
    }

    public void particlesFly(CustomBat customBat, Entity entity){
        t = 0;
        r = 1;
        new BukkitRunnable(){
            @Override
            public void run() {
                if(customBat.isAlive()){
                    Location loc = entity.getLocation();
                    t += Math.PI/8;
                    double x = r*cos(t);
                    double y = 0;
                    double z = r*sin(t);
                    loc.add(x,y,z);
                    entity.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc, 0);
                    loc.add(0, 1, 0);
                    entity.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc, 0);
                    loc.subtract(x,1,z);
                }
            }
        }.runTaskTimer(Spellcast.getInstance(), 0, 1);
    }

}
