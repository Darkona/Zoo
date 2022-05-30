package com.darkona.zoo.entity.vegetation;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class NoVegetation extends Vegetation {

    public NoVegetation(World world, Position position) {
        super(world, position, new Size(1,1));
        this.terrainRenderer = new TerrainRenderer( 448, 608, new Size(32, 32));
        this.name = "None";
    }

}
