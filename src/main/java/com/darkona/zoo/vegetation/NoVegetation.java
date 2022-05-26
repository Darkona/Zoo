package com.darkona.zoo.vegetation;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;

import java.awt.*;

public class NoVegetation extends Vegetation {

    public NoVegetation(Coordinates position) {
        super(position, new Size(1,1));
    }

    @Override
    public void update() {

    }

    @Override
    public Image getSprite(Graphics graphics) {
        return null;
    }


    @Override
    public void render(Graphics graphics) {

    }


}
