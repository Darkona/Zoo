package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.world.World;

public class Lava extends Terrain{

    private int u1 = 480;
    private int steps = 0;

    public Lava(World world, Position position) {
        super(world, position, new Size(), TerrainType.IMPASSABLE);
        this.name = "Lava";
        this.u = u1;
        this.v = 160;
        this.imageSize =  new Size(32);
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
