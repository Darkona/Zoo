package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class RedDirt extends Terrain{

    public RedDirt(World world, Position position) {
        super(world, position, new Size(), TerrainType.WALKABLE);
        this.terrainRenderer = new TerrainRenderer( 256, 160, new Size(32, 32));
        this.name = "Red Dirt";
    }

    @Override
    public void render(Graphics graphics) {
        terrainRenderer.render(graphics, this);
    }

    @Override
    public void update() {

    }

    @Override
    public int getSpeedModifier() {
        return 0;
    }
}
