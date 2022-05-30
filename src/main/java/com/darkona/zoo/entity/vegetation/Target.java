package com.darkona.zoo.entity.vegetation;

import java.awt.*;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

public class Target extends Vegetation {


    public Target(World world, Position position) {
        super(world, position, new Size());
        this.terrainRenderer = new TerrainRenderer( 416, 608, new Size(32, 32));
        this.name = "Target";
    }


}
