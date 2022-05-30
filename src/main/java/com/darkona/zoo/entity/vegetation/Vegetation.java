package com.darkona.zoo.entity.vegetation;

import java.awt.*;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;
import lombok.Data;

@Data
public  abstract class Vegetation extends WorldThing {

    protected TerrainRenderer terrainRenderer;
    protected int u;
    protected int v;
    protected Size imageSize;

    public Vegetation(World world, Position position, Size size) {
        super(position, size, world);
    }

    @Override
    public void update() {

    }
}
