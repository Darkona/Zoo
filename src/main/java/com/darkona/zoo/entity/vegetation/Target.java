package com.darkona.zoo.entity.vegetation;

import java.awt.*;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

public class Target extends Vegetation {


    public Target(World world, Position position) {
        super(world, position, new Size());
        this.u = 416;
        this.v = 608;
        this.imageSize = new Size(32);
        this.name = "Target";
    }


}
