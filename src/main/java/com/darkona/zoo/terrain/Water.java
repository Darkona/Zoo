package com.darkona.zoo.terrain;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.ImageUtils;

import java.awt.*;

public class Water extends Terrain{


    public Water(Coordinates coordinates) {
        super(coordinates, Size.ONE_BY_ONE, TerrainType.SWIMMABLE);
    }


    @Override
    public void render(Graphics graphics) {
        getSprite(graphics);
    }


    @Override
    public int getSpeedModifier() {
        return 1;
    }

    @Override
    public void update() {

    }

    @Override
    public Image getSprite(Graphics graphics) {
        ImageUtils.drawImage(image, this, graphics, 735, 352, new Size(32, 32));
        return image;
    }
}
