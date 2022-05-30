package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class Water extends Terrain{

    private final int u1 = 864;
    private int steps = 0;
    public Water(World world, Position position) {
        super(world, position, Size.ONE_BY_ONE, TerrainType.SWIMMABLE);
        this.u = u1;
        this.v = 160;
        this.imageSize = new Size(32);
        this.name = "Water";
    }

    @Override
    public void update() {
        steps++;
        u += 32;
        if(steps == 2) {
            steps = 0;
            u = u1;
        }
    }

    @Override
    public boolean mustUpdate() {
        return true;
    }

}
