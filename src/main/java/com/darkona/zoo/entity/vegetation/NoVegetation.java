package com.darkona.zoo.entity.vegetation;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.world.World;

import java.awt.*;

public class NoVegetation extends Vegetation {

    public NoVegetation(World world, Position position) {
        super(world, position, new Size(1,1));
        this.name = "None";
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {

    }


}
