package com.darkona.zoo.entity.vegetation;

import java.awt.*;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;

public  abstract class Vegetation extends WorldThing implements Renderable {

    protected TerrainRenderer terrainRenderer;
    public Vegetation(World world, Position position, Size size) {
        super(position, size, world);
    }

    @Override
    public void render(Graphics graphics) {
        if(terrainRenderer!= null) terrainRenderer.render(graphics, this);
    }


    @Override
    public void update() {

    }
}
