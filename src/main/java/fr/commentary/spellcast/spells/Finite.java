package fr.commentary.spellcast.spells;

import fr.commentary.spellcast.Spellcast;
import fr.commentary.spellcast.classes.StatType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Finite {

    private PlayerInteractEvent event;
    private Player player;
    private double t = 0;
    private double r = 1;
    private int x;

    public Finite(PlayerInteractEvent event, Player player) {
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
                        if(e instanceof Player){
                            Player p = ((Player) e).getPlayer();
                            List<Integer> effets = Spellcast.getInstance().getPlayerstatsFromUuid(p.getUniqueId()).getEffects();

                            if(!effets.contains(0)) return;

                            effets.remove(StatType.IMMOBILISER.getValue());
                            Spellcast.getInstance().getPlayerstatsFromUuid(p.getUniqueId()).setEffect(effets);
                            particleCercle(p.getLocation());
                            player.setWalkSpeed(0.2f);
                            player.removePotionEffect(PotionEffectType.JUMP);
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
                location.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 0);
                location.add(0, 1, 0);
                location.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 0);
                location.subtract(x,1,z);
            }
        }.runTaskTimer(Spellcast.getInstance(), 0, 1);
    }

}
