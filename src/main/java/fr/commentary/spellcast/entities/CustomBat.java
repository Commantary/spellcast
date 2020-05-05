package fr.commentary.spellcast.entities;

import net.minecraft.server.v1_15_R1.EntityBat;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

public class CustomBat extends EntityBat {


    public CustomBat(EntityTypes<? extends EntityBat> entitytypes, World world) {
        super(entitytypes, world);
    }
}
