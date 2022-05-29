package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class Dirt extends Terrain {

    public Dirt(World world, Position position) {
        super(world, position, Size.ONE_BY_ONE, TerrainType.WALKABLE);
        this.terrainRenderer = new TerrainRenderer( 64, 160, new Size(32, 32));
        this.name = "Dirt";
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
