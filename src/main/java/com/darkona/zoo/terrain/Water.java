package com.darkona.zoo.terrain;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;

import java.awt.*;

public class Water extends Terrain{


    public Water(Coordinates coordinates) {
        super(coordinates, Size.ONE_BY_ONE, TerrainType.SWIMMABLE);
        //this.terrainRenderer = new TerrainRenderer(735, 352, new Size(32, 32));
    }

    @Override
    public void render(Graphics graphics) {
       terrainRenderer.render(graphics, this);
    }

    @Override
    public int getSpeedModifier() {
        return 1;
    }
    @Override
    public void update() {

    }


}
