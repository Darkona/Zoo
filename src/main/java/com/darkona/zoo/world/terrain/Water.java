package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;

import java.awt.*;

public class Water extends Terrain{


    public Water(Position position) {
        super(position, Size.ONE_BY_ONE, TerrainType.SWIMMABLE);
        //this.terrainRenderer = new TerrainRenderer(735, 352, new Size(32, 32));
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
