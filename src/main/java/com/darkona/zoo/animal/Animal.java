package com.darkona.zoo.animal;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.interfaces.Renderable;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Animal extends WorldThing implements Renderable {

    protected int energy;
    protected Coordinates destination;
    protected int maxEnergy;
    protected int minEnergy;
    protected Animal partner;
    protected final World world;
    protected Image image;
    protected UUID id;


    public Animal(World world, Coordinates position, Size size) {
        super(position, size);
        this.world = world;
        this.id = UUID.randomUUID();
    }

    public abstract void move(World world, Coordinates destination);


}
