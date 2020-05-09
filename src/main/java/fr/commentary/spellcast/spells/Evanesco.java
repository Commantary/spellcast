package fr.commentary.spellcast.spells;

import fr.commentary.spellcast.Spellcast;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Evanesco {

    private PlayerInteractEvent event;
    private Player player;
    private double t = 0;
    private double r = 1;
    private int x;

    public Evanesco(PlayerInteractEvent event, Player player) {
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
                player.getWorld().spawnParticle(Particle.CRIT, loc, 0);
                for (Entity e : loc.getChunk().getEntities()) {
                    if (e.getLocation().distance(loc) < 2.0 && !e.equals(player)){
                        if(e instanceof CraftItem) return;
                        if(e instanceof Player){
                            Player p = ((Player) e).getPlayer();
                            Location locBack = p.getLocation();
                            Location location = new Location(p.getWorld(), p.getLocation().getX(), 256, p.getLocation().getZ());
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 10, false, false));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1, false, false));
                            player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locBack, 0);
                            particleCercle(e.getLocation());
                            p.setInvulnerable(true);
                            location.subtract(0, 1, 0);
                            changeBlock(location, Material.BARRIER);
                            location.add(1,1,0);
                            changeBlock(location, Material.BARRIER);
                            location.subtract(2,0,0);
                            changeBlock(location, Material.BARRIER);
                            location.add(1,0,1);
                            changeBlock(location, Material.BARRIER);
                            location.subtract(0,0,2);
                            changeBlock(location, Material.BARRIER);
                            location.add(0,0,1);
                            p.teleport(location);
                            respawnPlayer(locBack, p, location);
                            stop = true;
                        } else {
                            Location location = new Location(e.getWorld(), e.getLocation().getX(), 300, e.getLocation().getZ());

                            player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, location, 0);
                            particleCercle(e.getLocation());
                            e.teleport(location);
                            if(!e.isDead()){
                                ((CraftLivingEntity)e).damage(50.0);
                            }
                            stop = true;
                        }
                    }
                }
                if(loc.getBlock().getType()!=Material.AIR&&loc.getBlock().getType()!=Material.GRASS&&loc.getBlock().getType()!=Material.WATER&&loc.getBlock().getType()!=Material.SNOW)
                    stop = true;
                loc.subtract(x, y, z);
            }
        }
    }

    public void particleCercle(Location location){
        t = 0;
        r = 1;
        x = 0;
        new BukkitRunnable(){
            @Override
            public void run() {
                x +=1;
                if(x==20){
                    cancel();
                }
                t += Math.PI/8;
                double x = r*cos(t);
                double y = 0;
                double z = r*sin(t);
                location.add(x,y,z);
                location.getWorld().spawnParticle(Particle.CLOUD, location, 0);
                location.add(0, 1, 0);
                location.getWorld().spawnParticle(Particle.CLOUD, location, 0);
                location.add(0, 1, 0);
                location.getWorld().spawnParticle(Particle.CLOUD, location, 0);
                location.subtract(x,2,z);
            }
        }.runTaskTimer(Spellcast.getInstance(), 0, 1);
    }
    public void particleBack(Location location){
        t = 0;
        r = 1;
        x = 0;
        new BukkitRunnable(){
            @Override
            public void run() {
                x +=1;
                if(x==20){
                    cancel();
                }
                t += Math.PI/8;
                double x = r*cos(t);
                double y = 0;
                double z = r*sin(t);
                location.add(x,y,z);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location, 0);
                location.add(0, .5, 0);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location, 0);
                location.add(0, .5, 0);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location, 0);
                location.add(0, .5, 0);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location, 0);
                location.add(0, .5, 0);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location, 0);
                location.subtract(x,2,z);
            }
        }.runTaskTimer(Spellcast.getInstance(), 0, 1);
    }

    public void changeBlock(Location location, Material material){
        location.getBlock().setType(material);
        location.getBlock().getState().update();
    }

    public void respawnPlayer(Location locBack, Player player, Location oldBlock){
        new BukkitRunnable(){
            @Override
            public void run() {
                player.teleport(locBack);
                player.setInvulnerable(false);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                oldBlock.subtract(0, 1, 0);
                changeBlock(oldBlock, Material.BARRIER);
                oldBlock.add(1,1,0);
                changeBlock(oldBlock, Material.BARRIER);
                oldBlock.subtract(2,0,0);
                changeBlock(oldBlock, Material.BARRIER);
                oldBlock.add(1,0,1);
                changeBlock(oldBlock, Material.BARRIER);
                oldBlock.subtract(0,0,2);
                changeBlock(oldBlock, Material.BARRIER);
                oldBlock.add(0,0,1);
                particleBack(locBack);
            }
        }.runTaskLater(Spellcast.getInstance(), 140L);
    }

}
