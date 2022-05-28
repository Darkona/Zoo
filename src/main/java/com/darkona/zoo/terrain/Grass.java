package com.darkona.zoo.terrain;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;

import java.awt.*;

public class Grass extends Terrain {


    public Grass(Coordinates coordinates) {
        super(coordinates, Size.ONE_BY_ONE, TerrainType.WALKABLE);
        this.terrainRenderer = new TerrainRenderer(408, 282, new Size(32, 32));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {
        terrainRenderer.render(graphics, this);
    }


    @Override
    public int getSpeedModifier() {
        return 1;
    }
}
