package com.darkona.zoo.entity.vegetation;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;

import java.awt.*;

public class NoVegetation extends Vegetation {

    public NoVegetation(Position position) {
        super(position, new Size(1,1));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {

    }


}
