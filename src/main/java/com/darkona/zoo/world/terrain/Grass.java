package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class Grass extends Terrain {


    public Grass(World world, Position position) {
        super(world, position, Size.ONE_BY_ONE, TerrainType.WALKABLE);
        this.terrainRenderer = new TerrainRenderer(408, 282, new Size(32, 32));
        this.name = "Grass";
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
