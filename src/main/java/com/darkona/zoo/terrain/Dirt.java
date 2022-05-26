package com.darkona.zoo.terrain;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.ImageUtils;

import java.awt.*;

public class Dirt extends Terrain {

    public Dirt(Coordinates coordinates) {
        super(coordinates, Size.ONE_BY_ONE, TerrainType.WALKABLE);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {
        getSprite(graphics);
    }

    @Override
    public Image getSprite(Graphics graphics) {
        ImageUtils.drawImage(image, this, graphics, 27, 92, new Size(32, 32));
        return image;
    }

    @Override
    public int getSpeedModifier() {
        return 1;
    }
}
