package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class Lava extends Terrain{

    public Lava(World world, Position position) {
        super(world, position, new Size(), TerrainType.IMPASSABLE);
        this.name = "Lava";
        this.terrainRenderer = new TerrainRenderer(544,160, new Size(32));
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
