package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.world.World;

public class Grass extends Terrain {

    public Grass(World world, Position position) {
        super(world, position, Size.ONE_BY_ONE, TerrainType.WALKABLE);
        this.u = 408;
        this.v = 282;
        this.imageSize = new Size(32);
        this.name = "Grass";
    }

    @Override
    public void update() { }

}
