package com.darkona.zoo.entity.animal;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldCell;
import com.darkona.zoo.world.WorldThing;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Animal extends WorldThing implements Renderable {

    protected int energy;
    protected Position destination;
    protected int maxEnergy;
    protected int minEnergy;
    protected Animal partner;
    protected Image image;
    protected UUID id;
    protected WorldCell currentCell;

    public Animal(World world, Position position, Size size) {
        super(position, size, world);
        this.id = UUID.randomUUID();
    }

    public abstract void move(World world, Position destination);


}
