package fr.commentary.spellcast.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Captorum {

    private PlayerInteractEvent event;
    private Player player;

    public Captorum(PlayerInteractEvent event, Player player) {
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
                    if (e.getLocation().distance(loc) < 2.0 && e instanceof Player && !e.equals(player)) {
                        e.setFireTicks(20 * 5);
                        player.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
                        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 0);

                        Player p = (Player) e;
                        p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 10, true));
                        p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 10, true));
                        p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 10, true));
                        stop = true;
                    }
                }
                if(loc.getBlock().getType()!=Material.AIR&&loc.getBlock().getType()!=Material.GRASS&&loc.getBlock().getType()!=Material.WATER)
                    stop = true;
                loc.subtract(x, y, z);
            }
        }
    }

}
