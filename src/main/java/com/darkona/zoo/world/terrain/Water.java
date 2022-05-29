package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class Water extends Terrain{


    public Water(World world, Position position) {
        super(world, position, Size.ONE_BY_ONE, TerrainType.SWIMMABLE);
        this.terrainRenderer = new TerrainRenderer(928, 160, new Size(32, 32));
        this.name = "Water";
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
