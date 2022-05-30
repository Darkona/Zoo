package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class RedDirt extends Terrain{

    public RedDirt(World world, Position position) {
        super(world, position, new Size(), TerrainType.WALKABLE);
        this.u = 256;
        this.v = 160;
        this.imageSize = new Size(32);
        this.name = "Red Dirt";
    }

    @Override
    public void update() {}

}
