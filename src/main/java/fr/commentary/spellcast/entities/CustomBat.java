package fr.commentary.spellcast.entities;

import fr.commentary.spellcast.Spellcast;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomBat extends EntityBat {

    public CustomBat(EntityTypes<? extends EntityBat> entitytypes, Location loc) {
        super(entitytypes,((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        new BukkitRunnable(){
            @Override
            public void run() {
                setInvisible(true);
                setInvulnerable(true);
            }
        }.runTaskLater(Spellcast.getInstance(), 2);
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAsleep()) {
            this.setMot(Vec3D.a);
            this.setPositionRaw(this.locX(), (double) MathHelper.floor(this.locY()) + 1.0D - (double)this.getHeight(), this.locZ());
        } else {
            this.setMot(this.getMot().d(0.0D, 0.0D, 0.0D));
        }
    }
}
